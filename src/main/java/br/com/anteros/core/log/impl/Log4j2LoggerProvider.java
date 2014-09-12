package br.com.anteros.core.log.impl;

import br.com.anteros.core.log.Logger;
import br.com.anteros.core.log.LoggerProvider;

public class Log4j2LoggerProvider extends LoggerProvider {

	@Override
	public Logger getLogger(String name) {
		return new Log4j2Logger(name);
	}

	@Override
	public Logger getLogger(Class clazz) {
		return getLogger(clazz.getName());
	}
}
