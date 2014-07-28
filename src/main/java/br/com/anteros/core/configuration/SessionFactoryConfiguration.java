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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import br.com.anteros.core.utils.StringUtils;

@Root(name = "session-factory")
public class SessionFactoryConfiguration {

	@Element(name="placeholder", required=false)
	protected PlaceholderConfiguration placeholder;

	@Element(required=false)
	protected DataSourcesConfiguration dataSources;

	@Element
	protected PropertiesConfiguration properties;

	@Element
	protected AnnotatedClassesConfiguration annotatedClasses;

	public PlaceholderConfiguration getPlaceholder() {
		if (placeholder == null)
			placeholder = new PlaceholderConfiguration();
		return placeholder;
	}

	public void setPlaceholder(PlaceholderConfiguration value) {
		this.placeholder = value;
	}

	public DataSourcesConfiguration getDataSources() {
		if (dataSources == null)
			dataSources = new DataSourcesConfiguration();
		return dataSources;
	}

	public void setDataSources(DataSourcesConfiguration value) {
		this.dataSources = value;
	}

	public PropertiesConfiguration getProperties() {
		if (properties == null)
			properties = new PropertiesConfiguration();
		return properties;
	}

	public void setProperties(PropertiesConfiguration value) {
		this.properties = value;
	}

	public AnnotatedClassesConfiguration getAnnotatedClasses() {
		if (annotatedClasses == null)
			annotatedClasses = new AnnotatedClassesConfiguration();
		return annotatedClasses;
	}

	public void setAnnotatedClasses(AnnotatedClassesConfiguration value) {
		this.annotatedClasses = value;
	}

	public DataSourceConfiguration getDataSourceById(String id) {
		for (DataSourceConfiguration ds : getDataSources().getDataSources()) {
			if (ds.getId().equals(id))
				return ds;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Class<? extends Serializable>> getClasses() throws ClassNotFoundException {
		List<Class<? extends Serializable>> result = new ArrayList<Class<? extends Serializable>>();
		for (String cl : getAnnotatedClasses().getClazz()) {
			result.add((Class<? extends Serializable>) Class.forName(cl));
		}
		return result;
	}
	
	public void addProperty(String name, String value){
		this.properties.getProperties().add(new PropertyConfiguration(name, value));
	}

	public String getProperty(String name) throws IOException {
		for (PropertyConfiguration prop : getProperties().getProperties()) {
			if (prop.name.equalsIgnoreCase(name))
				return ConvertPlaceHolder(prop.getValue());
		}
		return null;
	}

	public String getPropertyDef(String name, String defaultValue) throws IOException {
		for (PropertyConfiguration prop : getProperties().getProperties()) {
			if (prop.name.equalsIgnoreCase(name))
				return ConvertPlaceHolder(prop.getValue());
		}
		return defaultValue;
	}

	public String ConvertPlaceHolder(String value) throws IOException {
		if (value != null) {
			if (value.indexOf("$") >= 0) {
				value = StringUtils.replaceAll(value, "${", "");
				value = StringUtils.replaceAll(value, "}", "");
				value = getPlaceholder().getProperties().getProperty(value);
				return value;
			}
		}
		return value;
	}

}
