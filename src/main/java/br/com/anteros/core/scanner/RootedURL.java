package br.com.anteros.core.scanner;

import java.io.Serializable;
import java.net.URL;

/**
 * This is a container class to keep an URL address with it's root URL.
 * This class is intended to use with the resource scanning filter classes.The {@link ClassPathScanner}
 * sends resource URL addresses to filters as RootedURL classes instead of single URL's. Thus filters 
 * can find out package name and file names.
 */
public class RootedURL implements Serializable {
 
	private static final long serialVersionUID = -4967318289335433478L;
	
	private URL rootURL=null;
	private URL resourceURL=null;
	private String rootPath=null;
	private String resourcePath=null;
	private String packageName=null;
	private String resourceName=null;
	private String nakedClassName=null;
	
	/**
	 * Constructs a new RootedURL address
	 * @param rootURL root of the resource URL 
	 * @param resourceURL Full resource URL
	 */
	public RootedURL(URL rootURL, URL resourceURL) {
		if(rootURL == null){
			throw new IllegalArgumentException("Null root URL not accepted!");
		}
		if(resourceURL == null){
			throw new IllegalArgumentException("Null resource URL not accepted!");
		}
		this.rootPath = rootURL.toString().substring(rootURL.toString().indexOf(":/")+2);
		this.resourcePath = resourceURL.toString().substring(resourceURL.toString().indexOf(":/")+2);
		if(! resourcePath.startsWith(rootPath) &&
			(rootPath.endsWith("/") && ! resourcePath.startsWith(rootPath.substring(0,rootPath.length()-1)))) {
				throw new IllegalArgumentException("The root URL \""+rootURL+"\" is not root of the resource URL \""+resourceURL+"\"");
		}
		this.rootURL = rootURL;
		this.resourceURL = resourceURL;
		//Extract package name
		packageName = resourcePath;
		if(rootPath.equals(packageName)){
			packageName= "";
		}else{
			if(packageName.lastIndexOf('/') >= rootPath.length()){
				packageName = packageName.substring(rootPath.length(),packageName.lastIndexOf('/'));
			}else{
				packageName = "";
			}
		}
		packageName = packageName.replace('/','.');
		if(packageName.startsWith(".")) packageName = packageName.substring(1);
		//Extract resource name 
		resourceName = resourcePath;
		if(rootPath.equals(resourceName)){
			resourceName=null;
		}else{
			if(resourceName.lastIndexOf('/') >= rootPath.length() && resourceName.length() > resourceName.lastIndexOf('/')){
				resourceName = resourceName.substring(resourceName.lastIndexOf('/')+1);
			}else{
				resourceName = null;
			}
		} 
		//Extract className
		if(resourceName != null && resourceName.indexOf(".class")>0){
			nakedClassName  = resourceName.substring(0,resourceName.indexOf(".class"));
		}
	}
	
	public URL getRootURL() {
		return rootURL;
	}
	
	public URL getResourceURL() {
		return resourceURL;
	}
	
	/**
	 * Package name from resource URL 
	 * @return package name 
	 */
	public String getPackageName(){
		return packageName;
	}
	
	/**
	 * Resource name from resource URL 
	 * @return resource name 
	 */
	public String getResourceName(){
		return resourceName;
	}
	
	/**
	 * If resource is a class,gives the class name otherwise returns null
	 * @return full class name 
	 */
	public String getClassName() {
		return packageName+"."+nakedClassName;
	}

	/**
	 * Naked class name
	 * @return
	 */
	public String getNakedClassName() {
		return nakedClassName;
	}

	@Override
	public String toString() {
	    return new StringBuilder().append("Root:"+getRootURL()).append(",Resource:").append(getResourceURL()).toString();
	}
	
	
	public boolean isArchive() {
		String url = rootURL.toString(); 
		if (url.startsWith("file")) {
			return url.toLowerCase().endsWith("jar")
					|| url.toLowerCase().endsWith("zip");
		} else if (url.startsWith("vfs")) {
			return url.toLowerCase().endsWith("jar")
					|| url.toLowerCase().endsWith("zip") 
					|| url.toLowerCase().endsWith("jar/")
					|| url.toLowerCase().endsWith("zip/");
		} else if (url.startsWith("jar")) { 
			return true;
		}else {
			return url.toLowerCase().startsWith("jar")
					|| url.toLowerCase().startsWith("zip");
		}
	}
 
}
