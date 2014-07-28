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
package br.com.anteros.core.converter;

import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;

import br.com.anteros.core.converter.converters.ArrayConverter;
import br.com.anteros.core.converter.converters.BigDecimalConverter;
import br.com.anteros.core.converter.converters.BigIntegerConverter;
import br.com.anteros.core.converter.converters.BooleanConverter;
import br.com.anteros.core.converter.converters.ByteConverter;
import br.com.anteros.core.converter.converters.CalendarConverter;
import br.com.anteros.core.converter.converters.CharacterConverter;
import br.com.anteros.core.converter.converters.ClassConverter;
import br.com.anteros.core.converter.converters.ConverterFacade;
import br.com.anteros.core.converter.converters.DateConverter;
import br.com.anteros.core.converter.converters.DoubleConverter;
import br.com.anteros.core.converter.converters.FileConverter;
import br.com.anteros.core.converter.converters.FloatConverter;
import br.com.anteros.core.converter.converters.IntegerConverter;
import br.com.anteros.core.converter.converters.LongConverter;
import br.com.anteros.core.converter.converters.ShortConverter;
import br.com.anteros.core.converter.converters.SqlDateConverter;
import br.com.anteros.core.converter.converters.SqlTimeConverter;
import br.com.anteros.core.converter.converters.SqlTimestampConverter;
import br.com.anteros.core.converter.converters.StringConverter;
import br.com.anteros.core.converter.converters.URLConverter;

public class ConversionHelper {

	private WeakFastHashMap converters = new WeakFastHashMap();
	private static final Integer ZERO = new Integer(0);
	private static final Character SPACE = new Character(' ');
	private static ConversionHelper conversionHelper;

	private ConversionHelper() {
		converters.setFast(false);
		deRegister();
		converters.setFast(true);
	}

	public static ConversionHelper getInstance() {
		if (conversionHelper == null)
			conversionHelper = new ConversionHelper();
		return conversionHelper;
	}

	public Object convert(Object value, Class<?> targetType) throws Exception {

		Class<?> sourceType = value == null ? null : value.getClass();
		Object converted = value;
		Converter converter = lookup(sourceType, targetType);
		if (converter != null) {
			converted = converter.convert(targetType, value);
		}
		if (targetType == String.class && converted != null && !(converted instanceof String)) {
			converter = lookup(String.class);
			if (converter != null) {
				converted = converter.convert(String.class, converted);
			}
			if (converted != null && !(converted instanceof String)) {
				converted = converted.toString();
			}
		}
		return converted;
	}

	public Object convert(String value, Class<?> clazz) throws Throwable {
		Converter converter = lookup(clazz);
		if (converter == null) {
			converter = lookup(String.class);
		}
		return (converter.convert(clazz, value));
	}

	public Object convert(String[] values, Class<?> clazz) throws Throwable {
		Class<?> type = clazz;
		if (clazz.isArray()) {
			type = clazz.getComponentType();
		}
		Converter converter = lookup(type);
		if (converter == null) {
			converter = lookup(String.class);
		}
		Object array = Array.newInstance(type, values.length);
		for (int i = 0; i < values.length; i++) {
			Array.set(array, i, converter.convert(type, values[i]));
		}
		return (array);
	}

	public Converter lookup(Class<?> clazz) {

		return ((Converter) converters.get(clazz));

	}

	public Converter lookup(Class<?> sourceType, Class<?> targetType) {
		if (targetType == null) {
			throw new IllegalArgumentException("Target type is missing");
		}
		if (sourceType == null) {
			return lookup(targetType);
		}

		Converter converter = null;
		if (targetType == String.class) {
			converter = lookup(sourceType);
			if (converter == null && (sourceType.isArray() || Collection.class.isAssignableFrom(sourceType))) {
				converter = lookup(String[].class);
			}
			if (converter == null) {
				converter = lookup(String.class);
			}
			return converter;
		}

		if (targetType == String[].class) {
			if (sourceType.isArray() || Collection.class.isAssignableFrom(sourceType)) {
				converter = lookup(sourceType);
			}
			if (converter == null) {
				converter = lookup(String[].class);
			}
			return converter;
		}
		return lookup(targetType);
	}

