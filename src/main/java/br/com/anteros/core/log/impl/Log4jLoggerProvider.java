package br.com.anteros.core.log.impl;

import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;

public class Log4jLoggerProvider extends LoggerProvider {

	@Override
	public Logger getLogger(String name) {
		return new Log4jLogger(name);
	}

	@Override
	public Logger getLogger(Class clazz) {
		return getLogger(clazz.getName());
	}
	
}
