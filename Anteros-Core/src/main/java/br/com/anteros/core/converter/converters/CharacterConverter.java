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

public final class CharacterConverter extends AbstractConverter {

	public CharacterConverter() {
		super();
	}

	public CharacterConverter(Object defaultValue) {
		super(defaultValue);
	}

	protected Class<?> getDefaultType() {
		return Character.class;
	}

	protected String convertToString(Object value) {
		String strValue = value.toString();
		return strValue.length() == 0 ? "" : strValue.substring(0, 1);
	}

	protected Object convertToType(Class<?> type, Object value) throws Exception {
		return new Character(value.toString().charAt(0));
	}

}
