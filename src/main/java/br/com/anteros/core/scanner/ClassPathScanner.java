package br.com.anteros.core.scanner;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This is the main class of this package. Resource scanner as it named, scans
 * the java class paths to find resources that are filtered out by the given
 * filters to its scan methods.
 * 
 */
public class ClassPathScanner {

	/**
	 * Finds list of classpath roots
	 * 
	 * @return list of class path root url's
	 */
	public static Set<URL> findRoots() {
		Set<URL> urls = new HashSet<URL>();
		// Start with Current Thread's laoder
		ClassLoader ctxLoader = Thread.currentThread().getContextClassLoader();
		ClassLoader loader = ctxLoader;
		while (loader != null) {
			urls.addAll(findRootsByLoader(loader));
			loader = loader.getParent();
		}

		// Also start with this classes's loader, in some environment this can
		// be different than the current thread's one
		ClassLoader sysLoader = ClassPathScanner.class.getClassLoader();
		loader = sysLoader;
		while (loader != null) {
			urls.addAll(findRootsByLoader(loader));
			loader = loader.getParent();
		}

		Map<URL, URL> replaceURLs = new HashMap<URL, URL>();
		Set<URL> derivedUrls = new HashSet<URL>();
		for (URL url : urls) {
			if (url.getProtocol().startsWith("vfs")) {
				try {
					URLConnection conn = url.openConnection();
					Object virtualFile = conn.getContent();
					if (virtualFile.getClass().getName()
							.equals("org.jboss.vfs.VirtualFile")) {
						File file = (File) virtualFile.getClass()
								.getMethod("getPhysicalFile")
								.invoke(virtualFile);
						String fileName = file.getCanonicalPath();
						String name = (String) virtualFile.getClass()
								.getMethod("getName").invoke(virtualFile);
						name = name.trim().toLowerCase();
						if ((name.endsWith("jar") || name.endsWith("zip")
								&& fileName.endsWith("/contents"))) {
							fileName = fileName.replace("contents", name);
						}
						URL repURL = new URL("file:/" + fileName);
						replaceURLs.put(url, repURL);
					}
				} catch (Exception e) {
					// We don't expect to trapped here
					e.printStackTrace();
				}
			}
			try {
				if (url.toExternalForm().endsWith("WEB-INF/classes")) {
					derivedUrls.add(new URL(url.toExternalForm().replace(
							"WEB-INF/classes", "WEB-INF/lib")));
				} else if (url.toExternalForm().endsWith("WEB-INF/classes/")) {
					derivedUrls.add(new URL(url.toExternalForm().replace(
							"WEB-INF/classes/", "WEB-INF/lib/")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		urls.removeAll(replaceURLs.keySet());
		urls.addAll(replaceURLs.values());
		urls.addAll(derivedUrls);
		replaceURLs.clear();
		//Check contained urls
		for (URL url : urls) {
			for (URL rootUrl : urls) {
				if(url.equals(rootUrl)) continue;
				if(url.toExternalForm().startsWith(rootUrl.toExternalForm())){
					if(replaceURLs.get(url) != null){
						URL settledUrl =replaceURLs.get(url);
						if(settledUrl.toExternalForm().startsWith(rootUrl.toExternalForm())){
							replaceURLs.put(url, rootUrl);	
						}
					}else{
						replaceURLs.put(url, rootUrl);						
					}
				}
			}
		}
		urls.removeAll(replaceURLs.keySet());
		return urls;
	}

	private static Set<URL> findRootsByLoader(ClassLoader loader) {
		Set<URL> urls = new HashSet<URL>();

		if (loader instanceof URLClassLoader) {
			URLClassLoader urlLoader = (URLClassLoader) loader;
			urls.addAll(Arrays.asList(urlLoader.getURLs()));
		} else {
			Enumeration<URL> urlEnum;
			try {
				urlEnum = loader.getResources("");
				while (urlEnum.hasMoreElements()) {
					URL url = urlEnum.nextElement();
					if(url.getProtocol().startsWith("bundleresource")){
						continue;
					}
					urls.add(url);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return urls;
	}

	/**
	 * Scans resources with in the classpath and applies the given list of
	 * filters
	 * 
	 * @param filters
	 *            that need to be applied
	 * @return list of found resources
	 */
	public static List<URL> scanResources(ClassPathResourceFilter... filters) {
		List<URL> resources = new ArrayList<URL>();
		Set<URL> roots = findRoots();
		filterResources(roots, filters);
		Set<RootedURL> allResources = new HashSet<RootedURL>();
		for (URL rootURL : roots) {
			List<RootedURL> rurls = scan(rootURL, false, filters);
			for (RootedURL rootedURL : rurls) {
				if(!allResources.contains(rootedURL))	allResources.add(rootedURL);
			}
		}
		for (RootedURL rURL : allResources) {
			resources.add(rURL.getResourceURL());
		}
		return resources;
	}

	/**
	 * Scans classes with in the classpath and applies the given list of filters
	 * 
	 * @param filters
	 *            that need to be applied
	 * @return list of classes that found
	 */
	public static List<Class<?>> scanClasses(ClassPathResourceFilter... filters) {
		List<Class<?>> classList = new ArrayList<Class<?>>();

		Set<URL> roots = findRoots();
		filterResources(roots, filters);
		if (roots.size() == 0)
			return classList;
		Set<RootedURL> allResources = new HashSet<RootedURL>();
		ClassLoader contextLoader = Thread.currentThread()
				.getContextClassLoader();
		ClassLoader appLoader = ClassPathScanner.class.getClassLoader();
		for (URL rootURL : roots) {
			List<RootedURL> resources = scan(rootURL, true, filters);
			for (RootedURL rootedURL : resources ) {
				if(!allResources.contains(rootedURL))	allResources.add(rootedURL);
			}

		}

		for (RootedURL rurl : allResources) {
			try {
				Class<?> clazz = loadClassByLoaders(rurl.getClassName(),
						contextLoader, appLoader);
				if (!clazz.isSynthetic()
						&& Modifier.isPublic(clazz.getModifiers())) {
					clazz.getName();
					clazz.getCanonicalName();
					if (acceptable(clazz, filters)) {
						classList.add(clazz);
					}
				}
			} catch (Exception e) {
				// Runtime dependencies not complete to load this class,
				// just skip it
			} catch (Error e) {
				// Runtime dependencies not complete to load this class,
				// just skip it
			}
		}
		return classList;
	}

	protected static Class<?> loadClassByLoaders(String name,
			ClassLoader... loaders) throws ClassNotFoundException {
		ClassNotFoundException lastException = null;
		Class<?> clazz = null;
		for (ClassLoader loader : loaders) {
			try {
				lastException = null;
				clazz = loader.loadClass(name);
				break;
			} catch (ClassNotFoundException e) {
				lastException = e;
			}
		}
		if (lastException != null)
			throw lastException;
		return clazz;
	}

	protected static void filterResources(Collection<?> resources,
			ClassPathResourceFilter... filters) {
		Set<Object> removed = new HashSet<Object>();
		for (Object resource : resources) {
			if (!acceptable(resource, filters))
				removed.add(resource);
		}
		for (Object object : removed) {
			resources.remove(object);
		}
	}

	protected static boolean acceptable(Object resource,
			ClassPathResourceFilter... filters) {
		if (resource == null)
			return false;
		if (filters == null)
			return true;
		for (ClassPathResourceFilter filter : filters) {
			if (filter.filterable(resource)) {
				if (!filter.accept(resource))
					return false;
			}
		}
		return true;
	}

	private static boolean isArchive(File f) {
		return f != null
				&& f.isFile()
				&& (f.getName().toLowerCase().endsWith("jar") || f.getName()
						.toLowerCase().endsWith("zip"));
	}

	private static boolean isClassUrl(URL url) {
		return url.toExternalForm().indexOf(".class") > 0;
	}

	private static URL convertToJarUrl(URL url) {
		if (url.toString().startsWith("jar"))
			return url;
		URL jarUrl;
		try {
			jarUrl = new URL("jar:" + url + "!/");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jarUrl = url;
		}
		return jarUrl;
	}

	protected static List<RootedURL> scan(URL rootURL, boolean scan4Classes,
			ClassPathResourceFilter... filters) {
		return scan(rootURL, rootURL, scan4Classes, filters);
	}

	protected static List<RootedURL> scan(URL rootURL, URL url,
			boolean scan4Classes, ClassPathResourceFilter... filters) {
		List<RootedURL> resources = new ArrayList<RootedURL>();
		File file = null;

		if (url.getFile() != null && url.getFile().length() > 0) {
			try {
				file = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// We don't expect to trapped here
				e.printStackTrace();
			}
		}

		if (file != null) {
			try {
				if (file.isDirectory()) {
					// package! scan for childs
					for (File child : file.listFiles()) {
						resources.addAll(scan(rootURL, child.toURI().toURL(),
								scan4Classes, filters));
					}
				} else if (isArchive(file)) {// archive
					URL jarUrl = convertToJarUrl(file.toURI().toURL());
					if (acceptable(jarUrl, filters)) {
						JarURLConnection connection = (JarURLConnection) jarUrl
								.openConnection();
						JarFile jarFile = connection.getJarFile();
						if (acceptable(jarFile, filters)) {
							Enumeration<JarEntry> entries = jarFile.entries();
							while (entries.hasMoreElements()) {
								JarEntry entry = entries.nextElement();
								if (entry.isDirectory()) {
									continue;
								} else {
									URL entryUrl = new URL(jarUrl
											+ entry.toString());
									RootedURL rurl = new RootedURL(jarUrl,
											entryUrl);
									if (((scan4Classes && isClassUrl(entryUrl)) || (!scan4Classes && !isClassUrl(entryUrl)))
											&& acceptable(rurl, filters)) {
										resources.add(rurl);
									}
								}
							}
						}
						jarFile.close();
					}
				} else {
					// Resource under a package
					RootedURL rurl = new RootedURL(rootURL, file.toURI()
							.toURL());
					if (((scan4Classes && isClassUrl(file.toURI().toURL())) || (!scan4Classes && !isClassUrl(file
							.toURI().toURL()))) && acceptable(rurl, filters)) {
						resources.add(rurl);
					}
				}
			} catch (Exception e) {
				// TODO Handle this
				// We don't expect to trapped here
				e.printStackTrace();
			}
		}
		return resources;
	}

}
