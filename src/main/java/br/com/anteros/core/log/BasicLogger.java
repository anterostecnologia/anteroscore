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
 * Interface responsável por assinar os métodos de Logs disponíveis.
 * 
 * @author Douglas Junior (nassifrroma@gmail.com)
 * 
 */
public interface BasicLogger {

	/**
	 * Retorna TRUE caso o LogLevel instanciado seja Verbose ou FALSE caso
	 * contrário.
	 * 
	 * @return boolean
	 */
	public boolean isVerboseEnabled();

	/**
	 * Envia uma mensagem de log com o level Verbose.
	 * 
	 * @param message
	 * @param t
	 */
	public void verbose(Object message, Throwable t);

	/**
	 * Envia uma mensagem de log com o level Verbose.
	 * 
	 * @param message
	 */
	public void verbose(Object message);

	/**
	 * Retorna TRUE caso o LogLevel instanciado seja Debug ou FALSE caso
	 * contrário.
	 * 
	 * @return boolean
	 */
	public boolean isDebugEnabled();

	/**
	 * Envia uma mensagem de log com o level Debug.
	 * 
	 * @param message
	 * @param t
	 */
	public void debug(Object message, Throwable t);

	/**
	 * Envia uma mensagem de log com o level Debug.
	 * 
	 * @param message
	 */
	public void debug(Object message);

	/**
	 * Retorna TRUE caso o LogLevel instanciado seja Info ou FALSE caso
	 * contrário.
	 * 
	 * @return boolean
	 */
	public boolean isInfoEnabled();

	/**
	 * Envia uma mensagem de log com o level Info.
	 * 
	 * @param message
	 * @param t
	 */
	public void info(Object message, Throwable t);

	/**
	 * Envia uma mensagem de log com o level Info.
	 * 
	 * @param message
	 */
	public void info(Object message);

	/**
	 * Retorna TRUE caso o LogLevel instanciado seja Warn ou FALSE caso
	 * contrário.
	 * 
	 * @return boolean
	 */
	public boolean isWarnEnabled();

	/**
	 * Envia uma mensagem de log com o level Warn.
	 * 
	 * @param message
	 * @param t
	 */
	public void warn(Object message, Throwable t);

	/**
	 * Envia uma mensagem de log com o level Warn.
	 * 
	 * @param message
	 */
	public void warn(Object message);

	/**
	 * Retorna TRUE caso o LogLevel instanciado seja Error ou FALSE caso
	 * contrário.
	 * 
	 * @return boolean
	 */
	public boolean isErrorEnabled();

	/**
	 * Envia uma mensagem de log com o level Error.
	 * 
	 * @param message
	 * @param t
	 */
	public void error(Object message, Throwable t);

	/**
	 * Envia uma mensagem de log com o level Error.
	 * 
	 * @param message
	 */
	public void error(Object message);

	/**
	 * Envia uma mensagem de log com o level informado por parâmetro.
	 * 
	 * @param level
	 * @param message
	 * @param t
	 */
	public void log(LogLevel level, Object message, Throwable t);

	/**
	 * Envia uma mensagem de log com o level informado por parâmetro.
	 * 
	 * @param level
	 * @param message
	 */
	public void log(LogLevel level, Object message);

	/**
	 * Retorna TRUE caso o LogLevel instanciado seja igual ao parâmetro
	 * informado ou FALSE caso contrário.
	 * 
	 * @param level
	 *            LogLevel
	 * @return boolean
	 */
	public boolean isEnabled(LogLevel level);

}
