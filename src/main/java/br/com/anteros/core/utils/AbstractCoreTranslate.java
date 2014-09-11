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
