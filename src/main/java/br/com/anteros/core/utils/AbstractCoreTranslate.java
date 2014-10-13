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
	
	private static AbstractCoreTranslate singleton; 
    private static AbstractCoreTranslate getInstance() {
        if ( singleton == null )
            throw new RuntimeException("Nenhuma implementação de AbstractCoreTranslate foi configurada.");

        return singleton;
    }    

    public static void setInstance(AbstractCoreTranslate instance) {
    	AbstractCoreTranslate.singleton = instance;
    }
	
	
	public AbstractCoreTranslate(String messageBundleName) {
		this.bundleName = messageBundleName;
	}
	
	public static ResourceBundle getResourceBundle(Locale locale) { 
        return getInstance().getResourceBundleImpl(locale);
    }
	
	protected  ResourceBundle getResourceBundleImpl(Locale locale) {
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
	
	public static  ResourceBundle getResourceBundle() {
		return getInstance().getResourceBundleImpl(Locale.getDefault());
	}

	protected  ResourceBundle getResourceBundleImpl() {
		return getResourceBundle(Locale.getDefault());
	}
	
	public static  String getMessage(Class<?> clazz, String tag, Object... arguments) {
		return MessageFormat.format(getInstance().getMessageImpl(clazz, tag), arguments);
	}

	protected  String getMessageImpl(Class<?> clazz, String tag, Object... arguments) {
		return MessageFormat.format(getMessageImpl(clazz, tag), arguments);
	}

	public static  String getMessage(Class<?> clazz, String tag) {
		return getInstance().getResourceBundleImpl().getString(clazz.getSimpleName() + "." + tag);
	}
	
	protected  String getMessageImpl(Class<?> clazz, String tag) {
		return getResourceBundleImpl().getString(clazz.getSimpleName() + "." + tag);
	}
}
