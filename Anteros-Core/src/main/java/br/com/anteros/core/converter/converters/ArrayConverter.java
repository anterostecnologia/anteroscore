/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package br.com.anteros.core.converter.converters;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import br.com.anteros.core.converter.Converter;
import br.com.anteros.core.converter.exception.AnterosConversionException;

public class ArrayConverter extends AbstractConverter {

	private Object defaultTypeInstance;
	private Converter elementConverter;
	private int defaultSize;
	private char delimiter = ',';
	private char[] allowedChars = new char[] { '.', '-' };
	private boolean onlyFirstToString = true;

	public ArrayConverter(Class<?> defaultType, Converter elementConverter) {
		super();
		if (defaultType == null) {
			throw new IllegalArgumentException("Default type is missing");
		}
		if (!defaultType.isArray()) {
			throw new IllegalArgumentException("Default type must be an array.");
		}
		if (elementConverter == null) {
			throw new IllegalArgumentException("Component Converter is missing.");
		}
		this.defaultTypeInstance = Array.newInstance(defaultType.getComponentType(), 0);
		this.elementConverter = elementConverter;
	}

	public ArrayConverter(Class<?> defaultType, Converter elementConverter, int defaultSize) {
		this(defaultType, elementConverter);
		this.defaultSize = defaultSize;
		Object defaultValue = null;
		if (defaultSize >= 0) {
			defaultValue = Array.newInstance(defaultType.getComponentType(), defaultSize);
		}
		try {
			setDefaultValue(defaultValue);
		} catch (Throwable e) {
		}
	}

	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	public void setAllowedChars(char[] allowedChars) {
		this.allowedChars = allowedChars;
	}

	public void setOnlyFirstToString(boolean onlyFirstToString) {
		this.onlyFirstToString = onlyFirstToString;
	}

	protected Class<?> getDefaultType() {
		return defaultTypeInstance.getClass();
	}

	protected String convertToString(Object value) throws Throwable {

		int size = 0;
		Iterator<?> iterator = null;
		Class<?> type = value.getClass();
		if (type.isArray()) {
			size = Array.getLength(value);
		} else {
			Collection<?> collection = convertToCollection(type, value);
			size = collection.size();
			iterator = collection.iterator();
		}

		if (size == 0) {
			return (String) getDefault(String.class);
		}

		if (onlyFirstToString) {
			size = 1;
		}

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				buffer.append(delimiter);
			}
			Object element = iterator == null ? Array.get(value, i) : iterator.next();
			element = elementConverter.convert(String.class, element);
			if (element != null) {
				buffer.append(element);
			}
		}

		return buffer.toString();

	}

	protected Object convertToType(Class<?> type, Object value) throws Throwable {

		if (!type.isArray()) {
			throw new AnterosConversionException(toString(getClass()) + " cannot handle conversion to '" + toString(type) + "' (not an array).");
		}

		int size = 0;
		Iterator<?> iterator = null;
		if (value.getClass().isArray()) {
			size = Array.getLength(value);
		} else {
			Collection<?> collection = convertToCollection(type, value);
			size = collection.size();
			iterator = collection.iterator();
		}

		Class<?> componentType = type.getComponentType();
		Object newArray = Array.newInstance(componentType, size);

		for (int i = 0; i < size; i++) {
			Object element = iterator == null ? Array.get(value, i) : iterator.next();
			element = elementConverter.convert(componentType, element);
			Array.set(newArray, i, element);
		}

		return newArray;
	}

	protected Object convertArray(Object value) {
		return value;
	}

	protected Collection<?> convertToCollection(Class<?> type, Object value) throws Throwable {
		if (value instanceof Collection) {
			return (Collection<?>) value;
		}
		if (value instanceof Number || value instanceof Boolean || value instanceof java.util.Date) {
			List list = new ArrayList(1);
			list.add(value);
			return list;
		}

		return parseElements(type, value.toString());
	}

	protected Object getDefault(Class<?> type) {
		if (type.equals(String.class)) {
			return null;
		}

		Object defaultValue = super.getDefault(type);
		if (defaultValue == null) {
			return null;
		}

		if (defaultValue.getClass().equals(type)) {
			return defaultValue;
		} else {
			return Array.newInstance(type.getComponentType(), defaultSize);
		}

	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(toString(getClass()));
		buffer.append("[UseDefault=");
		buffer.append(isUseDefault());
		buffer.append(", ");
		buffer.append(elementConverter.toString());
		buffer.append(']');
		return buffer.toString();
	}

	private List<?> parseElements(Class<?> type, String value) throws Throwable {
		value = value.trim();
		if (value.startsWith("{") && value.endsWith("}")) {
			value = value.substring(1, value.length() - 1);
		}

		try {
			StreamTokenizer st = new StreamTokenizer(new StringReader(value));
			st.whitespaceChars(delimiter, delimiter);
			st.ordinaryChars('0', '9');
			st.wordChars('0', '9');
			for (int i = 0; i < allowedChars.length; i++) {
				st.ordinaryChars(allowedChars[i], allowedChars[i]);
				st.wordChars(allowedChars[i], allowedChars[i]);
			}
			List list = null;
			while (true) {
				int ttype = st.nextToken();
				if ((ttype == StreamTokenizer.TT_WORD) || (ttype > 0)) {
					if (st.sval != null) {
						if (list == null) {
							list = new ArrayList();
						}
						list.add(st.sval);
					}
				} else if (ttype == StreamTokenizer.TT_EOF) {
					break;
				} else {
					throw new AnterosConversionException("Encountered token of type " + ttype + " parsing elements to '" + toString(type) + ".");
				}
			}
			if (list == null) {
				list = Collections.EMPTY_LIST;
			}
			return (list);
		} catch (IOException e) {
			throw new AnterosConversionException("Error converting from String to '" + toString(type) + "': " + e.getMessage());
		}

	}

}
