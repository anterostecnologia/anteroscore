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

import java.io.PrintStream;

import br.com.anteros.core.log.LogLevel;
import br.com.anteros.core.log.Logger;

/**
 * 
 * Implementação de Logger responsável somente por enviar a mensagem de Log ao console.
 * 
 * @author Douglas Junior <nassifrroma@gmail.com>
 * 
 */
public class ConsoleLogger extends Logger {

	private static final long serialVersionUID = 7220138343203098481L;

	private final LogLevel level;

	public ConsoleLogger(String name, LogLevel level) {
		super(name);
		this.level = level;
	}

	public boolean isEnabled(LogLevel level) {
		if (this.level == null)
			return false;
		return level.getIndex() >= this.level.getIndex();
	}

	@Override
	protected void doLog(LogLevel level, Object message, Throwable t) {
		if (isEnabled(level)) {
			PrintStream pt = System.out;
			if (level == LogLevel.ERROR || level == LogLevel.WARN)
				pt = System.err;
			if (t != null)
				pt.println("[" + level + ":" + getName() + "] " + message + ". Throwable: " + t.toString());
			else
				pt.println("[" + level + ":" + getName() + "] " + message);
		}
	}

}
