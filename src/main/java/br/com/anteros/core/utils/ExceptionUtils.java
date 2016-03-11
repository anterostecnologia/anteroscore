package br.com.anteros.core.utils;

public class ExceptionUtils {

	public static Throwable extractExceptionFromCause(Throwable ex, Class<?> exceptionClass) {
		if (ex.getCause() != null) {
			if (ex.getCause().getClass().equals(exceptionClass)) {
				return ex.getCause();
			} else
				return extractExceptionFromCause(ex.getCause(), exceptionClass);
		}
		return null;
	}
}
