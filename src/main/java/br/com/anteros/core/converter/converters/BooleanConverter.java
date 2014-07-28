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

import br.com.anteros.core.converter.exception.AnterosConversionException;

public final class BooleanConverter extends AbstractConverter {

	private String[] trueStrings = { "true", "yes", "y", "on", "1" };

	private String[] falseStrings = { "false", "no", "n", "off", "0" };

	public static final Object NO_DEFAULT = new Object();

	public BooleanConverter() {
		super();
	}

	public BooleanConverter(Object defaultValue) {
		super();
		if (defaultValue != NO_DEFAULT) {
			try {
				setDefaultValue(defaultValue);
			} catch (Throwable e) {
			}
		}
	}

	public BooleanConverter(String[] trueStrings, String[] falseStrings) {
		super();
		this.trueStrings = copyStrings(trueStrings);
		this.falseStrings = copyStrings(falseStrings);
	}

	public BooleanConverter(String[] trueStrings, String[] falseStrings, Object defaultValue) {
		super();
		this.trueStrings = copyStrings(trueStrings);
		this.falseStrings = copyStrings(falseStrings);
	}

	protected Class<?> getDefaultType() {
		return Boolean.class;
	}

	protected Object convertToType(Class<?> type, Object value) throws Throwable {
		String stringValue = value.toString().toLowerCase();
		for (int i = 0; i < trueStrings.length; ++i) {
			if (trueStrings[i].equals(stringValue)) {
				return Boolean.TRUE;
			}
		}
		for (int i = 0; i < falseStrings.length; ++i) {
			if (falseStrings[i].equals(stringValue)) {
				return Boolean.FALSE;
			}
		}

		throw new AnterosConversionException("Can't convert value '" + value + "' to a Boolean");
	}

	private static String[] copyStrings(String[] src) {
		String[] dst = new String[src.length];
		for (int i = 0; i < src.length; ++i) {
			dst[i] = src[i].toLowerCase();
		}
		return dst;
	}
}
