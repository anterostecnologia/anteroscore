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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

public final class SqlTimestampConverter extends DateTimeConverter {

	public SqlTimestampConverter() {
		super();
	}

	public SqlTimestampConverter(Object defaultValue) {
		super(defaultValue);
	}

	protected Class<?> getDefaultType() {
		return Timestamp.class;
	}

	protected DateFormat getFormat(Locale locale, TimeZone timeZone) {
		DateFormat format = null;
		if (locale == null) {
			format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		} else {
			format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
		}
		if (timeZone != null) {
			format.setTimeZone(timeZone);
		}
		return format;
	}
}