	public void register(Converter converter, Class<?> clazz) {
		converters.put(clazz, converter);
	}

	public void deregister(Class<?> clazz) {
		converters.remove(clazz);
	}

	private void registerConverter(Class<?> clazz, Converter converter) {
		register(new ConverterFacade(converter), clazz);
	}

	private void registerArraysConverters(boolean throwException, int defaultArraySize) {
		registerArrayConverter(Boolean.TYPE, new BooleanConverter(), throwException, defaultArraySize);
		registerArrayConverter(Byte.TYPE, new ByteConverter(), throwException, defaultArraySize);
		registerArrayConverter(Character.TYPE, new CharacterConverter(), throwException, defaultArraySize);
		registerArrayConverter(Double.TYPE, new DoubleConverter(), throwException, defaultArraySize);
		registerArrayConverter(Float.TYPE, new FloatConverter(), throwException, defaultArraySize);
		registerArrayConverter(Integer.TYPE, new IntegerConverter(), throwException, defaultArraySize);
		registerArrayConverter(Long.TYPE, new LongConverter(), throwException, defaultArraySize);
		registerArrayConverter(Short.TYPE, new ShortConverter(), throwException, defaultArraySize);

		registerArrayConverter(BigDecimal.class, new BigDecimalConverter(), throwException, defaultArraySize);
		registerArrayConverter(BigInteger.class, new BigIntegerConverter(), throwException, defaultArraySize);
		registerArrayConverter(Boolean.class, new BooleanConverter(), throwException, defaultArraySize);
		registerArrayConverter(Byte.class, new ByteConverter(), throwException, defaultArraySize);
		registerArrayConverter(Character.class, new CharacterConverter(), throwException, defaultArraySize);
		registerArrayConverter(Double.class, new DoubleConverter(), throwException, defaultArraySize);
		registerArrayConverter(Float.class, new FloatConverter(), throwException, defaultArraySize);
		registerArrayConverter(Integer.class, new IntegerConverter(), throwException, defaultArraySize);
		registerArrayConverter(Long.class, new LongConverter(), throwException, defaultArraySize);
		registerArrayConverter(Short.class, new ShortConverter(), throwException, defaultArraySize);
		registerArrayConverter(String.class, new StringConverter(), throwException, defaultArraySize);

		registerArrayConverter(Class.class, new ClassConverter(), throwException, defaultArraySize);
		registerArrayConverter(java.util.Date.class, new DateConverter(), throwException, defaultArraySize);
		registerArrayConverter(Calendar.class, new DateConverter(), throwException, defaultArraySize);
		registerArrayConverter(File.class, new FileConverter(), throwException, defaultArraySize);
		registerArrayConverter(java.sql.Date.class, new SqlDateConverter(), throwException, defaultArraySize);
		registerArrayConverter(java.sql.Time.class, new SqlTimeConverter(), throwException, defaultArraySize);
		registerArrayConverter(Timestamp.class, new SqlTimestampConverter(), throwException, defaultArraySize);
		registerArrayConverter(URL.class, new URLConverter(), throwException, defaultArraySize);
	}

	private void registerPrimitivesConverters(boolean throwException) {
		registerConverter(Boolean.TYPE, throwException ? new BooleanConverter() : new BooleanConverter(Boolean.FALSE));
		registerConverter(Byte.TYPE, throwException ? new ByteConverter() : new ByteConverter(ZERO));
		registerConverter(Character.TYPE, throwException ? new CharacterConverter() : new CharacterConverter(SPACE));
		registerConverter(Double.TYPE, throwException ? new DoubleConverter() : new DoubleConverter(ZERO));
		registerConverter(Float.TYPE, throwException ? new FloatConverter() : new FloatConverter(ZERO));
		registerConverter(Integer.TYPE, throwException ? new IntegerConverter() : new IntegerConverter(ZERO));
		registerConverter(Long.TYPE, throwException ? new LongConverter() : new LongConverter(ZERO));
		registerConverter(Short.TYPE, throwException ? new ShortConverter() : new ShortConverter(ZERO));
	}

