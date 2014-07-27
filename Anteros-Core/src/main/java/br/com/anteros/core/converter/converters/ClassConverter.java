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

public final class ClassConverter extends AbstractConverter {

	public ClassConverter() {
		super();
	}

	public ClassConverter(Object defaultValue) {
		super(defaultValue);
	}

	protected Class<?> getDefaultType() {
		return Class.class;
	}

	protected String convertToString(Object value) {
		return (value instanceof Class) ? ((Class<?>) value).getName() : value.toString();
	}

	protected Object convertToType(Class<?> type, Object value) throws Throwable {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			try {
				return (classLoader.loadClass(value.toString()));
			} catch (ClassNotFoundException ex) {
			}
		}
		classLoader = ClassConverter.class.getClassLoader();
		return (classLoader.loadClass(value.toString()));
	}

}
