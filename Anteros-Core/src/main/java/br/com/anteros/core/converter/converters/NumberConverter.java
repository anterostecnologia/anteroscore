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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.com.anteros.core.converter.exception.AnterosConversionException;

public abstract class NumberConverter extends AbstractConverter {

	private static final Integer ZERO = new Integer(0);
	private static final Integer ONE = new Integer(1);

	private String pattern;
	private boolean allowDecimals;
	private boolean useLocaleFormat;
	private Locale locale;

	public NumberConverter(boolean allowDecimals) {
		super();
		this.allowDecimals = allowDecimals;
	}

	public NumberConverter(boolean allowDecimals, Object defaultValue) {
		super();
		this.allowDecimals = allowDecimals;
		try {
			setDefaultValue(defaultValue);
		} catch (Throwable e) {
		}
	}

	public boolean isAllowDecimals() {
		return allowDecimals;
	}

	public void setUseLocaleFormat(boolean useLocaleFormat) {
		this.useLocaleFormat = useLocaleFormat;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
		setUseLocaleFormat(true);
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
		setUseLocaleFormat(true);
	}

	protected String convertToString(Object value) throws Throwable {

		String result = null;
		if (useLocaleFormat && value instanceof Number) {
			NumberFormat format = getFormat();
			format.setGroupingUsed(false);
			result = format.format(value);
		} else {
			result = value.toString();
		}
		return result;
	}

	protected Object convertToType(Class<?> targetType, Object value) throws Throwable {
		Class<?> sourceType = value.getClass();
		if (value instanceof Number) {
			return toNumber(sourceType, targetType, (Number) value);
		}

		if (value instanceof Boolean) {
			return toNumber(sourceType, targetType, ((Boolean) value).booleanValue() ? ONE : ZERO);
		}

		if (value instanceof Date && Long.class.equals(targetType)) {
			return new Long(((Date) value).getTime());
		}

		if (value instanceof Calendar && Long.class.equals(targetType)) {
			return new Long(((Calendar) value).getTime().getTime());
		}

		String stringValue = value.toString().trim();
		if (stringValue.length() == 0) {
			return handleMissing(targetType);
		}

		Number number = null;
		if (useLocaleFormat) {
			NumberFormat format = getFormat();
			number = parse(sourceType, targetType, stringValue, format);
		} else {
			number = toNumber(sourceType, targetType, stringValue);
		}

		return toNumber(sourceType, targetType, number);

	}

	private Number toNumber(Class<?> sourceType, Class<?> targetType, Number value) throws Throwable {
		if (targetType.equals(value.getClass())) {
			return value;
		}
		if (targetType.equals(Byte.class)) {
			long longValue = value.longValue();
			if (longValue > Byte.MAX_VALUE) {
				throw new AnterosConversionException(toString(sourceType) + " value '" + value + "' is too large for " + toString(targetType));
			}
			if (longValue < Byte.MIN_VALUE) {
				throw new AnterosConversionException(toString(sourceType) + " value '" + value + "' is too small " + toString(targetType));
			}
			return new Byte(value.byteValue());
		}
		if (targetType.equals(Short.class)) {
			long longValue = value.longValue();
			if (longValue > Short.MAX_VALUE) {
				throw new AnterosConversionException(toString(sourceType) + " value '" + value + "' is too large for " + toString(targetType));
			}
			if (longValue < Short.MIN_VALUE) {
				throw new AnterosConversionException(toString(sourceType) + " value '" + value + "' is too small " + toString(targetType));
			}
			return new Short(value.shortValue());
		}

		if (targetType.equals(Integer.class)) {
			long longValue = value.longValue();
			if (longValue > Integer.MAX_VALUE) {
				throw new AnterosConversionException(toString(sourceType) + " value '" + value + "' is too large for " + toString(targetType));
			}
			if (longValue < Integer.MIN_VALUE) {
				throw new AnterosConversionException(toString(sourceType) + " value '" + value + "' is too small " + toString(targetType));
			}
			return new Integer(value.intValue());
		}

		if (targetType.equals(Long.class)) {
			return new Long(value.longValue());
		}

		if (targetType.equals(Float.class)) {
			if (value.doubleValue() > Float.MAX_VALUE) {
				throw new AnterosConversionException(toString(sourceType) + " value '" + value + "' is too large for " + toString(targetType));
			}
			return new Float(value.floatValue());
		}

		if (targetType.equals(Double.class)) {
			return new Double(value.doubleValue());
		}

		if (targetType.equals(BigDecimal.class)) {
			if (value instanceof Float || value instanceof Double) {
				return new BigDecimal(value.toString());
			} else if (value instanceof BigInteger) {
				return new BigDecimal((BigInteger) value);
			} else {
				return BigDecimal.valueOf(value.longValue());
			}
		}

		if (targetType.equals(BigInteger.class)) {
			if (value instanceof BigDecimal) {
				return ((BigDecimal) value).toBigInteger();
			} else {
				return BigInteger.valueOf(value.longValue());
			}
		}

		String msg = toString(getClass()) + " cannot handle conversion to '" + toString(targetType) + "'";
		throw new AnterosConversionException(msg);
	}

