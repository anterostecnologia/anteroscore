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
package br.com.anteros.core.metadata.beans;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class BeanUtils {

	private static Map declaredMethodCache = Collections.synchronizedMap(new WeakHashMap());
	/**
	 * 
	 *Find accessible method and include any inherited interfaces as well.
	 *<p>
	 * Its a little slow when compared to
	 * {@link MethodUtils#findAccessibleMethod(Class, String, Class...)}
	 *</p>
	 * 
	 * @param start
	 * @param methodName
	 * @param argCount
	 *            number of arguments
	 * @param argumentTypes
	 *            list of argument types. If null the method is determined based
	 *            on <code>argCount</code>
	 * @return
	 */
	public static Method findAccessibleMethodIncludeInterfaces(Class start, String methodName,
			int argCount, Class argumentTypes[]) {
		
		if(methodName == null){
			return null;
		}
		// For overriden methods we need to find the most derived version.
		// So we start with the given class and walk up the superclass chain.

		Method method = null;

		for(Class cl = start; cl != null; cl = cl.getSuperclass()){
			Method methods[] = getPublicDeclaredMethods(cl);
			for(int i = 0; i < methods.length; i++){
				method = methods[i];
				if(method == null){
					continue;
				}

				// make sure method signature matches.
				Class params[] = method.getParameterTypes();
				if(method.getName().equals(methodName) && params.length == argCount){
					if(argumentTypes != null){
						boolean different = false;
						if(argCount > 0){
							for(int j = 0; j < argCount; j++){
								if(params[j] != argumentTypes[j]){
									different = true;
									continue;
								}
							}
							if(different){
								continue;
							}
						}
					}
					return method;
				}
			}
		}
		method = null;

		// Now check any inherited interfaces. This is necessary both when
		// the argument class is itself an interface, and when the argument
		// class is an abstract class.
		Class ifcs[] = start.getInterfaces();
		for(int i = 0; i < ifcs.length; i++){
			// Note: The original implementation had both methods calling
			// the 3 arg method. This is preserved but perhaps it should
			// pass the args array instead of null.
			method = findAccessibleMethodIncludeInterfaces(ifcs[i], methodName, argCount, null);
			if(method != null){
				break;
			}
		}
		return method;
	}
	

	/**
	 * Get the methods declared as public within the class
	 * 
	 * @param clz
	 *            class to find public methods
	 * @return
	 */
	public static synchronized Method[] getPublicDeclaredMethods(Class clz) {
		// Looking up Class.getDeclaredMethods is relatively expensive,
		// so we cache the results.
		Method[] result = null;
		// if(!ReflectUtil.isPackageAccessible(clz)){
		// return new Method[0];
		// }
		final Class fclz = clz;
		Reference ref = (Reference) declaredMethodCache.get(fclz);
		if(ref != null){
			result = (Method[]) ref.get();
			if(result != null){
				return result;
			}
		}

		// We have to raise privilege for getDeclaredMethods
		result = (Method[]) AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				return fclz.getDeclaredMethods();
			}
		});

		// Null out any non-public methods.
		for(int i = 0; i < result.length; i++){
			Method method = result[i];
			int mods = method.getModifiers();
			if(!Modifier.isPublic(mods)){
				result[i] = null;
			}
		}
		// Add it to the cache.
		declaredMethodCache.put(fclz, new SoftReference(result));
		return result;
	}
	
	/**
	 * Utility method to take a string and convert it to normal Java variable
	 * name capitalization. This normally means converting the first character
	 * from upper case to lower case, but in the (unusual) special case when
	 * there is more than one character and both the first and second characters
	 * are upper case, we leave it alone.
	 * <p>
	 * Thus "FooBah" becomes "fooBah" and "X" becomes "x", but "URL" stays as
	 * "URL".
	 * 
	 * @param name
	 *            The string to be decapitalized.
	 * @return The decapitalized version of the string.
	 */
	public static String decapitalize(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}
		if (name.length() > 1 && Character.isUpperCase(name.charAt(1))
				&& Character.isUpperCase(name.charAt(0))) {
			return name;
		}
		char chars[] = name.toCharArray();
		chars[0] = Character.toLowerCase(chars[0]);
		return new String(chars);
	}
	
	/**
	 * Return true iff the given method throws the given exception
	 * 
	 * @param method The method that throws the exception
	 * @param exception
	 * @return
	 */
	public static boolean throwsException(Method method, Class exception) {
		Class exs[] = method.getExceptionTypes();
		for(int i = 0; i < exs.length; i++){
			if(exs[i] == exception){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return true if class a is either equivalent to class b, or
	 * if class a is a subclass of class b, i.e. if a either "extends"
	 * or "implements" b.
	 * Note tht either or both "Class" objects may represent interfaces.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isSubclass(Class a, Class b) {
		// We rely on the fact that for any given java class or
		// primtitive type there is a unqiue Class object, so
		// we can use object equivalence in the comparisons.
		if(a == b){
			return true;
		}
		if(a == null || b == null){
			return false;
		}
		for(Class x = a; x != null; x = x.getSuperclass()){
			if(x == b){
				return true;
			}
			if(b.isInterface()){
				Class interfaces[] = x.getInterfaces();
				for(int i = 0; i < interfaces.length; i++){
					if(isSubclass(interfaces[i], b)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Try to create an instance of a named class.
	 * First try the classloader of "sibling", then try the system
	 * classloader then the class loader of the current Thread.
	 * 
	 * @param sibling
	 * @param className
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static Object instantiate(Class sibling, String className) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		// First check with sibling's classloader (if any).
		ClassLoader cl = sibling.getClassLoader();
		if(cl != null){
			try{
				Class cls = cl.loadClass(className);
				return cls.newInstance();
			} catch(Exception ex){
				// Just drop through and try the system classloader.
			}
		}

		// Now try the system classloader.
		try{
			cl = ClassLoader.getSystemClassLoader();
			if(cl != null){
				Class cls = cl.loadClass(className);
				return cls.newInstance();
			}
		} catch(Exception ex){
			// We're not allowed to access the system class loader or
			// the class creation failed.
			// Drop through.
		}

		// Use the classloader from the current Thread.
		cl = Thread.currentThread().getContextClassLoader();
		Class cls = cl.loadClass(className);
		return cls.newInstance();
	}


}
