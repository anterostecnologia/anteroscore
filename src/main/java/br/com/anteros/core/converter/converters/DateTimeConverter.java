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

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import br.com.anteros.core.converter.exception.AnterosConversionException;

public abstract class DateTimeConverter extends AbstractConverter {

	private String[] patterns;
	private String displayPatterns;
	private Locale locale;
	private TimeZone timeZone;
	private boolean useLocaleFormat;

	public DateTimeConverter() {
		super();
	}
	
	public DateTimeConverter(Object defaultValue) {
        super(defaultValue);
    }

	public void setUseLocaleFormat(boolean useLocaleFormat) {
		this.useLocaleFormat = useLocaleFormat;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
		setUseLocaleFormat(true);
	}

	public void setPattern(String pattern) {
		setPatterns(new String[] { pattern });
	}

	public String[] getPatterns() {
		return patterns;
	}

	public void setPatterns(String[] patterns) {
		this.patterns = patterns;
		if (patterns != null && patterns.length > 1) {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < patterns.length; i++) {
				if (i > 0) {
					buffer.append(", ");
				}
				buffer.append(patterns[i]);
			}
			displayPatterns = buffer.toString();
		}
		setUseLocaleFormat(true);
	}

	protected String convertToString(Object value) throws Throwable {
		Date date = null;
		if (value instanceof Date) {
			date = (Date) value;
		} else if (value instanceof Calendar) {
			date = ((Calendar) value).getTime();
		} else if (value instanceof Long) {
			date = new Date(((Long) value).longValue());
		}

		String result = null;
		if (useLocaleFormat && date != null) {
			DateFormat format = null;
			if (patterns != null && patterns.length > 0) {
				format = getFormat(patterns[0]);
			} else {
				format = getFormat(locale, timeZone);
			}
			result = format.format(date);
		} else {
			result = value.toString();
		}
		return result;
	}

	protected Object convertToType(Class<?> targetType, Object value) throws Throwable {
		Class<?> sourceType = value.getClass();
		if (value instanceof java.sql.Timestamp) {
			java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
			long timeInMillis = ((timestamp.getTime() / 1000) * 1000);
			timeInMillis += timestamp.getNanos() / 1000000;
			return toDate(targetType, timeInMillis);
		}

		if (value instanceof Date) {
			Date date = (Date) value;
			return toDate(targetType, date.getTime());
		}

		if (value instanceof Calendar) {
			Calendar calendar = (Calendar) value;
			return toDate(targetType, calendar.getTime().getTime());
		}

		if (value instanceof Long) {
			Long longObj = (Long) value;
			return toDate(targetType, longObj.longValue());
		}

		String stringValue = value.toString().trim();
		if (stringValue.length() == 0) {
			return handleMissing(targetType);
		}

		if (useLocaleFormat) {
			Calendar calendar = null;
			if (patterns != null && patterns.length > 0) {
				calendar = parse(sourceType, targetType, stringValue);
			} else {
				DateFormat format = getFormat(locale, timeZone);
				calendar = parse(sourceType, targetType, stringValue, format);
			}
			if (Calendar.class.isAssignableFrom(targetType)) {
				return calendar;
			} else {
				return toDate(targetType, calendar.getTime().getTime());
			}
		}

		return toDate(targetType, stringValue);

	}

	private Object toDate(Class<?> type, long value) throws Throwable {

		if (type.equals(Date.class)) {
			return new Date(value);
		}

		if (type.equals(java.sql.Date.class)) {
			return new java.sql.Date(value);
		}

		if (type.equals(java.sql.Time.class)) {
			return new java.sql.Time(value);
		}

		if (type.equals(java.sql.Timestamp.class)) {
			return new java.sql.Timestamp(value);
		}

		if (type.equals(Calendar.class)) {
			Calendar calendar = null;
			if (locale == null && timeZone == null) {
				calendar = Calendar.getInstance();
			} else if (locale == null) {
				calendar = Calendar.getInstance(timeZone);
			} else if (timeZone == null) {
				calendar = Calendar.getInstance(locale);
			} else {
				calendar = Calendar.getInstance(timeZone, locale);
			}
			calendar.setTime(new Date(value));
			calendar.setLenient(false);
			return calendar;
		}
		String msg = toString(getClass()) + " cannot handle conversion to '" + toString(type) + "'";
		throw new AnterosConversionException(msg);
	}

	private Object toDate(Class<?> type, String value) throws Throwable {
		if (type.equals(java.sql.Date.class)) {
			try {
				return java.sql.Date.valueOf(value);
			} catch (IllegalArgumentException e) {
				throw new AnterosConversionException("String must be in JDBC format [yyyy-MM-dd] to create a java.sql.Date");
			}
		}

		if (type.equals(java.sql.Time.class)) {
			try {
				return java.sql.Time.valueOf(value);
			} catch (IllegalArgumentException e) {
				throw new AnterosConversionException("String must be in JDBC format [HH:mm:ss] to create a java.sql.Time");
			}
		}

		if (type.equals(java.sql.Timestamp.class)) {
			try {
				return java.sql.Timestamp.valueOf(value);
			} catch (IllegalArgumentException e) {
				throw new AnterosConversionException("String must be in JDBC format [yyyy-MM-dd HH:mm:ss.fffffffff] " + "to create a java.sql.Timestamp");
			}
		}
		String msg = toString(getClass()) + " does not support default String to '" + toString(type) + "' conversion.";
		throw new AnterosConversionException(msg);
	}

	protected DateFormat getFormat(Locale locale, TimeZone timeZone) {
		DateFormat format = null;
		if (locale == null) {
			format = DateFormat.getDateInstance(DateFormat.SHORT);
		} else {
			format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		}
		if (timeZone != null) {
			format.setTimeZone(timeZone);
		}
		return format;
	}

	private DateFormat getFormat(String pattern) {
		DateFormat format = new SimpleDateFormat(pattern);
		if (timeZone != null) {
			format.setTimeZone(timeZone);
		}
		return format;
	}

	private Calendar parse(Class<?> sourceType, Class<?> targetType, String value) throws Throwable {
		Exception firstEx = null;
		for (int i = 0; i < patterns.length; i++) {
			try {
				DateFormat format = getFormat(patterns[i]);
				Calendar calendar = parse(sourceType, targetType, value, format);
				return calendar;
			} catch (Exception ex) {
				if (firstEx == null) {
					firstEx = ex;
				}
			}
		}
		if (patterns.length > 1) {
			throw new AnterosConversionException("Error converting '" + toString(sourceType) + "' to '" + toString(targetType) + "' using  patterns '"
					+ displayPatterns + "'");
		} else {
			throw firstEx;
		}
	}

	private Calendar parse(Class<?> sourceType, Class<?> targetType, String value, DateFormat format) throws Throwable {
		format.setLenient(false);
		ParsePosition pos = new ParsePosition(0);
		Date parsedDate = format.parse(value, pos);
		if (pos.getErrorIndex() >= 0 || pos.getIndex() != value.length() || parsedDate == null) {
			String msg = "Error converting '" + toString(sourceType) + "' to '" + toString(targetType) + "'";
			if (format instanceof SimpleDateFormat) {
				msg += " using pattern '" + ((SimpleDateFormat) format).toPattern() + "'";
			}
			throw new AnterosConversionException(msg);
		}
		Calendar calendar = format.getCalendar();
		return calendar;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(toString(getClass()));
		buffer.append("[UseDefault=");
		buffer.append(isUseDefault());
		buffer.append(", UseLocaleFormat=");
		buffer.append(useLocaleFormat);
		if (displayPatterns != null) {
			buffer.append(", Patterns={");
			buffer.append(displayPatterns);
			buffer.append('}');
		}
		if (locale != null) {
			buffer.append(", Locale=");
			buffer.append(locale);
		}
		if (timeZone != null) {
			buffer.append(", TimeZone=");
			buffer.append(timeZone);
		}
		buffer.append(']');
		return buffer.toString();
	}
}
