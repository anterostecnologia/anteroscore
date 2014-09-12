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
package br.com.anteros.core.log.impl;

import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;

/**
 * 
 * Promove a instância de um objeto Logger do tipo Slf4jLogger.
 * 
 * @author Douglas Junior (nassifrroma@gmail.com)
 * 
 */
public class Slf4jLoggerProvider extends LoggerProvider {

	@Override
	public Logger getLogger(String name) {
		return new Slf4jLogger(name);
	}

	@Override
	public Logger getLogger(Class clazz) {
		return getLogger(clazz.getName());
	}

}
