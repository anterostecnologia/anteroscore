package br.com.anteros.core.math.regression;

/**
 *
 * <p>Title: ZeroVarianceException</p>
 * <p>Description: This Exception is thrown, when a variance equals zero <br>
 * during the correlation coefficent calculation.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Matthias Maneck
 * @version 1.0
 */
public class ZeroVarianceException extends Exception {

  private String message = "";

  /**
   * Constructor with specified message.
   * @param str the message string
   */
  public ZeroVarianceException(String str) {
    message = str;
  }

  /**
   * Retrieves the message.
   * @return the message string
   */
  public String getMessage() {
    return message;
  }

}
