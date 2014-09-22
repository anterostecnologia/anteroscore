package br.com.anteros.core.scanner;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A Generic filter that applicable to any kind of resources.  
 */
public class ResourceFilter implements ClassPathResourceFilter {
	private static final Integer NAME_TYPE_PACKAGE = 1;
	private static final Integer NAME_TYPE_RESOURCE = 2;
	private static final Integer NAME_TYPE_CLASS = 3;
	private static final Integer NAME_TYPE_DIRECTORY = 4;
	private static final Integer NAME_TYPE_ARCHIVE = 5;
	
	private Map<Integer, Set<NamePattern>> namePatterns = new HashMap<Integer, Set<NamePattern>>();
	private boolean scanArchives = true;

	public ResourceFilter() {
	}

	private ResourceFilter appendName(String name, int type) {
		if (namePatterns.get(type) == null) {
			namePatterns.put(type, new HashSet<NamePattern>());
		}
		namePatterns.get(type).add(new NamePattern(name));
		return this;
	}

	/**
	 * Appends a package name criterion to this filter. Names always joined with
	 * logical or operator.
	 * 
	 * @param name the package name need to be filtered, , the star ('*')
	 *            character can be used with the name to represent unlimited
	 *            number of any character and the underscore character ('_') can
	 *            be used to represent single any character.
	 * @return this
	 */
	public  ResourceFilter packageName(String name) {
		return appendName(name, NAME_TYPE_PACKAGE);
	}

	/**
	 * Appends a resource name criterion to this filter. Names always joined with
	 * logical or operator.
	 * 
	 * @param name
	 *            the resource name need to be filtered, , the star ('*')
	 *            character can be used with the name to represent unlimited
	 *            number of any character and the underscore character ('_') can
	 *            be used to represent single any character.
	 * @return this
	 */
	public  ResourceFilter resourceName(String name) {
		return appendName(name, NAME_TYPE_RESOURCE);
	}

	/**
	 * Appends an archive name criterion to this filter like a jar or a zip file name.
	 * . Names always joined with logical or operator.
	 * 
	 * @param name
	 *            the archive name need to be filtered, , the star ('*')
	 *            character can be used with the name to represent unlimited
	 *            number of any character and the underscore character ('_') can
	 *            be used to represent single any character.
	 * @return this
	 */
	public  ResourceFilter archiveName(String name) {
		return appendName(name, NAME_TYPE_ARCHIVE);
	}

	/**
	 * Appends an directory name criterion to this filter to limit class path directory
	 * names and archive files (jar,zip) directory names. Names always joined with
	 * logical or operator.
	 * 
	 * @param name
	 *            the directory name need to be filtered, , the star ('*')
	 *            character can be used with the name to represent unlimited
	 *            number of any character and the underscore character ('_') can
	 *            be used to represent single any character.
	 * @return this
	 */
	public  ResourceFilter directoryName(String name) {
		return appendName(name, NAME_TYPE_DIRECTORY);
	}

	/**
	 * Sets if the archived files need to be scanned. By default everything to be
	 * scanned
	 * 
	 * @param scan
	 *            true if archives to be scanned
	 * @return this
	 */
	public  ResourceFilter scanArchives(boolean scan) {
		scanArchives = scan;
		return this;
	}

	/**
	 * Appends a class name to this filter. Class names checked without 
	 * the package name, so if you need to filter package names use {@link packageName}
	 * method.
	 * 
	 * @param name
	 *            the class name need to be filtered, the star ('*')
	 *            character can be used with the name to represent unlimited
	 *            number of any character and the underscore character ('_') can
	 *            be used to represent single any character.
	 * @return this
	 */
	public  ResourceFilter className(String name) {
		return appendName(name, NAME_TYPE_CLASS);
	}

	public boolean accept(Object subject) {
		if (!filterable(subject))
			return true;
		if (subject instanceof RootedURL) {
			if (!checkRootedURL((RootedURL) subject))
				return false;
		} else if (subject instanceof URL) {
			if (!checkURL((URL) subject))
				return false;
		}
		return true;
	}

