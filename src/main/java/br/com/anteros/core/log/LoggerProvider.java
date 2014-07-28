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

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import br.com.anteros.core.configuration.AnterosBasicConfiguration;
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

	/**
	 * Retorna uma instância Singleton do LoggerProvider configurado no arquivo
	 * anteros-config.xml
	 * 
	 * @return LoggerProvider
	 */
	public static synchronized LoggerProvider getInstance() {
		if (PROVIDER == null) {
			PROVIDER = findProvider();
		}
		return PROVIDER;
	}

	/**
	 * Busca o arquivo XML anteros-config.xml e tenta ler a propriedade
	 * loggerProviderClassName para saber qual a classe LoggerProvider que deve
	 * ser instanciada. Caso nenhuma classe seja encontrada será instanciado um
	 * ConsoleLoggerProvider.
	 * 
	 * @return LoggerProvider
	 */
	private static LoggerProvider findProvider() {
		try {
			Serializer serializer = new Persister(new Format("<?xml version=\"1.0\" encoding= \"UTF-8\" ?>"));
			final AnterosBasicConfiguration result = serializer.read(AnterosBasicConfiguration.class,
					AnterosBasicConfiguration.getDefaultXmlInputStream());
			String providerClassName = result.getSessionFactoryConfiguration().getProperties()
					.getProperty(AnterosCoreProperties.LOGGER_PROVIDER);
			System.out.println("providerClassName: " + providerClassName);
			if (providerClassName == null)
				return new ConsoleLoggerProvider();
			return (LoggerProvider) Class.forName(providerClassName).newInstance();
		} catch (Exception ex) {
			System.err.println(ResourceUtils.getMessage(LoggerProvider.class, "not_configured", ex.getMessage()));
			return new ConsoleLoggerProvider();
		}
	}

}
