package br.com.anteros.core.utils;

import java.io.Serializable;

/**
 * Signals a configuration problem with any of the factory methods.
 * @version $Revision: 811685 $ $Date: 2009-09-05 13:36:48 -0400 (Sat, 05 Sep 2009) $
 */
public class MathConfigurationException extends MathException implements Serializable{

    /** Serializable version identifier */
    private static final long serialVersionUID = 5261476508226103366L;

    /**
     * Default constructor.
     */
    public MathConfigurationException() {
        super();
    }

    /**
     * Constructs an exception with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     * @param pattern format specifier
     * @param arguments format arguments
     * @since 1.2
     */
    public MathConfigurationException(String pattern, Object ... arguments) {
        super(pattern, arguments);
    }

    /**
     * Create an exception with a given root cause.
     * @param cause  the exception or error that caused this exception to be thrown
     */
    public MathConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an exception with specified formatted detail message and root cause.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     * @param cause  the exception or error that caused this exception to be thrown
     * @param pattern format specifier
     * @param arguments format arguments
     * @since 1.2
     */
    public MathConfigurationException(Throwable cause, String pattern, Object ... arguments) {
        super(cause, pattern, arguments);
    }

}
