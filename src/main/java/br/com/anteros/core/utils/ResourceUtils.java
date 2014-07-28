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
package br.com.anteros.core.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;

public class ResourceUtils {

	private static Logger LOG = LoggerProvider.getInstance().getLogger(ResourceUtils.class.getName());

	private static Map<Locale, ResourceBundle> bundles;

	private static final String bundleName = "br/com/anteros/persistence/resources/languages/messages";

	public static ResourceBundle getResourceBundle(Locale locale) {
		if (bundles == null)
			bundles = new HashMap<Locale, ResourceBundle>();

		ResourceBundle bundle = bundles.get(locale);

		if (bundle != null) {
			return bundle;
		}

		ClassLoader loader = ResourceUtils.class.getClassLoader();
		bundle = ResourceBundle.getBundle(bundleName, locale, loader);
		bundles.put(locale, bundle);

		return bundle;
	}

	public static ResourceBundle getResourceBundle() {
		return getResourceBundle(Locale.getDefault());
	}

	public static String getMessage(Class<?> clazz, String tag, Object... arguments) {
		return MessageFormat.format(getMessage(clazz, tag), arguments);
	}

	public static String getMessage(Class<?> clazz, String tag) {
		return getResourceBundle().getString(clazz.getSimpleName() + "." + tag);
	}

	public static InputStream getResourceAsStream(String resource) throws FileNotFoundException {
		String stripped = resource.startsWith("/") ? resource.substring(1) : resource;

		InputStream stream = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			URL url = classLoader.getResource(stripped);
			LOG.info(url);
			stream = classLoader.getResourceAsStream(stripped);
		}
		if (stream == null) {
			stream = new FileInputStream(resource);
		}
		return stream;
	}

	public static URL getResource(String resourceName, Class<?> callingClass) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
		if (url == null && resourceName.startsWith("/")) {
			// certain classloaders need it without the leading /
			url = Thread.currentThread().getContextClassLoader()
					.getResource(resourceName.substring(1));
		}

		ClassLoader cluClassloader = callingClass.getClassLoader();
		if (cluClassloader == null) {
			cluClassloader = ClassLoader.getSystemClassLoader();
		}
		if (url == null) {
			url = cluClassloader.getResource(resourceName);
		}
		if (url == null && resourceName.startsWith("/")) {
			// certain classloaders need it without the leading /
			url = cluClassloader.getResource(resourceName.substring(1));
		}

		if (url == null) {
			ClassLoader cl = callingClass.getClassLoader();

			if (cl != null) {
				url = cl.getResource(resourceName);
			}
		}

		if (url == null) {
			url = callingClass.getResource(resourceName);
		}

		if ((url == null) && (resourceName != null) && (resourceName.charAt(0) != '/')) {
			return getResource('/' + resourceName, callingClass);
		}

		return url;
	}

	/**
	 * Load a given resources.
	 * <p/>
	 * This method will try to load the resources using the following methods
	 * (in order):
	 * <ul>
	 * <li>From Thread.currentThread().getContextClassLoader()
	 * <li>From ClassLoaderUtil.class.getClassLoader()
	 * <li>callingClass.getClassLoader()
	 * </ul>
	 * 
	 * @param resourceName
	 *            The name of the resource to load
	 * @param callingClass
	 *            The Class object of the calling object
	 */
	public static List<URL> getResources(String resourceName, Class<?> callingClass) {
		List<URL> ret = new ArrayList<URL>();
		Enumeration<URL> urls = new Enumeration<URL>() {
			public boolean hasMoreElements() {
				return false;
			}

			public URL nextElement() {
				return null;
			}

		};
		try {
			urls = Thread.currentThread().getContextClassLoader()
					.getResources(resourceName);
		} catch (IOException e) {
			// ignore
		}
		if (!urls.hasMoreElements() && resourceName.startsWith("/")) {
			// certain classloaders need it without the leading /
			try {
				urls = Thread.currentThread().getContextClassLoader()
						.getResources(resourceName.substring(1));
			} catch (IOException e) {
				// ignore
			}
		}

		ClassLoader cluClassloader = callingClass.getClassLoader();
		if (cluClassloader == null) {
			cluClassloader = ClassLoader.getSystemClassLoader();
		}
		if (!urls.hasMoreElements()) {
			try {
				urls = cluClassloader.getResources(resourceName);
			} catch (IOException e) {
				// ignore
			}
		}
		if (!urls.hasMoreElements() && resourceName.startsWith("/")) {
			// certain classloaders need it without the leading /
			try {
				urls = cluClassloader.getResources(resourceName.substring(1));
			} catch (IOException e) {
				// ignore
			}
		}

		if (!urls.hasMoreElements()) {
			ClassLoader cl = callingClass.getClassLoader();

			if (cl != null) {
				try {
					urls = cl.getResources(resourceName);
				} catch (IOException e) {
					// ignore
				}
			}
		}

		if (!urls.hasMoreElements()) {
			URL url = callingClass.getResource(resourceName);
			if (url != null) {
				ret.add(url);
			}
		}
		while (urls.hasMoreElements()) {
			ret.add(urls.nextElement());
		}

		if (ret.isEmpty() && (resourceName != null) && (resourceName.charAt(0) != '/')) {
			return getResources('/' + resourceName, callingClass);
		}
		return ret;
	}

}
