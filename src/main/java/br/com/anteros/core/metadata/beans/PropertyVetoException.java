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
package br.com.anteros.core.metadata.beans;

import java.beans.PropertyChangeEvent;

/*
 * A PropertyVetoException is thrown when a proposed change to a
 * property represents an unacceptable value.
 */

public class PropertyVetoException extends Exception {

	/**
	 * Provide a brief description of serialVersionUID.
	 * Specify the purpose of this field.
	 *
	 */
	private static final long serialVersionUID = -2206020012556077235L;

	/**
	 * Constructs a <code>PropertyVetoException</code> with a
	 * detailed message.
	 * 
	 * @param mess
	 *            Descriptive message
	 * @param evt
	 *            A PropertyChangeEvent describing the vetoed change.
	 */
	public PropertyVetoException(String mess, PropertyChangeEvent evt) {
		super(mess);
		this.evt = evt;
	}

	/**
	 * Gets the vetoed <code>PropertyChangeEvent</code>.
	 * 
	 * @return A PropertyChangeEvent describing the vetoed change.
	 */
	public PropertyChangeEvent getPropertyChangeEvent() {
		return evt;
	}

	/**
	 * A PropertyChangeEvent describing the vetoed change.
	 * 
	 * @serial
	 */
	private PropertyChangeEvent evt;
}
