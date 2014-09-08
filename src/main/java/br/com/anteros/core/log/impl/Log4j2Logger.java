package br.com.anteros.core.log.impl;

import br.com.anteros.core.log.LogLevel;
import br.com.anteros.core.log.Logger;

public class Log4j2Logger extends Logger {

	private static final long serialVersionUID = -3849491369896227753L;
	private final org.apache.logging.log4j.Logger logger;

	public Log4j2Logger(String name) {
		super(name);
		logger = org.apache.logging.log4j.LogManager.getLogger(name);
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
