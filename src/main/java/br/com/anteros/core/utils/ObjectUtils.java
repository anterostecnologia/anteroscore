/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package br.com.anteros.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.anteros.core.converter.ConversionHelper;

public abstract class ObjectUtils {

	private static final int INITIAL_HASH = 7;
	private static final int MULTIPLIER = 31;

	private static final String EMPTY_STRING = "";
	private static final String NULL_STRING = "null";
	private static final String ARRAY_START = "{";
	private static final String ARRAY_END = "}";
	private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;
	private static final String ARRAY_ELEMENT_SEPARATOR = ", ";

	public static boolean isCheckedException(Throwable ex) {
		return !(ex instanceof RuntimeException || ex instanceof Error);
	}

	public static boolean isCompatibleWithThrowsClause(Throwable ex, Class<?>[] declaredExceptions) {
		if (!isCheckedException(ex)) {
			return true;
		}
		if (declaredExceptions != null) {
			for (int i = 0; i < declaredExceptions.length; i++) {
				if (declaredExceptions[i].isAssignableFrom(ex.getClass())) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	public static boolean containsElement(Object[] array, Object element) {
		if (array == null)
			return false;
		for (int i = 0; i < array.length; i++) {
			if (nullSafeEquals(array[i], element))
				return true;
		}
		return false;
	}

	public static Object[] addObjectToArray(Object[] array, Object obj) {
		Class<?> compType = Object.class;
		if (array != null)
			compType = array.getClass().getComponentType();
		else if (obj != null)
			compType = obj.getClass();

		int newArrLength = (array != null ? array.length + 1 : 1);
		Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
		if (array != null)
			System.arraycopy(array, 0, newArr, 0, array.length);
		newArr[newArr.length - 1] = obj;
		return newArr;
	}

	public static Object[] toObjectArray(Object source) {
		if (source instanceof Object[]) {
			return (Object[]) source;
		}
		if (source == null) {
			return new Object[0];
		}
		if (!source.getClass().isArray()) {
			throw new IllegalArgumentException("Source is not an array: " + source);
		}
		int length = Array.getLength(source);
		if (length == 0) {
			return new Object[0];
		}
		Class<?> wrapperType = Array.get(source, 0).getClass();
		Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
		for (int i = 0; i < length; i++) {
			newArray[i] = Array.get(source, i);
		}
		return newArray;
	}

	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			if (o1 instanceof Object[] && o2 instanceof Object[]) {
				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
			if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2);
			}
			if (o1 instanceof byte[] && o2 instanceof byte[]) {
				return Arrays.equals((byte[]) o1, (byte[]) o2);
			}
			if (o1 instanceof char[] && o2 instanceof char[]) {
				return Arrays.equals((char[]) o1, (char[]) o2);
			}
			if (o1 instanceof double[] && o2 instanceof double[]) {
				return Arrays.equals((double[]) o1, (double[]) o2);
			}
			if (o1 instanceof float[] && o2 instanceof float[]) {
				return Arrays.equals((float[]) o1, (float[]) o2);
			}
			if (o1 instanceof int[] && o2 instanceof int[]) {
				return Arrays.equals((int[]) o1, (int[]) o2);
			}
			if (o1 instanceof long[] && o2 instanceof long[]) {
				return Arrays.equals((long[]) o1, (long[]) o2);
			}
			if (o1 instanceof short[] && o2 instanceof short[]) {
				return Arrays.equals((short[]) o1, (short[]) o2);
			}
		}
		return false;
	}

