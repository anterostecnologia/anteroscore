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

import java.io.IOException;
import java.util.Properties;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;

import br.com.anteros.core.utils.ResourceUtils;
import br.com.anteros.core.utils.StringUtils;

@Root(name = "placeholder")
public class PlaceholderConfiguration {

	@Attribute(name = "location", required = true)
	protected String location;
	@Transient
	protected Properties properties;

	public String getLocation() {
		return location;
	}

	public void setLocation(String value) {
		this.location = value;
	}

	public Properties getProperties() throws IOException {
		if (properties == null) {
			properties = new Properties();
			if (!StringUtils.isEmpty(location))
				properties.load(ResourceUtils.getResourceAsStream(location));
		}
		return properties;
	}

	public PlaceholderConfiguration setProperties(Properties properties) {
		this.properties = properties;
		return this;
	}

}
