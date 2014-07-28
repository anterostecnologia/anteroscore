package br.com.anteros.core.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import br.com.anteros.core.configuration.exception.AnterosConfigurationException;
import br.com.anteros.core.utils.ResourceUtils;

@Root(name = "anteros-configuration")
public class AnterosBasicConfiguration 	implements BasicConfiguration {

		@Element(name = "session-factory")
		protected SessionFactoryConfiguration sessionFactory;

		public SessionFactoryConfiguration getSessionFactoryConfiguration() {
			if (sessionFactory == null)
				sessionFactory = new SessionFactoryConfiguration();
			return sessionFactory;
		}

		public void setSessionFactory(SessionFactoryConfiguration value) {
			this.sessionFactory = value;
		}

		public AnterosBasicConfiguration addAnnotatedClass(Class<?> clazz) {
			getSessionFactoryConfiguration().getAnnotatedClasses().getClazz().add(clazz.getName());
			return this;
		}

		public AnterosBasicConfiguration addAnnotatedClass(String clazz) {
			getSessionFactoryConfiguration().getAnnotatedClasses().getClazz().add(clazz);
			return this;
		}

		public AnterosBasicConfiguration setLocationPlaceHolder(String location) {
			getSessionFactoryConfiguration().getPlaceholder().setLocation(location);
			return this;
		}

		public AnterosBasicConfiguration addDataSource(DataSourceConfiguration dataSource) {
			getSessionFactoryConfiguration().getDataSources().getDataSources().add(dataSource);
			return this;
		}

		public AnterosBasicConfiguration addDataSource(String id, Class<?> clazz, PropertyConfiguration[] properties) {
			return addDataSource(id, clazz.getName(), properties);
		}

		public AnterosBasicConfiguration addDataSource(String id, String clazz, PropertyConfiguration[] properties) {
			DataSourceConfiguration dataSource = new DataSourceConfiguration();
			dataSource.setId(id);
			dataSource.setClazz(clazz);
			for (PropertyConfiguration propertyConfiguration : properties) {
				dataSource.getProperties().add(propertyConfiguration);
			}
			getSessionFactoryConfiguration().getDataSources().getDataSources().add(dataSource);
			return this;
		}

		public AnterosBasicConfiguration addDataSource(String id, Class<?> clazz, Map<String, String> properties) {
			return addDataSource(id, clazz.getName(), properties);
		}

		public AnterosBasicConfiguration addDataSource(String id, String clazz, Map<String, String> properties) {
			List<PropertyConfiguration> props = new ArrayList<PropertyConfiguration>();
			for (String property : properties.keySet()) {
				props.add(new PropertyConfiguration().setName(property).setValue(properties.get(property)));
			}
			return addDataSource(id, clazz, props.toArray(new PropertyConfiguration[] {}));
		}

		public AnterosBasicConfiguration addDataSource(String id, Class<?> clazz, Properties properties) {
			return addDataSource(id, clazz.getName(), properties);
		}

		public AnterosBasicConfiguration addDataSource(String id, String clazz, Properties properties) {
			List<PropertyConfiguration> props = new ArrayList<PropertyConfiguration>();
			for (Object property : properties.keySet()) {
				props.add(new PropertyConfiguration().setName((String) property).setValue((String) properties.get(property)));
			}
			return addDataSource(id, clazz, props.toArray(new PropertyConfiguration[] {}));
		}

		public AnterosBasicConfiguration addProperty(PropertyConfiguration property) {
			getSessionFactoryConfiguration().getProperties().getProperties().add(property);
			return this;
		}

		public AnterosBasicConfiguration addProperties(Properties properties) {
			for (Object property : properties.keySet()) {
				addProperty(new PropertyConfiguration().setName((String) property).setValue((String) properties.get(property)));
			}
			return this;
		}

		public AnterosBasicConfiguration addProperties(PropertyConfiguration[] properties) {
			for (PropertyConfiguration property : properties) {
				addProperty(property);
			}
			return this;
		}

		public AnterosBasicConfiguration addProperty(String name, String value) {
			addProperty(new PropertyConfiguration().setName(name).setValue(value));
			return this;
		}

		
		public AnterosBasicConfiguration configure() throws AnterosConfigurationException {
			return configure(AnterosCoreProperties.XML_CONFIGURATION);
		}

		public AnterosBasicConfiguration configure(String xmlFile) throws AnterosConfigurationException {
			InputStream is;
			try {
				final List<URL> resources = ResourceUtils.getResources(xmlFile, getClass());
				if ((resources !=null) && (resources.size()>0)) {
					final URL url = resources.get(0);
					is = url.openStream();
					configure(is);
					return this;
				}
			} catch (final Exception e) {
				throw new AnterosConfigurationException("Impossível realizar a leitura " + xmlFile + " " + e);
			}

			throw new AnterosConfigurationException("Arquivo de configuração " + xmlFile + " não encontrado.");
		}

		public AnterosBasicConfiguration configure(InputStream xmlConfiguration) throws AnterosConfigurationException {
			try {
				Serializer serializer = new Persister(new Format("<?xml version=\"1.0\" encoding= \"UTF-8\" ?>"));
				final AnterosBasicConfiguration result = serializer.read(AnterosBasicConfiguration.class, xmlConfiguration);
				this.setSessionFactory(result.getSessionFactoryConfiguration());
				return this;
			} catch (final Exception e) {
				throw new AnterosConfigurationException("Impossível realizar a leitura do arquivo de configuração." + e);
			}
		}

		public AnterosBasicConfiguration configure(InputStream xmlConfiguration, InputStream placeHolder) throws AnterosConfigurationException {
			try {
				Serializer serializer = new Persister(new Format("<?xml version=\"1.0\" encoding= \"UTF-8\" ?>"));
				final AnterosBasicConfiguration result = serializer.read(AnterosBasicConfiguration.class, xmlConfiguration);
				result.setPlaceHolder(placeHolder);
				this.setSessionFactory(result.getSessionFactoryConfiguration());			
				
				return this;
			} catch (final Exception e) {
				throw new AnterosConfigurationException("Impossível realizar a leitura do arquivo de configuração." + e);
			}
		}


		public String getProperty(String name) {
			return getSessionFactoryConfiguration().getProperties().getProperty(name);
		}
		
		public AnterosBasicConfiguration setPlaceHolder(InputStream placeHolder) throws IOException {
			if (placeHolder!=null){
				Properties props = new Properties();
				props.load(placeHolder);
			    getSessionFactoryConfiguration().getPlaceholder().setProperties(props);	
			}		
			return this;
		}
		
		public AnterosBasicConfiguration setProperties(Properties props){
			getSessionFactoryConfiguration().getProperties().setProperties(props);	
			return this;
		}
		
		public static InputStream getDefaultXmlInputStream() throws Exception {	
			List<URL> resources = ResourceUtils.getResources("/anteros-config.xml", AnterosBasicConfiguration.class);
			if ((resources == null) || (resources.isEmpty())) {
				resources = ResourceUtils.getResources("/assets/anteros-config.xml", AnterosBasicConfiguration.class);
				if ((resources != null) && (!resources.isEmpty())) {
					final URL url = resources.get(0);
					return url.openStream();
				}
			}
			return null;
		}

	}
