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
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import br.com.anteros.core.converter.converters.ClassConverter;
import br.com.anteros.core.translation.TranslateMessage;

public abstract class AbstractCoreTranslate {

	protected Map<Locale, TranslateMessage> translators;

	protected Class<? extends TranslateMessage> translateClass;

	
	public AbstractCoreTranslate(Class<? extends TranslateMessage> translateClass) {
		this.translateClass = translateClass;
	}

	protected  TranslateMessage getTranslateMessage(Locale locale) {
		if (translateClass==null)
			throw new RuntimeException("Variable translateClass not initialized. Use concrete clazz to translate. ");
		if (translators == null)
			translators = new ConcurrentHashMap<Locale, TranslateMessage>();

		TranslateMessage translateMessage = translators.get(locale);

		if (translateMessage != null) {
			return translateMessage;
		}

		ClassLoader loader = AbstractCoreTranslate.class.getClassLoader();		
		String className = translateClass.getName()+"_"+locale.getLanguage()+locale.getCountry();
		Class<?> _translateClass = translateClass;
		try {
			_translateClass = Class.forName(className, true, loader);
		} catch (ClassNotFoundException e) {
		}

		try {
			translateMessage = (TranslateMessage) _translateClass.newInstance();
		} catch (Exception e) {
			throw new TranslateCoreException("Não foi possível instanciar a classe "+_translateClass.getName()+" de tradução.", e);
		}
		
		translators.put(locale, translateMessage);

		return translateMessage;
	}
	
	protected TranslateMessage getTranslateMessage() {
		return getTranslateMessage(Locale.getDefault());
	}
	
	public String getMessage(Class<?> clazz, String tag, Object... arguments) {
		return MessageFormat.format(getMessage(clazz, tag), arguments);
	}
	
	public String getMessage(Class<?> clazz, String tag) {
		return getTranslateMessage().getMessage(clazz.getSimpleName() + "." + tag);
	}

}
