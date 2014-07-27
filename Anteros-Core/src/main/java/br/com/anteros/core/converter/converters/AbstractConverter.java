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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;

import br.com.anteros.core.converter.Converter;
import br.com.anteros.core.converter.exception.AnterosConversionException;

public abstract class AbstractConverter implements Converter {

	private boolean useDefault = false;

	private Object defaultValue = null;

	public AbstractConverter() {
	}

	public AbstractConverter(Object defaultValue) {
		try {
			setDefaultValue(defaultValue);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public boolean isUseDefault() {
		return useDefault;
	}

	public Object convert(Class<?> type, Object value) throws Exception {

		Class<?> sourceType = value == null ? null : value.getClass();
		Class<?> targetType = primitive(type == null ? getDefaultType() : type);

		value = convertArray(value);

		if (value == null) {
			return handleMissing(targetType);
		}

		sourceType = value.getClass();

		try {
			if (targetType.equals(String.class)) {
				return convertToString(value);
			} else if (targetType.equals(sourceType)) {
				return value;
			} else {
				Object result = convertToType(targetType, value);
				return result;
			}
		} catch (Throwable t) {
			return handleError(targetType, value, t);
		}

	}

	protected String convertToString(Object value) throws Throwable {
		return value.toString();
	}

	protected abstract Object convertToType(Class<?> type, Object value) throws Throwable;

	protected Object convertArray(Object value) {
		if (value == null) {
			return null;
		}
		if (value.getClass().isArray()) {
			if (Array.getLength(value) > 0) {
				return Array.get(value, 0);
			} else {
				return null;
			}
		}
		if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>) value;
			if (collection.size() > 0) {
				return collection.iterator().next();
			} else {
				return null;
			}
		}
		return value;
	}

	protected Object handleError(Class<?> type, Object value, Throwable cause) throws Exception {
		if (useDefault) {
			return handleMissing(type);
		}

		AnterosConversionException cex = null;
		if (cause instanceof AnterosConversionException) {
			cex = (AnterosConversionException) cause;
		} else {
			String msg = "Error converting from '" + toString(value.getClass()) + "' to '" + toString(type) + "' " + cause.getMessage();
			cex = new AnterosConversionException(msg + cause.getMessage());
			initCause(cex, cause);
		}

		throw cex;

	}

	public boolean initCause(Throwable throwable, Throwable cause) {
		if (getInitCauseMethod() != null && cause != null) {
			try {
				getInitCauseMethod().invoke(throwable, new Object[] { cause });
				return true;
			} catch (Throwable e) {
				return false;
			}
		}
		return false;
	}

	private static Method getInitCauseMethod() {
		try {
			Class<?>[] paramsClasses = new Class<?>[] { Throwable.class };
			return Throwable.class.getMethod("initCause", paramsClasses);
		} catch (NoSuchMethodException e) {
			return null;
		} catch (Throwable e) {
			return null;
		}
	}

	protected Object handleMissing(Class<?> type) throws AnterosConversionException {

		if (useDefault || type.equals(String.class)) {
			Object value = getDefault(type);
			if (useDefault && value != null && !(type.equals(value.getClass()))) {
				try {
					value = convertToType(type, defaultValue);
				} catch (Throwable t) {
				}
			}
			return value;
		}

		AnterosConversionException cex = new AnterosConversionException("No value specified for '" + toString(type) + "'");
		throw cex;

	}

	protected void setDefaultValue(Object defaultValue) throws Throwable {
		useDefault = false;
		if (defaultValue == null) {
			this.defaultValue = null;
		} else {
			this.defaultValue = convert(getDefaultType(), defaultValue);
		}
		useDefault = true;
	}

	protected abstract Class<?> getDefaultType();

	protected Object getDefault(Class<?> type) {
		if (type.equals(String.class)) {
			return null;
		} else {
			return defaultValue;
		}
	}

	public String toString() {
		return toString(getClass()) + "[UseDefault=" + useDefault + "]";
	}

	protected Class<?> primitive(Class<?> type) {
		if (type == null || !type.isPrimitive()) {
			return type;
		}
		if (type == Integer.TYPE) {
			return Integer.class;
		} else if (type == Double.TYPE) {
			return Double.class;
		} else if (type == Long.TYPE) {
			return Long.class;
		} else if (type == Boolean.TYPE) {
			return Boolean.class;
		} else if (type == Float.TYPE) {
			return Float.class;
		} else if (type == Short.TYPE) {
			return Short.class;
		} else if (type == Byte.TYPE) {
			return Byte.class;
		} else if (type == Character.TYPE) {
			return Character.class;
		} else {
			return type;
		}
	}

	protected String toString(Class<?> type) {
		String typeName = null;
		if (type == null) {
			typeName = "null";
		} else if (type.isArray()) {
			Class<?> elementType = type.getComponentType();
			int count = 1;
			while (elementType.isArray()) {
				elementType = elementType.getComponentType();
				count++;
			}
			typeName = elementType.getName();
			for (int i = 0; i < count; i++) {
				typeName += "[]";
			}
		} else {
			typeName = type.getName();
		}
		if (typeName.startsWith("java.lang.") || typeName.startsWith("java.util.") || typeName.startsWith("java.math.")) {
			typeName = typeName.substring("java.lang.".length());
		}
		return typeName;
	}
}