	private void registerOthersConverters(boolean throwException) {
		registerConverter(Class.class, throwException ? new ClassConverter() : new ClassConverter(null));
		registerConverter(java.util.Date.class, throwException ? new DateConverter() : new DateConverter(null));
		registerConverter(Calendar.class, throwException ? new CalendarConverter() : new CalendarConverter(null));
		registerConverter(File.class, throwException ? new FileConverter() : new FileConverter(null));
		registerConverter(java.sql.Date.class, throwException ? new SqlDateConverter() : new SqlDateConverter(null));
		registerConverter(java.sql.Time.class, throwException ? new SqlTimeConverter() : new SqlTimeConverter(null));
		registerConverter(Timestamp.class, throwException ? new SqlTimestampConverter() : new SqlTimestampConverter(null));
		registerConverter(URL.class, throwException ? new URLConverter() : new URLConverter(null));
	}

	private void registerStandardsConverters(boolean throwException, boolean defaultNull) {

		Number defaultNumber = defaultNull ? null : ZERO;
		BigDecimal bigDecDeflt = defaultNull ? null : new BigDecimal("0.0");
		BigInteger bigIntDeflt = defaultNull ? null : new BigInteger("0");
		Boolean booleanDefault = defaultNull ? null : Boolean.FALSE;
		Character charDefault = defaultNull ? null : SPACE;
		String stringDefault = defaultNull ? null : "";

		registerConverter(BigDecimal.class, throwException ? new BigDecimalConverter() : new BigDecimalConverter(bigDecDeflt));
		registerConverter(BigInteger.class, throwException ? new BigIntegerConverter() : new BigIntegerConverter(bigIntDeflt));
		registerConverter(Boolean.class, throwException ? new BooleanConverter() : new BooleanConverter(booleanDefault));
		registerConverter(Byte.class, throwException ? new ByteConverter() : new ByteConverter(defaultNumber));
		registerConverter(Character.class, throwException ? new CharacterConverter() : new CharacterConverter(charDefault));
		registerConverter(Double.class, throwException ? new DoubleConverter() : new DoubleConverter(defaultNumber));
		registerConverter(Float.class, throwException ? new FloatConverter() : new FloatConverter(defaultNumber));
		registerConverter(Integer.class, throwException ? new IntegerConverter() : new IntegerConverter(defaultNumber));
		registerConverter(Long.class, throwException ? new LongConverter() : new LongConverter(defaultNumber));
		registerConverter(Short.class, throwException ? new ShortConverter() : new ShortConverter(defaultNumber));
		registerConverter(String.class, throwException ? new StringConverter() : new StringConverter(stringDefault));
	}

	public void register(boolean throwException, boolean defaultNull, int defaultArraySize) {
		registerPrimitivesConverters(throwException);
		registerStandardsConverters(throwException, defaultNull);
		registerOthersConverters(throwException);
		registerArraysConverters(throwException, defaultArraySize);
	}

	public void deRegister() {
		converters.clear();
		registerPrimitivesConverters(false);
		registerStandardsConverters(false, false);
		registerOthersConverters(true);
		registerArraysConverters(false, 0);
		registerConverter(BigDecimal.class, new BigDecimalConverter());
		registerConverter(BigInteger.class, new BigIntegerConverter());
	}

	private void registerArrayConverter(Class<?> componentType, Converter componentConverter, boolean throwException, int defaultArraySize) {
		Class<?> arrayType = Array.newInstance(componentType, 0).getClass();
		Converter arrayConverter = null;
		if (throwException) {
			arrayConverter = new ArrayConverter(arrayType, componentConverter);
		} else {
			arrayConverter = new ArrayConverter(arrayType, componentConverter, defaultArraySize);
		}
		registerConverter(arrayType, arrayConverter);
	}
}
