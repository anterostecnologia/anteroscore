package br.com.anteros.core.log.impl;

import br.com.anteros.core.log.LogLevel;
import br.com.anteros.core.log.Logger;

public class Log4jLogger extends Logger {

	private static final long serialVersionUID = -3849491369896227753L;
	private final org.apache.log4j.Logger logger;

	public Log4jLogger(String name) {
		super(name);
		logger = org.apache.log4j.Logger.getLogger(name);
	}

	public boolean isEnabled(LogLevel level) {
		if (level != null)
			switch (level) {
			case ERROR:
				return logger.isEnabledFor(org.apache.log4j.Level.ERROR);
			case WARN:
				return logger.isEnabledFor(org.apache.log4j.Level.WARN);
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
