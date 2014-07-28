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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "properties")
public class PropertiesConfiguration {

	@ElementList(inline = true)
	protected List<PropertyConfiguration> properties;

	public List<PropertyConfiguration> getProperties() {
		if (properties == null) {
			properties = new ArrayList<PropertyConfiguration>();
		}
		return this.properties;
	}

	public String getProperty(String name) {
		for (PropertyConfiguration prop : properties) {
			if (prop.name.equalsIgnoreCase(name))
				return prop.getValue();
		}
		return null;
	}

	public void setProperties(Properties props) {
		if (properties == null)
			properties = new ArrayList<PropertyConfiguration>();
		for (Object key : props.keySet()) {
			properties.add(new PropertyConfiguration(key + "", props.get(key) + ""));
		}
	}

}
