/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *******************************************************************************/
package br.com.anteros.core.resource.messages;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import br.com.anteros.core.utils.TranslateCoreException;

public abstract class AnterosResourceBundle {

	protected Map<AnterosResourceBundleHolder, AnterosBundle> bundles;

	private static AnterosResourceBundle singleton;

	private static AnterosResourceBundle getInstance() {
		if (singleton == null)
			singleton = new AnterosResourceBundleImpl();
		return singleton;
	}

	protected AnterosBundle getInternalBundle(String holder, Locale locale, Class<? extends AnterosBundle> messages) {
		if (messages == null)
			throw new RuntimeException("Variable messages not initialized.");
		if (bundles == null)
			bundles = new ConcurrentHashMap<AnterosResourceBundleHolder, AnterosBundle>();

		AnterosBundle bundle = bundles.get(new AnterosResourceBundleHolder(holder, locale));

		if (bundle != null) {
			return bundle;
		}

		ClassLoader loader = AnterosResourceBundle.class.getClassLoader();
		String className = messages.getName() + "_" + locale.getLanguage() + locale.getCountry();
		Class<?> _messages = null;
		try {
			_messages = Class.forName(className, true, loader);
		} catch (ClassNotFoundException e) {
			_messages = messages;
		}

		try {
			bundle = (AnterosBundle) _messages.newInstance();
		} catch (Exception e) {
			throw new TranslateCoreException("Não foi possível instanciar a classe " + _messages.getName() + " de mensagens.", e);
		}

		bundles.put(new AnterosResourceBundleHolder(holder, locale), bundle);

		return bundle;
	}
	
	protected AnterosBundle getInternalBundle(String holder, Locale locale) {
		if (bundles == null)
			bundles = new ConcurrentHashMap<AnterosResourceBundleHolder, AnterosBundle>();
		return bundles.get(new AnterosResourceBundleHolder(holder, locale));
	}

	public static AnterosBundle getBundle(String holder, Class<? extends AnterosBundle> messages) {
		return getInstance().getInternalBundle(holder, Locale.getDefault(), messages);
	}

	public static AnterosBundle getBundle(String holder, Locale locale, Class<? extends AnterosBundle> messages) {
		return getInstance().getInternalBundle(holder, locale, messages);
	}
	
	public static AnterosBundle getBundle(String holder, Locale locale) {
		return getInstance().getInternalBundle(holder, locale);
	}

	public static void registerBundle(String holder, Locale locale, AnterosBundle bundle) {
		getInstance().bundles.put(new AnterosResourceBundleHolder(holder, locale), bundle);
	}

}
