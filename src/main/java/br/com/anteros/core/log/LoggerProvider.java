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
package br.com.anteros.core.log;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import br.com.anteros.core.configuration.AnterosCoreProperties;
import br.com.anteros.core.log.impl.ConsoleLoggerProvider;
import br.com.anteros.core.utils.ResourceUtils;

/**
 * 
 * Classe abstrata responsável por promover o objeto Logger a ser utilizado.
 * 
 * @author Douglas Junior (nassifrroma@gmail.com)
 * 
 */
public abstract class LoggerProvider {

	private static LoggerProvider PROVIDER;

	/**
	 * Instancia e retorna um objeto do tipo Logger.
	 * 
	 * @param name
	 *            Nome da classe que está sendo logada.
	 * @return Logger
	 */
	public abstract Logger getLogger(String name);
	
	public abstract Logger getLogger(Class clazz);

	/**
	 * Retorna uma instância Singleton do LoggerProvider configurado no arquivo
	 * anteros-config.xml
	 * 
	 * @return LoggerProvider
	 */
	public static synchronized LoggerProvider getInstance() {
		if (PROVIDER == null) {
			PROVIDER = discoverProvider();
		}
		return PROVIDER;
	}

	/**
	 * Busca o arquivo de configuração de log do Anteros no classpath e tenta
	 * ler a propriedade loggerProviderClassName para saber qual a classe
	 * LoggerProvider que deve ser instanciada. Caso nenhuma classe seja
	 * encontrada será instanciado um ConsoleLoggerProvider.
	 * 
	 * @return LoggerProvider
	 */
	protected static synchronized LoggerProvider discoverProvider() {
		/*
		 * Tenta criar o log provider lendo arquivo de configuração caso
		 * encontre um no classpath.
		 */
		LoggerProvider provider = tryGetLoggerProviderByConfigIfPossible();

		if (provider == null) {
			/*
			 * Verifica se existe o Slf4j no classpath, se tiver usa
			 */
			try {
				provider = (LoggerProvider)Class.forName("org.slf4j.Logger").newInstance();
			} catch (Exception e) {
				/*
				 * Verifica se existe o Log4j no classpath, se tiver usa
				 */
				try {
					provider = (LoggerProvider)Class.forName("org.apache.log4j.Logger").newInstance();
				} catch (Exception e1) {
					/*
					 * Verifica se existe o Log4j 2 no classpath, se tiver usa
					 */
					try {
						provider = (LoggerProvider)Class.forName("org.apache.logging.log4j.Logger").newInstance();
					} catch (Exception e2) {
					}
				}
			}
		}
		
		if (provider == null) {
			provider = new ConsoleLoggerProvider();
		}
		return provider;
	}

	protected static LoggerProvider tryGetLoggerProviderByConfigIfPossible() {
		try {
			Properties properties = new Properties();
			properties.load(getLogPropertiesInputStream());
			String providerClassName = properties.getProperty(AnterosCoreProperties.LOGGER_PROVIDER);
			return (LoggerProvider) Class.forName(providerClassName).newInstance();
		} catch (Exception ex) {
			return null;
		}
	}

	protected static InputStream getLogPropertiesInputStream() throws Exception {
		List<URL> resources = ResourceUtils.getResources(AnterosCoreProperties.PROPERTIES_LOG, LoggerProvider.class);
		if ((resources == null) || (resources.isEmpty())) {
			resources = ResourceUtils.getResources("/assets" + AnterosCoreProperties.PROPERTIES_LOG,
					LoggerProvider.class);
			if (resources == null || resources.isEmpty()) {
				return null;
			}
		}
		final URL url = resources.get(0);
		return url.openStream();
	}

}
