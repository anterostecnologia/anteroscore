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

import br.com.anteros.core.log.LogLevel;
import br.com.anteros.core.log.Logger;

/**
 * 
 * Implementação de Logger responsável por enviar a mensagem de Log a biblioteca
 * SLF4J (necessita de XML de configuração a parte)
 * 
 * @author Douglas Junior (nassifrroma@gmail.com)
 * 
 */
public class Slf4jLogger extends Logger {

	private static final long serialVersionUID = -3849491369896227753L;
	private final org.slf4j.Logger logger;

	public Slf4jLogger(String name) {
		super(name);
		logger = org.slf4j.LoggerFactory.getLogger(name);
	}

	public boolean isEnabled(LogLevel level) {
		if (level != null)
			switch (level) {
			case ERROR:
				return logger.isErrorEnabled();
			case WARN:
				return logger.isWarnEnabled();
			case INFO:
				return logger.isInfoEnabled();
			case DEBUG:
				return logger.isDebugEnabled();
			case VERBOSE:
				return logger.isDebugEnabled();
			}
		return true;
	}

	@Override
	protected void doLog(LogLevel level, Object message, Throwable thrown) {
		if (isEnabled(level)) {
			switch (level) {
			case ERROR:
				logger.error(message + "", thrown);
				return;
			case WARN:
				logger.warn(message + "", thrown);
				return;
			case INFO:
				logger.info(message + "", thrown);
				return;
			case DEBUG:
				logger.debug(message + "", thrown);
				return;
			case VERBOSE:
				logger.debug(message + "", thrown);
				return;
			}
		}
	}

}
