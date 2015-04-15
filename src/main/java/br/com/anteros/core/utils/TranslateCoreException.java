package br.com.anteros.core.utils;

public class TranslateCoreException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TranslateCoreException() {
		super();
	}

	public TranslateCoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public TranslateCoreException(String message) {
		super(message);
	}

	public TranslateCoreException(Throwable cause) {
		super(cause);
	}

}
