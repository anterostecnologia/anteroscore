package br.com.anteros.core.math.regression;

import java.util.Arrays;

/**
 * <p>Title: Functions </p>
 * <p> Description: This class contains several mathematical function <br>
 * &nbsp;prototypes like exponentail, logarithmic or linear functions.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Matthias Maneck
 * @version 1.0
 */

public class Functions {


  /**
   * Calculates the result y of a liniar function: <br>
   * &nbsp; y = a+b*<i>x</i> <br>
   * for given parameters a, b and a value x.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param value the function value
   * @return the result of the function as a double value
   */
  public static synchronized double linear(double[] params, double value) {

    double result = params[0]+params[1]*value;

    return result;
  }




  /**
   * Calculates the results y of a liniar function: <br>
   * &nbsp; y = a+b*<i>x</i> <br>
   * for given parameters a, b and a values x.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param values a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static synchronized double[] linear(double[] params, double[] values) {

    int n = values.length;
    double[] result = new double[n];

    for (int i=0; i<n; i++) {
      result[i] = params[0] + params[1] * values[i];
    }

    return result;
  }

  public static double linear_inv(double[] params, double value) {
    return (value-params[0])/params[1];
  }

  public static double[] linear_inv(double[] params, double[] values) {
    double[] result = new double[values.length];

    for (int i=0; i<values.length; i++) {
      result[i] = (values[i]-params[0])/params[1];
    }
    return result;
  }



  /**
   * Calculates the result y of a exponential function: <br>
   * &nbsp; y = a*<i>e</i><sup>(b*<i>x</i>)</sup> <br>
   * for given parameters a, b and a value x.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param value the function value
   * @return the result of the function as a double value
   */
  public static synchronized double exponential(double[] params, double value) {

    double result = params[0]*Math.exp(params[1]*value);

    return result;
  }




  /**
   * Calculates the results y of a exponential function: <br>
   * &nbsp; y = a*<i>e</i><sup>(b*<i>x</i>)</sup> <br>
   * for given parameters a, b and a values x.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param values a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static synchronized double[] exponential(double[] params, double[] values) {
    int n = values.length;
    double[] result = new double[n];

    for (int i=0; i<n; i++) {
      result[i] = params[0]*Math.exp(params[1]*values[i]);
    }

    return result;
  }

  /**
   * Calculates the results of the inverse function of the given exponential
   * function.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param value a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static double exponential_inv(double[] params, double value) {
    return Math.log(value/params[0])/params[1];
  }

  /**
   * Calculates the results of the inverse function of the given exponential
   * function.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param values a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static double[] exponential_inv(double[] params, double values[]) {
    double[] result = new double[values.length];

    for (int i=0; i<values.length; i++) {
      result[i] = Math.log(values[i]/params[0])/params[1];
    }
    return result;
  }



  /**
   * Calculates the result y of a lograthimc function: <br>
   * &nbsp; y = a+b*ln(<i>x</i>) <br>
   * for given parameters a, b and a value x.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param value the function value
   * @return the result of the function as a double value
   */
  public static synchronized double logarithmic(double[] params, double value) {
    double result = params[0]+params[1]*Math.log(value);

    return result;
  }



  /**
   * Calculates the results y of a lograthimc function: <br>
   * &nbsp; y = a+b*ln(<i>x</i>) <br>
   * for given parameters a, b and a values x.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param values a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static synchronized double[] logarithmic(double[] params, double[] values) {

    int n = values.length;
    double[] result = new double[n];

    for (int i=0; i<n; i++) {
      result[i] = params[0]+params[1]*Math.log(values[i]);
    }

    return result;
  }

  /**
   * Calculates the results of the inverse function of the given logarithmic
   * function.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param value a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static double logarithmic_inv(double[] params, double value) {
    return Math.exp((value-params[0])/params[1]);
  }

  /**
   * Calculates the results of the inverse function of the given logarithmic
   * function.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param values a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static double[] logarithmic_inv(double[] params, double values[]) {
    double[] result = new double[values.length];

    for(int i=0; i<values.length; i++) {
      result[i] = Math.exp((values[i]-params[0])/params[1]);
    }
    return result;
  }


  /**
   * Calculates the result y of a power function: <br>
   * &nbsp; y = a*<i>x</i><sup>b</sup> <br>
   * for given parameters a, b and a value x.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param value the function value
   * @return the result of the function as a double value
   */
  public static synchronized double power(double[] params, double value) {

    double result = params[0] * Math.pow(value,params[1]);

    return result;
  }




  /**
   * Calculates the results y of a power function: <br>
   * &nbsp; y = a*<i>x</i><sup>b</sup> <br>
   * for given parameters a, b and a values x.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param values a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static synchronized double[] power(double[] params, double values[]) {

    int n = values.length;
    double[] result = new double[n];

    for (int i=0; i<n; i++) {
      result[i] = params[0] * Math.pow(values[i],params[1]);
    }

    return result;
  }

  /**
   * Calculates the results of the inverse function of the given power law
   * function.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param value a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static double power_inv(double[] params, double value) {
    return Math.pow(value/params[0], 1/params[1]);
  }

  /**
   * Calculates the results of the inverse function of the given power law
   * function.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param values a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static double[] power_inv(double[] params, double[] values) {
    double[] result = new double[values.length];

    for(int i=0; i<values.length; i++) {
      result[i] = Math.pow(values[i]/params[0], 1/params[1]);
    }

    return result;
  }



  /**
   * Calculates the result y of a polynomial function: <br>
   * &nbsp; y = a+b*<i>x</i>+c*<i>x</i><sup>2</sup>+d*<i>x</i><sup>3</sup>+...+k*<i>x</i><sup>n</sup> <br>
   * for given parameters a, b and a value x.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param value the function value
   * @return the result of the function as a double value
   */
  public static synchronized double polynomial(double[] params, double value) {

    int k = params.length;
    double result = 0;

    for (int i=0; i<k; i++) {
      result += params[i]*Math.pow(value,i);
    }

    return result;
  }




  /**
   * Calculates the results y of a polynomial function: <br>
   * &nbsp; y = a+b*<i>x</i>+c*<i>x</i><sup>2</sup>+d*<i>x</i><sup>3</sup>+...+k*<i>x</i><sup>n</sup> <br>
   * for given parameters a, b and a values x.
   * @param params a double[] of the parameters a (params[0]) and b (params[1])
   * @param values a double[] of the function values
   * @return the results of the function as a double[]
   */
  public static synchronized double[] polynomial(double[] params, double[] values) {
    int n = values.length;
    int k = params.length;
    double[] result = new double[n];
    Arrays.fill(result, 0);

    for (int i=0; i<n; i++) {
      for (int j=0; j<k; j++) {
        result[i] += params[j]*Math.pow(values[i],j);
      }
    }

    return result;
  }

}