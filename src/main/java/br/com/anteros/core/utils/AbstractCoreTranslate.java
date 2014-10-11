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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class AbstractCoreTranslate {

	protected Map<Locale, ResourceBundle> bundles;

	protected String bundleName = "";
	
	public AbstractCoreTranslate(String messageBundleName) {
		this.bundleName = messageBundleName;
	}
	
	public  ResourceBundle getResourceBundle(Locale locale) {
		if (bundleName.equals(""))
			throw new RuntimeException("Variable bundleName not initialized. Use concrete clazz to translate. ");
		if (bundles == null)
			bundles = new HashMap<Locale, ResourceBundle>();

		ResourceBundle bundle = bundles.get(locale);

		if (bundle != null) {
			return bundle;
		}

		ClassLoader loader = AbstractCoreTranslate.class.getClassLoader();
		bundle = ResourceBundle.getBundle(bundleName, locale, loader);
		bundles.put(locale, bundle);

		return bundle;
	}

	public  ResourceBundle getResourceBundle() {
		return getResourceBundle(Locale.getDefault());
	}

	public  String getMessage(Class<?> clazz, String tag, Object... arguments) {
		return MessageFormat.format(getMessage(clazz, tag), arguments);
	}

	public  String getMessage(Class<?> clazz, String tag) {
		return getResourceBundle().getString(clazz.getSimpleName() + "." + tag);
	}
}