	private Number toNumber(Class<?> sourceType, Class<?> targetType, String value) throws Throwable {

		if (targetType.equals(Byte.class)) {
			return new Byte(value);
		}

		if (targetType.equals(Short.class)) {
			return new Short(value);
		}

		if (targetType.equals(Integer.class)) {
			return new Integer(value);
		}

		if (targetType.equals(Long.class)) {
			return new Long(value);
		}

		if (targetType.equals(Float.class)) {
			return new Float(value);
		}

		if (targetType.equals(Double.class)) {
			return new Double(value);
		}

		if (targetType.equals(BigDecimal.class)) {
			return new BigDecimal(value);
		}

		if (targetType.equals(BigInteger.class)) {
			return new BigInteger(value);
		}

		String msg = toString(getClass()) + " cannot handle conversion from '" + toString(sourceType) + "' to '" + toString(targetType) + "'";
		throw new AnterosConversionException(msg);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(toString(getClass()));
		buffer.append("[UseDefault=");
		buffer.append(isUseDefault());
		buffer.append(", UseLocaleFormat=");
		buffer.append(useLocaleFormat);
		if (pattern != null) {
			buffer.append(", Pattern=");
			buffer.append(pattern);
		}
		if (locale != null) {
			buffer.append(", Locale=");
			buffer.append(locale);
		}
		buffer.append(']');
		return buffer.toString();
	}

	private NumberFormat getFormat() {
		NumberFormat format = null;
		if (pattern != null) {
			if (locale == null) {
				format = new DecimalFormat(pattern);
			} else {
				DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
				format = new DecimalFormat(pattern, symbols);
			}
		} else {
			if (locale == null) {
				format = NumberFormat.getInstance();
			} else {
				format = NumberFormat.getInstance(locale);
			}
		}
		if (!allowDecimals) {
			format.setParseIntegerOnly(true);
		}
		return format;
	}

	private Number parse(Class<?> sourceType, Class<?> targetType, String value, NumberFormat format) throws Throwable {
		ParsePosition pos = new ParsePosition(0);
		Number parsedNumber = format.parse(value, pos);
		if (pos.getErrorIndex() >= 0 || pos.getIndex() != value.length() || parsedNumber == null) {
			String msg = "Error converting from '" + toString(sourceType) + "' to '" + toString(targetType) + "'";
			if (format instanceof DecimalFormat) {
				msg += " using pattern '" + ((DecimalFormat) format).toPattern() + "'";
			}
			if (locale != null) {
				msg += " for locale=[" + locale + "]";
			}
		}
		return parsedNumber;
	}

}
