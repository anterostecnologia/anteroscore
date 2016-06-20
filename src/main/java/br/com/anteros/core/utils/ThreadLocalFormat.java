package br.com.anteros.core.utils;

/**
 * <p>
 * Abstração para o uso de formatadores thread-safe em enumerações.
 * </p>
 * 
 * @author <a href=http://gilmatryx.googlepages.com/>Gilmar P.S.L.</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public abstract class ThreadLocalFormat<T> extends ThreadLocal<T> {

	protected final String format;

	protected ThreadLocalFormat(String format) {
		Assert.notNull(format, "INVALID NULL FORMAT!");
		this.format = format;
	}
}