	public static int nullSafeHashCode(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj.getClass().isArray()) {
			if (obj instanceof Object[]) {
				return nullSafeHashCode((Object[]) obj);
			}
			if (obj instanceof boolean[]) {
				return nullSafeHashCode((boolean[]) obj);
			}
			if (obj instanceof byte[]) {
				return nullSafeHashCode((byte[]) obj);
			}
			if (obj instanceof char[]) {
				return nullSafeHashCode((char[]) obj);
			}
			if (obj instanceof double[]) {
				return nullSafeHashCode((double[]) obj);
			}
			if (obj instanceof float[]) {
				return nullSafeHashCode((float[]) obj);
			}
			if (obj instanceof int[]) {
				return nullSafeHashCode((int[]) obj);
			}
			if (obj instanceof long[]) {
				return nullSafeHashCode((long[]) obj);
			}
			if (obj instanceof short[]) {
				return nullSafeHashCode((short[]) obj);
			}
		}
		return obj.hashCode();
	}

	public static int nullSafeHashCode(Object[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + nullSafeHashCode(array[i]);
		}
		return hash;
	}

	public static int nullSafeHashCode(boolean[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	public static int nullSafeHashCode(byte[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	public static int nullSafeHashCode(char[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	public static int nullSafeHashCode(double[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	public static int nullSafeHashCode(float[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	public static int nullSafeHashCode(int[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	public static int nullSafeHashCode(long[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + hashCode(array[i]);
		}
		return hash;
	}

	public static int nullSafeHashCode(short[] array) {
		if (array == null) {
			return 0;
		}
		int hash = INITIAL_HASH;
		int arraySize = array.length;
		for (int i = 0; i < arraySize; i++) {
			hash = MULTIPLIER * hash + array[i];
		}
		return hash;
	}

	public static int hashCode(boolean bool) {
		return bool ? 1231 : 1237;
	}

	public static int hashCode(double dbl) {
		long bits = Double.doubleToLongBits(dbl);
		return hashCode(bits);
	}

	public static int hashCode(float flt) {
		return Float.floatToIntBits(flt);
	}

	public static int hashCode(long lng) {
		return (int) (lng ^ (lng >>> 32));
	}

	public static String identityToString(Object obj) {
		if (obj == null) {
			return EMPTY_STRING;
		}
		return obj.getClass().getName() + "@" + getIdentityHexString(obj);
	}

	public static String getIdentityHexString(Object obj) {
		return Integer.toHexString(System.identityHashCode(obj));
	}

	public static String getDisplayString(Object obj) {
		if (obj == null) {
			return EMPTY_STRING;
		}
		return nullSafeToString(obj);
	}

	public static String nullSafeClassName(Object obj) {
		return (obj != null ? obj.getClass().getName() : NULL_STRING);
	}

	public static String nullSafeToString(Object obj) {
		if (obj == null) {
			return NULL_STRING;
		}
		if (obj instanceof String) {
			return (String) obj;
		}
		if (obj instanceof Object[]) {
			return nullSafeToString((Object[]) obj);
		}
		if (obj instanceof boolean[]) {
			return nullSafeToString((boolean[]) obj);
		}
		if (obj instanceof byte[]) {
			return nullSafeToString((byte[]) obj);
		}
		if (obj instanceof char[]) {
			return nullSafeToString((char[]) obj);
		}
		if (obj instanceof double[]) {
			return nullSafeToString((double[]) obj);
		}
		if (obj instanceof float[]) {
			return nullSafeToString((float[]) obj);
		}
		if (obj instanceof int[]) {
			return nullSafeToString((int[]) obj);
		}
		if (obj instanceof long[]) {
			return nullSafeToString((long[]) obj);
		}
		if (obj instanceof short[]) {
			return nullSafeToString((short[]) obj);
		}
		String str = obj.toString();
		return (str != null ? str : EMPTY_STRING);
	}

	public static String nullSafeToString(Object[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append(String.valueOf(array[i]));
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	public static String nullSafeToString(boolean[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}

			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	public static String nullSafeToString(byte[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	public static String nullSafeToString(char[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append("'").append(array[i]).append("'");
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	public static String nullSafeToString(double[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}

			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	public static String nullSafeToString(float[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}

			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	public static String nullSafeToString(int[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	public static String nullSafeToString(long[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	public static String nullSafeToString(short[] array) {
		if (array == null) {
			return NULL_STRING;
		}
		int length = array.length;
		if (length == 0) {
			return EMPTY_ARRAY;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				buffer.append(ARRAY_START);
			} else {
				buffer.append(ARRAY_ELEMENT_SEPARATOR);
			}
			buffer.append(array[i]);
		}
		buffer.append(ARRAY_END);
		return buffer.toString();
	}

	public static Object cloneObject(Object source) throws Exception {
		Object result = null;

		if (source instanceof String) {
			result = new String((String) source);
		} else if (source instanceof Integer) {
			result = new Integer(((Integer) source).intValue());
		} else if (source instanceof Double) {
			result = new Double(((Double) source).doubleValue());
		} else if (source instanceof Long) {
			result = new Long(((Long) source).longValue());
		} else if (source instanceof Short) {
			result = new Short(((Short) source).shortValue());
		} else if (source instanceof Boolean) {
			result = new Boolean((Boolean) source);
		} else if (source instanceof Character) {
			result = new Character(((Character) source).charValue());
		} else if (source instanceof Float) {
			result = new Float(((Float) source).floatValue());
		} else if (source instanceof Byte) {
			result = new Byte(((Byte) source).byteValue());
		} else if (source instanceof BigInteger) {
			result = new BigInteger(((BigInteger) source).toByteArray());
		} else if (source instanceof BigDecimal) {
			result = new BigDecimal(((BigDecimal) source).toString());
		} else if (source instanceof Blob) {
			result =  IOUtils.toByteArray(((Blob)source).getBinaryStream());
		} else if (source instanceof Clob) {
			result = IOUtils.toByteArray(((Blob)source).getBinaryStream());
		} else if (source instanceof NClob) {
			result = IOUtils.toByteArray(((Blob)source).getBinaryStream());
		} else if (source instanceof byte[]) {
			result = source;
		} else if (source instanceof Map) {
			result = new HashMap<Object, Object>();
			Iterator<?> it = ((Map<?, ?>) source).keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				((Map<Object, Object>) result).put(key, cloneObject(((Map<?, ?>) source).get(key)));
			}
		} else if (source instanceof List) {
			result = new ArrayList<Object>();
			for (Object value : (List<?>) source) {
				((List) result).add(cloneObject(value));
			}
		} else if (source instanceof Set) {
			result = new HashSet<Object>();
			for (Object value : (Set) source) {
				((Set) result).add(cloneObject(value));
			}
		} else if (source instanceof Date) {
			result = new Date(((Date) source).getTime());
		} else if (source instanceof Enum) {
			result = new String(((Enum)source).name());
		}

		return result;
	}

	public static boolean isValueModified(Object source, Object target) {
		if (source instanceof String) {
			return ((String) source).compareTo((String) target) != 0;
		} else if (source instanceof Integer) {
			return ((Integer) source).compareTo((Integer) target) != 0;
		} else if (source instanceof Double) {
			return ((Double) source).compareTo((Double) target) != 0;
		} else if (source instanceof Long) {
			return ((Long) source).compareTo((Long) target) != 0;
		} else if (source instanceof Short) {
			return ((Short) source).compareTo((Short) target) != 0;
		} else if (source instanceof Boolean) {
			return ((Boolean) source).compareTo((Boolean) target) != 0;
		} else if (source instanceof Character) {
			return ((Character) source).compareTo((Character) target) != 0;
		} else if (source instanceof Float) {
			return ((Float) source).compareTo((Float) target) != 0;
		} else if (source instanceof Byte) {
			return ((Byte) source).compareTo((Byte) target) != 0;
		} else if (source instanceof BigInteger) {
			return ((BigInteger) source).compareTo((BigInteger) target) != 0;
		} else if (source instanceof BigDecimal) {
			return ((BigDecimal) source).compareTo((BigDecimal) target) != 0;
		} else if (source instanceof Enum) {
			return ((Enum) source).compareTo((Enum) target) != 0;
		} else if (source instanceof byte[]) {
			return Arrays.equals((byte[]) source, (byte[]) target);
		}
		return false;
	}

	public static String convertObjectToString(Object object) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(baos);
		out.writeObject(object);
		return Base64.encodeBytes(baos.toByteArray());
	}

	public static Object convertStringToObject(String s) throws Exception {
		byte[] buf = Base64.decode(s);
		if (buf != null) {
			ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
			return objectIn.readObject();
		}
		return null;
	}

	public static Object convert(Object value, Class<?> type) throws Exception {
		if (value instanceof Clob) {
			if (type == Clob.class)
				return value;

			Clob lob = ((Clob) value);
			String s = lob.getSubString(new Long(1).longValue(), (int) lob.length());
			if (type == char[].class) {
				return s.toCharArray();
			} else if (type == Character[].class) {
				return ArrayUtils.toObject(s.toCharArray());
			} else if (type == String.class) {
				return s;
			} else if (type == byte[].class) {
				return s.getBytes();
			} else if (type == Byte[].class) {
				return ArrayUtils.toObject(s.getBytes());
			}
		} else if (value instanceof NClob) {
			if (type == NClob.class)
				return value;

			NClob lob = ((NClob) value);
			String s = lob.getSubString(new Long(1).longValue(), (int) lob.length());
			if (type == char[].class) {
				return s.toCharArray();
			} else if (type == Character[].class) {
				return ArrayUtils.toObject(s.toCharArray());
			} else if (type == String.class) {
				return s;
			} else if (type == byte[].class) {
				return s.getBytes();
			} else if (type == Byte[].class) {
				return ArrayUtils.toObject(s.getBytes());
			}
		} else if (value instanceof Blob) {
			if (type == Blob.class)
				return value;
			InputStream in = ((Blob) value).getBinaryStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while ((n = in.read(buf)) >= 0) {
				baos.write(buf, 0, n);
			}
			in.close();
			byte[] bytes = baos.toByteArray();

			if (type == byte[].class) {
				return bytes;
			} else if (type == Byte[].class) {
				return ArrayUtils.toObject(bytes);
			} else if (ReflectionUtils.isImplementsInterface(type, Serializable.class)) {
				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
				return ois.readObject();
			}
		} else
			return (value == null ? null : ConversionHelper.getInstance().convert(value, type));

		return null;
	}

	public static Object deepCopy(Object oldObj) {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(oldObj);
			oos.flush();
			ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bin);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace(System.err);
			}
		}
	}

	public static Byte[] toByteArray(byte[] value) {
		Byte[] bytes = new Byte[((byte[]) value).length];
		for (int i = 0; i < ((byte[]) value).length; i++)
			bytes[i] = new Byte(((byte[]) value)[i]);

		return bytes;
	}

	public static byte[] toPrimitiveByteArray(Byte[] value) {
		byte[] bytes = new byte[value.length];
		for (int i = 0; i < value.length; i++)
			bytes[i] = value[i].byteValue();
		return bytes;
	}

	public static char[] toPrimitiveCharacterArray(byte[] value) {
		return new String(value).toCharArray();
	}

	public static Character[] toCharacterArray(byte[] value) {
		char[] c = new String(value).toCharArray();
		Character[] chars = new Character[value.length];
		for (int i = 0; i < value.length; i++)
			chars[i] = new Character(c[i]);
		return chars;
	}

	public static <T> T defaultIfNull(T object, T defaultValue) {
		return object != null ? object : defaultValue;
	}

	public static boolean equals(Object object1, Object object2) {
		if (object1 == object2) {
			return true;
		}
		if ((object1 == null) || (object2 == null)) {
			return false;
		}
		return object1.equals(object2);
	}

}
