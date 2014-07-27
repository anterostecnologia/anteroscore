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

/**
 * 
 * Enum que representa os níveis de Logs disponíveis.
 * 
 * @author Douglas Junior (nassifrroma@gmail.com)
 * 
 */
public enum LogLevel {
	VERBOSE(0),
	DEBUG(1),
	INFO(2),
	WARN(3),
	ERROR(4);

	private final int index;

	private LogLevel(int index) {
		this.index = index;
	}

	/**
	 * Retorna o índice do LogLevel na hierarquia.
	 * @return int
	 */
	public int getIndex() {
		return index;
	}

}
