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
package br.com.anteros.core.configuration;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "property")
public class PropertyConfiguration {

	@Attribute(name = "name", required = true)
	protected String name;
	@Attribute(name = "value", required = true)
	protected String value;

	public PropertyConfiguration() {
	}

	public PropertyConfiguration(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public PropertyConfiguration setName(String value) {
		this.name = value;
		return this;
	}

	public String getValue() {
		return value;
	}

	public PropertyConfiguration setValue(String value) {
		this.value = value;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyConfiguration other = (PropertyConfiguration) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return name+" : "+value;
	}

}