	private boolean checkURL(URL url) {
		if (isArchive(url)) {
			if (!scanArchives)
				return false;
			if (namePatterns.get(NAME_TYPE_ARCHIVE) != null
					&& namePatterns.get(NAME_TYPE_ARCHIVE).size() > 0) {
				String archiveName = extractArchiveName(url);
				boolean matched = false;
				for (NamePattern namePattern : namePatterns.get(NAME_TYPE_ARCHIVE)) {
					if (namePattern.matches(archiveName)) {
						matched = true;
						break;
					}
				}
				if (!matched)
					return false;
			}
		}

		if (namePatterns.get(NAME_TYPE_DIRECTORY) != null
				&& namePatterns.get(NAME_TYPE_DIRECTORY).size() > 0) {
			String dirName = extractDirName(url);
			boolean matched = false;
			for (NamePattern np : namePatterns.get(NAME_TYPE_DIRECTORY)) {
				if (np.matches(dirName)) {
					matched = true;
					break;
				}
			}
			if (!matched)
				return false;
		}
		
		return true;
	}

	private String extractDirName(URL url) {
		String st = url.toExternalForm();
		if (isArchive(url)) {
			int inx = st.indexOf(".jar");
			if (inx < 0)
				inx = st.indexOf(".zip");
			if (inx > 0)
				st = st.substring(0, inx - 1);
			inx = st.lastIndexOf("/");
			if (inx > 0)
				st = st.substring(0, inx - 1);
		}
		if (st.contains(":/")) {
			st = st.substring(st.indexOf(":/") + 2);
		}
		return !st.endsWith("/") ? st : st.substring(0, st.length() - 1);
	}

	private String extractArchiveName(URL url) {
		String st = url.toExternalForm();
		int inx = st.indexOf(".jar");
		if (inx < 0)
			inx = st.indexOf(".zip");
		if (inx < 0)
			return "";
		int binx = st.lastIndexOf("/", inx);
		if (binx < 0)
			binx = 0;
		else
			binx++;
		return st.substring(binx, inx + 4);
	}

	private boolean isArchive(URL url) {
		String urlStr = url.toString();
		if (urlStr.startsWith("file")) {
			return urlStr.toLowerCase().endsWith(".jar")
					|| urlStr.toLowerCase().endsWith(".zip");
		} else if (urlStr.startsWith("vfs")) {
			return urlStr.toLowerCase().endsWith(".jar")
					|| urlStr.toLowerCase().endsWith(".zip")
					|| urlStr.toLowerCase().endsWith(".jar/")
					|| urlStr.toLowerCase().endsWith(".zip/");
		} else {
			return urlStr.toLowerCase().startsWith("jar")
					|| urlStr.toLowerCase().startsWith("zip");
		}
	}

	private boolean checkRootedURL(RootedURL rootedUrl) {
		boolean matched = false;
		// Check package name patterns
		if (namePatterns.get(NAME_TYPE_PACKAGE) != null
				&& namePatterns.get(NAME_TYPE_PACKAGE).size() > 0) {
			matched = false;
			for (NamePattern np : namePatterns.get(NAME_TYPE_PACKAGE)) {
				if (np.matches(rootedUrl.getPackageName())) {
					matched = true;
					break;
				}
			}
			if (!matched)
				return false;
		}
		// Check resource name patterns
		if (namePatterns.get(NAME_TYPE_RESOURCE) != null
				&& namePatterns.get(NAME_TYPE_RESOURCE).size() > 0) {
			if (rootedUrl.getResourceName() == null)
				return false;
			matched = false;
			for (NamePattern np : namePatterns.get(NAME_TYPE_RESOURCE)) {
				if (np.matches(rootedUrl.getResourceName())) {
					matched = true;
					break;
				}
			}
			if (!matched)
				return false;
		}
		// Check class name patterns
		if (namePatterns.get(NAME_TYPE_CLASS) != null
				&& namePatterns.get(NAME_TYPE_CLASS).size() > 0) {
			if (rootedUrl.getNakedClassName() == null)
				return false;
			matched = false;
			for (NamePattern np : namePatterns.get(NAME_TYPE_CLASS)) {
				if (np.matches(rootedUrl.getNakedClassName())) {
					matched = true;
					break;
				}
			}
			if (!matched)
				return false;
		}

		return true;
	}

	public boolean filterable(Object subject) {
		return (subject != null && (subject instanceof RootedURL || subject instanceof URL));
	}

	@Override
	public String toString() {
		return "ResourceFilter [namePatterns=" + namePatterns
				+ ", scanArchives=" + scanArchives + "]";
	}

}
