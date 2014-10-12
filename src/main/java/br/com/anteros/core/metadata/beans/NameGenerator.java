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

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * A utility class which generates unique names for object instances.
 * The name will be a concatenation of the unqualified class name
 * and an instance number.
 * <p>
 * For example, if the first object instance javax.swing.JButton is passed into
 * <code>instanceName</code> then the returned string identifier will be
 * &quot;JButton0&quot;.
 * 
 * @version 1.9 12/19/03
 * @author Philip Milne
 */
class NameGenerator {

	private Map valueToName;
	private Map nameToCount;

	public NameGenerator() {
		valueToName = new IdentityHashMap();
		nameToCount = new HashMap();
	}

	/**
	 * Clears the name cache. Should be called to near the end of
	 * the encoding cycle.
	 */
	public void clear() {
		valueToName.clear();
		nameToCount.clear();
	}

	/**
	 * Returns the root name of the class.
	 */
	public static String unqualifiedClassName(Class type) {
		if(type.isArray()){
			return unqualifiedClassName(type.getComponentType()) + "Array";
		}
		String name = type.getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}

	/**
	 * Returns a String which capitalizes the first letter of the string.
	 */
	public static String capitalize(String name) {
		if(name == null || name.length() == 0){
			return name;
		}
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	/**
	 * Returns a unique string which identifies the object instance.
	 * Invocations are cached so that if an object has been previously
	 * passed into this method then the same identifier is returned.
	 * 
	 * @param instance
	 *            object used to generate string
	 * @return a unique string representing the object
	 */
	public String instanceName(Object instance) {
		if(instance == null){
			return "null";
		}
		if(instance instanceof Class){
			return unqualifiedClassName((Class) instance);
		} else{
			String result = (String) valueToName.get(instance);
			if(result != null){
				return result;
			}
			Class type = instance.getClass();
			String className = unqualifiedClassName(type);

			Object size = nameToCount.get(className);
			int instanceNumber = (size == null) ? 0 : ((Integer) size).intValue() + 1;
			nameToCount.put(className, new Integer(instanceNumber));

			result = className + instanceNumber;
			valueToName.put(instance, result);
			return result;
		}
	}
}
