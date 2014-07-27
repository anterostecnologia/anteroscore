package br.com.anteros.core.math.regression;

import java.util.Vector;

/**
 *
 * <p>Title: RegressionMethods </p>
 * <p>Description: This class contains several regression methods.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Matthias Maneck
 * @version 1.0
 */
public class RegressionMethods {


  /**
   * Calculates the parameters a and b of an fitting function like: <br>
   * <&nbsp;> y = a+b*x <br>
   * for the indenpendent variable X, that estimates y for a given x.
   * @param x a MathVector which represents the independent variable X
   * @param y a MathVector which contains the known y values
   * @return A double array of length two. The first entry contains parameter a<br>
   * and the second entry contains parameter b.
   * @throws NotEnoughValues if not enough values for regression are given
   */
  public static  double[] linear(double[] x, double[] y) throws NotEnoughValues {

    MathVector X = new MathVector(x);
    MathVector Y = new MathVector(y);
    double[] result = new double[2];
    double a, b;
    int n;
    
    // trows exception if regression can not be performed
    if (X.size()<2 | Y.size()<2) {
      throw new NotEnoughValues();
    }

    if (X.size() == Y.size()) {
      n = X.size();

      a = (( Y.sum()*X.pow(2).sum() ) - ( X.sum()*X.multiply(Y).sum() )) / ( n*X.pow(2).sum() - Math.pow(X.sum(),2) );

      b = (( n*X.multiply(Y).sum() ) - ( X.sum()*Y.sum() )) / ( n*X.pow(2).sum() - Math.pow(X.sum(),2) );
      
      result[0] = a;
      result[1] = b;
    }
    else {
      throw new ArrayIndexOutOfBoundsException();
    }

    return result;
  }




  /**
   * Calculates the parameters a and b of an fitting function like: <br>
   * <&nbsp;> y = a*e^(b*x) <br>
   * for the indenpendent variable X, that estimates y for a given x.
   * @param x a MathVector which represents the independent variable X
   * @param y a MathVector which contains the known y values
   * @return A double array of length two. The fist entry contains patameter a<br>
   * and the second entry contains parameter b.
   * @throws NotEnoughValues if not enough values for regression are given
   */
  public static  double[] exponential(double[] x, double[] y) throws NotEnoughValues {

    MathVector X = new MathVector(x);
    MathVector Y = new MathVector(y);
    double[] result = new double[2];
    double a, b;
    int n;

    // trows exception if regression can not be performed
    if (X.size()<2 | Y.size()<2) {
      throw new NotEnoughValues();
    }

    if (X.size()==Y.size()) {
      n = X.size();

      a = (( Y.log().sum() * X.pow(2).sum() ) - ( X.sum() * Y.log().multiply(X).sum() )) / ( n*X.pow(2).sum() - Math.pow(X.sum(),2) );
      a = Math.exp(a);

      b = (( n * Y.log().multiply(X).sum() ) - ( X.sum()*Y.log().sum() )) / ( n*X.pow(2).sum() - Math.pow(X.sum(),2) );

      result[0] = a;
      result[1] = b;
    }
    else {
      throw new ArrayIndexOutOfBoundsException();
    }

    return result;
  }


  /**
   * Calculates the parameters a and b of an fitting function like: <br>
   * <&nbsp;> y = a*e^(b*x) <br>
   * for the indenpendent variable X, that estimates y for a given x. This fit
   * gives greater weights to small y values.
   * @param x a MathVector which represents the independent variable X
   * @param y a MathVector which contains the known y values
   * @return A double array of length two. The fist entry contains patameter a<br>
   * and the second entry contains parameter b.
   * @throws NotEnoughValues if not enough values for regression are given
   */
  public static  double[] exponential2(double[] x, double[] y) throws NotEnoughValues {

    MathVector X = new MathVector(x);
    MathVector Y = new MathVector(y);
    double[] result = new double[2];
    double a, b;
    int n;

    // trows exception if regression can not be performed
    if (X.size()<2 | Y.size()<2) {
      throw new NotEnoughValues();
    }

    if (X.size()==Y.size()) {
      n = X.size();

      a = (( X.pow(2).multiply(Y).sum() * Y.log().multiply(Y).sum()) - (X.multiply(Y).sum() * X.multiply(Y).multiply(Y.log()).sum())) / ( Y.sum()*X.pow(2).multiply(Y).sum() - Math.pow(X.multiply(Y).sum(),2) );
      a = Math.exp(a);

      b = (( Y.sum()*X.multiply(Y).multiply(Y.log()).sum() ) - ( X.multiply(Y).sum()*Y.multiply(Y.log()).sum() )) / ( Y.sum()*X.pow(2).multiply(Y).sum() - Math.pow(X.multiply(Y).sum(),2) );

      result[0] = a;
      result[1] = b;
    }
    else {
      throw new ArrayIndexOutOfBoundsException();
    }

    return result;
  }







  /**
   * Calculates the parameters a and b of an fitting function like: <br>
   * <&nbsp;> y = a+b*ln(x) <br>
   * for the indenpendent variable X, that estimates y for a given x.
   * @param x a MathVector which represents the independent variable X
   * @param y a MathVector which contains the known y values
   * @return A double array of length two. The fist entry contains patameter a<br>
   * and the second entry contains parameter b.
   * @throws NotEnoughValues if not enough values for regression are given
   */
  public static  double[] logarithmic(double[] x, double[] y) throws NotEnoughValues {

    MathVector X = new MathVector(x);
    MathVector Y = new MathVector(y);
    double[] result = new double[2];
    double a, b;
    int n;

    // trows exception if regression can not be performed
    if (X.size()<2 | Y.size()<2) {
      throw new NotEnoughValues();
    }

    if (X.size()==Y.size()) {
      n = X.size();

      b= (( n*X.log().multiply(Y).sum() ) - ( Y.sum()*X.log().sum() )) / ( n*X.log().pow(2).sum() - Math.pow(X.log().sum(),2) )  ;

      a= ( Y.sum() - b*X.log().sum() ) / n;

      result[0] = a;
      result[1] = b;
    }
    else {
      throw new ArrayIndexOutOfBoundsException();
    }

    return result;
  }




  /**
   * Calculates the parameters a and b of an fitting function like: <br>
   * <&nbsp;> y = a*x^b <br>
   * for the indenpendent variable X, that estimates y for a given x.
   * @param x a MathVector which represents the independent variable X
   * @param y a MathVector which contains the known y values
   * @return A double array of length two. The fist entry contains patameter a<br>
   * and the second entry contains parameter b.
   * @throws NotEnoughValues if not enough values for regression are given
   */
  public static  double[] power(double[] x, double[] y) throws NotEnoughValues {

    MathVector X = new MathVector(x);
    MathVector Y = new MathVector(y);
    double[] result = new double[2];
    double a, b;
    int n;

    // trows exception if regression can not be performed
    if (X.size()<2 | Y.size()<2) {
      throw new NotEnoughValues();
    }

    if (X.size()==Y.size()) {
      n = X.size();

      b = (( n* X.log().multiply(Y.log()).sum() ) - ( X.log().sum() * Y.log().sum() )) / ( n*X.log().pow(2).sum() - Math.pow(X.log().sum(),2) );

      a = ( Y.log().sum() - (b*X.log().sum()) ) / n;
      a = Math.exp(a);

      result[0] = a;
      result[1] = b;
    }
    else {
      throw new ArrayIndexOutOfBoundsException();
    }

    return result;
  }





  /**
   * Calculates the parameters for polynomial functions like: <br>
   * <&nbsp;> y = a+b*x+c*x^2+d*x^3+...+k*x^(n) <br>
   * @param x a MathVector which represents the independent variable X
   * @param y a MathVector which contains the known y values
   * @param degree the polynoms degree, for a polynom of the n'th degree
   * n+1 datapoints are necessary.
   * @return A double array of length two. The fist entry contains patameter a<br>
   * and the second entry contains parameter b.
   * @throws NotEnoughValues if not enough values for regression are given
   */
  public static  double[] polynomial(double[] x, double[] y, int degree) throws NotEnoughValues {
    Matrix X = new Matrix(x.length,degree+1);
    Matrix Y = new Matrix(y,y.length);
    double[] result = new double[degree+1];

    // trows exception if regression can not be performed
    if ((x.length-1)<(degree) | (y.length-1)<(degree)) {
      throw new NotEnoughValues();
    }

    // fill matrix
    for (int i=0; i<x.length; i++) {
      X.set(i,0,1);
    }
    for (int i=0; i<x.length; i++) {
      for (int j=1; j<(degree+1); j++) {
        X.set(i, j, Math.pow(x[i], j) );
      }
    }

    SingularValueDecomposition svd = new SingularValueDecomposition(X);

    Matrix V = svd.getV();
    Matrix S = svd.getS();

    for (int i=0; i<S.getColumnDimension(); i++) {
      if (S.get(i,i)>0.000000001)
        S.set(i,i,1/S.get(i,i));
      else
        S.set(i,i,0);
    }

    Matrix U = svd.getU();

    result = V.times(S.transpose()).times(U.transpose()).times(Y).getColumnPackedCopy();

    return result;
  }




  /**
   * Calculates the upper and lower confidence borders for given data and
   * quantil alpha (linear regression).
   * @param x_data x values of datapoints used for regression
   * @param y_data y values of datapoints used for regression
   * @param y_reg_data values of regressionfunction corresponding to x_data
   * @param x_line x values of regression curve (later also used for confidence
   * bounds, mostly different from x_data)
   * @param y_line y values of regression curve
   * @param percent size of confidence band ect. 0.8 for 80% confidence
   * @return An Vector of two double arrays. Element one stores the array of
   * the upper confidence border, element two the array for the lower
   * confidence border.
   * @throws NotEnoughValues if not enough values for intervalls are given
   */
  public static Vector getLinearConfidenceIntervall(double[] x_data, double[] y_data, double[] y_reg_data, double[] x_line, double[] y_line, double percent) throws NotEnoughValues {

    if ((x_data.length < 3) | (y_data.length < 3)) {
      throw new NotEnoughValues();
    }

    Vector result = new Vector();
    double s_xx; // variance * (n-1)
    double ms_e;  // mean square error
    double n = (double) x_data.length; // no. values for regression
    double alpha = 1-percent;
    MathVector X = new MathVector(x_data);
    MathVector Y = new MathVector(y_data);
    MathVector Y_reg = new MathVector(y_reg_data);
    MathVector Y_line = new MathVector(y_line);
    MathVector X_line = new MathVector(x_line);
    double mean = X.mean();

    s_xx = X.pow(2).sum() - Math.pow(X.sum(),2)/n;

    ms_e = Y.substract(Y_reg).pow(2).sum() / (n-2.0);

    // upper confidence border
    result.addElement( Y_line.add( X_line.substract(mean).pow(2).divide(s_xx).add(1.0/n).multiply(ms_e).sqrt().multiply( getTvalue(alpha/2.0, (int) (n-2)) )).getArray() );
    // lower confidence border
    result.addElement( Y_line.substract( X_line.substract(mean).pow(2).divide(s_xx).add(1.0/n).multiply(ms_e).sqrt().multiply( getTvalue(alpha/2.0, (int) (n-2)) )).getArray() );

    return result;
  }


  /**
   * Calculates the upper and lower confidence borders for given data and
   * quantil alpha (exponential regression).
   * @param x_data x values of datapoints used for regression
   * @param y_data y values of datapoints used for regression
   * @param y_reg_data values of regressionfunction corresponding to x_data
   * @param x_line x values of regression curve (later also used for confidence
   * bounds, mostly different from x_data)
   * @param y_line y values of regression curve
   * @param percent size of confidence band ect. 0.8 for 80% confidence
   * @return An Vector of two double arrays. Element one stores the array of
   * the upper confidence border, element two the array for the lower
   * confidence border.
   * @throws NotEnoughValues if not enough values for intervalls are given
   */
  public static Vector getExponentialConfidenceIntervall(double[] x_data, double[] y_data, double[] y_reg_data, double[] x_line, double[] y_line, double percent) throws NotEnoughValues {

    try {
      Vector result = new Vector();
      double[] y_data_log = new MathVector(y_data).log().getArray();
      double[] y_reg_data_log = new MathVector(y_reg_data).log().getArray();
      double[] y_line_log = new MathVector(y_line).log().getArray();

      Vector tmp = getLinearConfidenceIntervall(x_data, y_data_log,
                                                y_reg_data_log, x_line,
                                                y_line_log, percent);
      double[] up = new MathVector( (double[]) tmp.elementAt(0)).exp().getArray();
      double[] lo = new MathVector( (double[]) tmp.elementAt(1)).exp().getArray();

      result.add(up);
      result.add(lo);

      return result;
    }
    catch(NotEnoughValues nev) {
      throw nev;
    }
  }



  /**
   * Calculates the upper and lower confidence borders for given data and
   * quantil alpha (logarithmic regression).
   * @param x_data x values of datapoints used for regression
   * @param y_data y values of datapoints used for regression
   * @param y_reg_data values of regressionfunction corresponding to x_data
   * @param x_line x values of regression curve (later also used for confidence
   * bounds, mostly different from x_data)
   * @param y_line y values of regression curve
   * @param percent size of confidence band ect. 0.8 for 80% confidence
   * @return An Vector of two double arrays. Element one stores the array of
   * the upper confidence border, element two the array for the lower
   * confidence border.
   * @throws NotEnoughValues if not enough values for intervalls are given
   */
  public static Vector getLogarithmicConfidenceIntervall(double[] x_data, double[] y_data, double[] y_reg_data, double[] x_line, double[] y_line, double percent) throws NotEnoughValues {

    try {
      Vector result = new Vector();
      double[] x_data_log = new MathVector(x_data).log().getArray();
      double[] x_line_log = new MathVector(x_line).log().getArray();

      result = getLinearConfidenceIntervall(x_data_log, y_data, y_reg_data,
                                            x_line_log, y_line, percent);

      return result;
    }
    catch(NotEnoughValues nev) {
      throw nev;
    }
  }



  /**
   * Calculates the upper and lower confidence borders for given data and
   * quantil alpha (power law regression).
   * @param x_data x values of datapoints used for regression
   * @param y_data y values of datapoints used for regression
   * @param y_reg_data values of regressionfunction corresponding to x_data
   * @param x_line x values of regression curve (later also used for confidence
   * bounds, mostly different from x_data)
   * @param y_line y values of regression curve
   * @param percent size of confidence band ect. 0.8 for 80% confidence
   * @return An Vector of two double arrays. Element one stores the array of
   * the upper confidence border, element two the array for the lower
   * confidence border.
   * @throws NotEnoughValues if not enough values for intervalls are given
   */
  public static Vector getPowerConfidenceIntervall(double[] x_data, double[] y_data, double[] y_reg_data, double[] x_line, double[] y_line, double percent) throws NotEnoughValues {

    try {
      Vector result = new Vector();
      double[] x_data_log = new MathVector(x_data).log().getArray();
      double[] y_data_log = new MathVector(y_data).log().getArray();
      double[] y_reg_data_log = new MathVector(y_reg_data).log().getArray();
      double[] x_line_log = new MathVector(x_line).log().getArray();
      double[] y_line_log = new MathVector(y_line).log().getArray();

      Vector tmp = getLinearConfidenceIntervall(x_data_log, y_data_log,
                                                y_reg_data_log, x_line_log,
                                                y_line_log, percent);
      double[] up = new MathVector( (double[]) tmp.elementAt(0)).exp().getArray();
      double[] lo = new MathVector( (double[]) tmp.elementAt(1)).exp().getArray();

      result.add(up);
      result.add(lo);

      return result;
    }
    catch(NotEnoughValues nev) {
      throw nev;
    }
  }



  /**
   * Claculates upper and lower confidence bounds for plynomial regression curve.
   * @param x_data x values of datapoints used for regression
   * @param y_data y values of datapoints used for regression
   * @param y_reg_data values of regressionfunction corresponding to x_data
   * @param x_line x values of regression curve (later also used for confidence
   * bounds, mostly different from x_data)
   * @param y_line y values of regression curve
   * @param degree polynomial degree
   * @param percent size of confidence band ect. 0.8 for 80% confidence
   * @return An Vector of two double arrays. Element one stores the array of
   * the upper confidence border, element two the array for the lower
   * confidence border.
   * @throws NotEnoughValues if not enough values for intervalls are given
   */
  public static Vector getPolynomialConfidenceIntervall(double[] x_data, double[] y_data, double[] y_reg_data, double[] x_line, double[] y_line, int degree, double percent) throws NotEnoughValues {

    Vector result = new Vector();
    int n = x_data.length;

    // calculate remaining degrees of freedom (after regression)
    int degrees_freedom = y_data.length - (degree+1);

    if (degrees_freedom < 1) {
      throw new NotEnoughValues();
    }

    // calculate norm of residuals
    MathVector Y_data = new MathVector(y_data);
    MathVector Y_reg = new MathVector(y_reg_data);
    MathVector residuals = Y_data.substract(Y_reg);
    double residuals_norm = Math.sqrt(residuals.pow(2).sum());

    // calculate quantil for t-value
    double alpha = 1.0-percent;

    // construct Vandermonde Matrix for QR-Decomposition of regression
    Matrix V = new Matrix(n ,degree+1, 1);
    for (int i=0; i<n; i++) {
      for (int j=1; j<=degree; j++) {
        V.set(i,(degree-j), Math.pow(x_data[i],j) );
      }
    }

    // qr decomposition of x-data Vanermonde Matrix
    Matrix R = V.qr().getR();

    n = x_line.length;

    // construct Vandermonde-Matrix for line data
    V = new Matrix(n,degree+1,1);
    for (int i=0; i<n; i++) {
      for (int j=1; j<=degree; j++) {
        V.set(i,degree-j, Math.pow(x_line[i],j) );
      }
    }

    Matrix E = V.times(R.inverse());
    Matrix tmp = E.arrayTimes(E).transpose();
    MathVector e = new MathVector(n);
    double col_sum;
    for (int i=0; i<n; i++) {
      col_sum = 0.0;
      for (int j=0; j<=degree; j++) {
        col_sum += tmp.get(j,i);
      }
      e.set(i,col_sum);
    }
    e = e.sqrt();

    MathVector delta = new MathVector(n);
    delta = e.multiply(residuals_norm/Math.sqrt(degrees_freedom)).multiply(getTvalue(alpha/2.0,degrees_freedom));

    double[] up = new MathVector(y_line).add(delta).getArray();
    double[] lo = new MathVector(y_line).substract(delta).getArray();

    result.add(up);
    result.add(lo);

    return result;
  }



  /**
   * Claculates upper and lower confidence bounds for firther predictions.
   * @param x_data x values of datapoints used for regression
   * @param y_data y values of datapoints used for regression
   * @param y_reg_data values of regressionfunction corresponding to x_data
   * @param x_line x values of regression curve (later also used for confidence
   * bounds, mostly different from x_data)
   * @param y_line y values of regression curve
   * @param degree polynomial degree
   * @param percent size of confidence band ect. 0.8 for 80% confidence
   * @return An Vector of two double arrays. Element one stores the array of
   * the upper prediction border, element two the array for the lower
   * prediction border.
   * @throws NotEnoughValues if not enough values for intervalls are given
   */
  public static Vector getPolynomialPredictionIntervall(double[] x_data, double[] y_data, double[] y_reg_data, double[] x_line, double[] y_line, int degree, double percent) throws NotEnoughValues {

    Vector result = new Vector();
    int n = x_data.length;

    // calculate remaining degrees of freedom (after regression)
    int degrees_freedom = y_data.length - (degree+1);

    if (degrees_freedom < 1) {
      throw new NotEnoughValues();
    }

    // calculate norm of residuals
    MathVector Y_data = new MathVector(y_data);
    MathVector Y_reg = new MathVector(y_reg_data);
    MathVector residuals = Y_data.substract(Y_reg);
    double residuals_norm = Math.sqrt(residuals.pow(2).sum());

    // calculate quantil for t-value
    double alpha = 1.0-percent;

    // construct Vandermonde Matrix for QR-Decomposition of regression
    Matrix V = new Matrix(n ,degree+1, 1);
    for (int i=0; i<n; i++) {
      for (int j=1; j<=degree; j++) {
        V.set(i,(degree-j), Math.pow(x_data[i],j) );
      }
    }

    // qr decomposition of x-data Vanermonde Matrix
    Matrix R = V.qr().getR();

    n = x_line.length;

    // construct Vandermonde-Matrix for line data
    V = new Matrix(n,degree+1,1);
    for (int i=0; i<n; i++) {
      for (int j=1; j<=degree; j++) {
        V.set(i,degree-j, Math.pow(x_line[i],j) );
      }
    }

    Matrix E = V.times(R.inverse());
    Matrix tmp = E.arrayTimes(E).transpose();
    MathVector e = new MathVector(n);
    double col_sum;
    for (int i=0; i<n; i++) {
      col_sum = 0.0;
      for (int j=0; j<=degree; j++) {
        col_sum += tmp.get(j,i);
      }
      e.set(i,col_sum);
    }
    e = e.add(1.0).sqrt();

    MathVector delta = new MathVector(n);
    delta = e.multiply(residuals_norm/Math.sqrt(degrees_freedom)).multiply(getTvalue(alpha/2.0,degrees_freedom));

    double[] up = new MathVector(y_line).add(delta).getArray();
    double[] lo = new MathVector(y_line).substract(delta).getArray();

    result.add(up);
    result.add(lo);

    return result;
  }










  /**
   * Calculates the upper and lower confidence borders for given data and
   * quantil alpha (linear regression).
   * @param x_data x values of datapoints used for regression
   * @param y_data y values of datapoints used for regression
   * @param y_reg_data values of regressionfunction corresponding to x_data
   * @param x_line x values of regression curve (later also used for confidence
   * bounds, mostly different from x_data)
   * @param y_line y values of regression curve
   * @param percent size of confidence band ect. 0.8 for 80% confidence
   * @return An Vector of two double arrays. Element one stores the array of
   * the upper confidence border, element two the array for the lower
   * confidence border.
   * @throws NotEnoughValues if not enough values for intervalls are given
   */
  public static Vector getLinearPredictionIntervall(double[] x_data, double[] y_data, double[] y_reg_data, double[] x_line, double[] y_line, double percent) throws NotEnoughValues {

    if ((x_data.length < 3) | (y_data.length < 3)) {
      throw new NotEnoughValues();
    }

    Vector result = new Vector();
    double s_xx; // variance * (n-1)
    double ms_e;  // mean square error
    double n = (double) x_data.length; // no. values for regression
    double alpha = 1-percent;
    MathVector X = new MathVector(x_data);
    MathVector Y = new MathVector(y_data);
    MathVector Y_reg = new MathVector(y_reg_data);
    MathVector Y_line = new MathVector(y_line);
    MathVector X_line = new MathVector(x_line);
    double mean = X.mean();

    s_xx = X.pow(2).sum() - Math.pow(X.sum(),2)/n;

    ms_e = Y.substract(Y_reg).pow(2).sum() / (n-2.0);

    // upper confidence border
    result.addElement( Y_line.add( X_line.substract(mean).pow(2).divide(s_xx).add(1 + 1.0/n).multiply(ms_e).sqrt().multiply( getTvalue(alpha/2.0, (int) (n-2)) )).getArray() );
    // lower confidence border
    result.addElement( Y_line.substract( X_line.substract(mean).pow(2).divide(s_xx).add(1 + 1.0/n).multiply(ms_e).sqrt().multiply( getTvalue(alpha/2.0, (int) (n-2)) )).getArray() );

    return result;
  }


  /**
   * Calculates the upper and lower confidence borders for given data and
   * quantil alpha (exponential regression).
   * @param x_data x values of datapoints used for regression
   * @param y_data y values of datapoints used for regression
   * @param y_reg_data values of regressionfunction corresponding to x_data
   * @param x_line x values of regression curve (later also used for confidence
   * bounds, mostly different from x_data)
   * @param y_line y values of regression curve
   * @param percent size of confidence band ect. 0.8 for 80% confidence
   * @return An Vector of two double arrays. Element one stores the array of
   * the upper confidence border, element two the array for the lower
   * confidence border.
   * @throws NotEnoughValues if not enough values for intervalls are given
   */
  public static Vector getExponentialPredictionIntervall(double[] x_data, double[] y_data, double[] y_reg_data, double[] x_line, double[] y_line, double percent) throws NotEnoughValues {

    try {
      Vector result = new Vector();
      double[] y_data_log = new MathVector(y_data).log().getArray();
      double[] y_reg_data_log = new MathVector(y_reg_data).log().getArray();
      double[] y_line_log = new MathVector(y_line).log().getArray();

      Vector tmp = getLinearPredictionIntervall(x_data, y_data_log,
                                                y_reg_data_log, x_line,
                                                y_line_log, percent);
      double[] up = new MathVector( (double[]) tmp.elementAt(0)).exp().getArray();
      double[] lo = new MathVector( (double[]) tmp.elementAt(1)).exp().getArray();

      result.add(up);
      result.add(lo);

      return result;
    }
    catch(NotEnoughValues nev) {
      throw nev;
    }
  }



  /**
   * Calculates the upper and lower confidence borders for given data and
   * quantil alpha (logarithmic regression).
   * @param x_data x values of datapoints used for regression
   * @param y_data y values of datapoints used for regression
   * @param y_reg_data values of regressionfunction corresponding to x_data
   * @param x_line x values of regression curve (later also used for confidence
   * bounds, mostly different from x_data)
   * @param y_line y values of regression curve
   * @param percent size of confidence band ect. 0.8 for 80% confidence
   * @return An Vector of two double arrays. Element one stores the array of
   * the upper confidence border, element two the array for the lower
   * confidence border.
   * @throws NotEnoughValues if not enough values for intervalls are given
   */
  public static Vector getLogarithmicPredictionIntervall(double[] x_data, double[] y_data, double[] y_reg_data, double[] x_line, double[] y_line, double percent) throws NotEnoughValues  {

    try {
      Vector result = new Vector();
      double[] x_data_log = new MathVector(x_data).log().getArray();
      double[] x_line_log = new MathVector(x_line).log().getArray();

      result = getLinearPredictionIntervall(x_data_log, y_data, y_reg_data,
                                            x_line_log, y_line, percent);

      return result;
    }
    catch(NotEnoughValues nev) {
      throw nev;
    }

  }



  /**
   * Calculates the upper and lower confidence borders for given data and
   * quantil alpha (power law regression).
   * @param x_data x values of datapoints used for regression
   * @param y_data y values of datapoints used for regression
   * @param y_reg_data values of regressionfunction corresponding to x_data
   * @param x_line x values of regression curve (later also used for confidence
   * bounds, mostly different from x_data)
   * @param y_line y values of regression curve
   * @param percent size of confidence band ect. 0.8 for 80% confidence
   * @return An Vector of two double arrays. Element one stores the array of
   * the upper confidence border, element two the array for the lower
   * confidence border.
   * @throws NotEnoughValues if not enough values for intervalls are given
   */
  public static Vector getPowerPredictionIntervall(double[] x_data, double[] y_data, double[] y_reg_data, double[] x_line, double[] y_line, double percent) throws NotEnoughValues {

    try {
      Vector result = new Vector();
      double[] x_data_log = new MathVector(x_data).log().getArray();
      double[] y_data_log = new MathVector(y_data).log().getArray();
      double[] y_reg_data_log = new MathVector(y_reg_data).log().getArray();
      double[] x_line_log = new MathVector(x_line).log().getArray();
      double[] y_line_log = new MathVector(y_line).log().getArray();

      Vector tmp = getLinearPredictionIntervall(x_data_log, y_data_log,
                                                y_reg_data_log, x_line_log,
                                                y_line_log, percent);
      double[] up = new MathVector( (double[]) tmp.elementAt(0)).exp().getArray();
      double[] lo = new MathVector( (double[]) tmp.elementAt(1)).exp().getArray();

      result.add(up);
      result.add(lo);

      return result;
    }
    catch(NotEnoughValues nev) {
      throw nev;
    }
  }













/*
  public  static double vaiance(double[] x, double[] y) {
    double result;

    MathVector X = new MathVector(x);
    MathVector Y = new MathVector(y);

    double mean = Y.mean();

    double une_streu = Y.substract(X).pow(2).sum();
    double erk_streu = X.substract(mean).pow(2).sum();
    double ges_streu = une_streu + erk_streu;

    result = erk_streu / ges_streu;

    return result;
  }
*/


  public static double variance(double[] values) {
    MathVector vec = new MathVector(values);
    return vec.substract(vec.mean()).pow(2).sum()/(values.length-1);
  }

  public static double covariance(double[] x, double[] y) {
    MathVector X = new MathVector(x);
    MathVector Y = new MathVector(y);

    return X.substract(X.mean()).multiply(Y.substract(Y.mean())).sum()/(x.length-1);
  }


  /**
   * Calculates the pearson correlation coefficient between two random
   * variables.
   * @param x one random variable
   * @param y the other randomvariable
   * @return the correaltion coefficient
   * @throws ZeroVarianceException if the variance of a random variable equals
   * zero
   */
  public static double corrCoeff(double[] x, double[] y) throws ZeroVarianceException {

    double var_x, var_y;
    double cov_xy;

    var_x = variance(x);
    var_y = variance(y);
    cov_xy = covariance(x,y);

    if (var_x==0) {
      throw new ZeroVarianceException("x");
    }
    else if (var_y==0) {
      throw new ZeroVarianceException("y");
    }
    else {
      return cov_xy / Math.sqrt(var_x * var_y);
    }

  }






/*
  public static double calculateError(double[] x, double[] y) throws ArithmeticException{
    //return (x.length-1d)*(calculateVariance(y)-Math.pow(calculateCovariance(x,y),2)/calculateVariance(x));
    try {
      return Math.sqrt( (x.length - 1d) * (1 - Math.pow(corrCoeff(x, y), 2)) *
                       variance(y) / x.length);
    }
    catch(ZeroVarianceException zve) {
      throw new ArithmeticException();
    }
  }

  // x enth�lt bereits die werte der regressionskurve
  public static double maxError(double[] x, double[] y) throws ArithmeticException{
    if(x.length!=y.length)
      throw new ArithmeticException("x[] and y[] must have same length");
    double maxError=0;

    //double slope=calculateSlope(x,y);
    //double intercept=calculateIntercept(x,y);
    for(int i=0;i<x.length;i++){
     double ey=Math.abs(y[i]-x[i]);
      maxError = maxError > ey ? maxError : ey;
    }
    return maxError;
  }

  public static double minError(double[] x, double[] y) throws ArithmeticException{
    if(x.length!=y.length)
      throw new ArithmeticException("x[] and y[] must have same length");
    double minError=0;
//    double slope=calculateSlope(x,y);
//    double intercept=calculateIntercept(x,y);
    for(int i=0;i<x.length;i++){
     double ey=Math.abs(y[i]-x[i]);
      minError = minError < ey ? minError : ey;
    }
    return minError;
  }
*/



  /**
   * Returns the names of the implemented regresson methods.
   * @return names of regression methods
   */
  public static String[] getMethodsNames() {
    String[] result = {"exponential", "exponential2", "linear", "logarithmic", "polynomial", "power"};
    return result;
  }

  /**
   * Returns the ids of the implemented regression methods.
   * @return ids of regression methods
   */
  public static String[] getMethodIds() {
    String[] result = {"exp", "exp2", "lin", "log", "pol", "pwr"};
    return result;
  }



  /**
   * Calculates the factorial of an given parameter.
   * @param n parameter
   * @return the factorial of n
   */
  private static int factorial(int n) {
    int result = 1;

    for (int i=2; i<=n; i++) {
      result = result*i;
    }

    return result;
  }



  /**
   * Calculates n atop k
   * @param n upper parameter
   * @param k lower parameter
   * @return n atop k
   */
  private static int atop(int n, int k) {

    int result = factorial(n) / ( factorial(k)*factorial(n-k) );

    return result;
  }


  /**
   * Gives value of inverse student distribiution.
   * @param alpha the quantile (1-alpha)
   * @param n degrees of freedom
   * @return the t value
   */
  private static double getTvalue(double alpha, int n) {

    double[] act;
    // 80% -> 90
    double[] alph_10  = {3.0780,1.8860,1.6380,1.5330,1.4760,1.4400,1.4150,1.3970,1.3830,1.3720,1.3630,1.3560,1.3500,1.3450,1.3410,1.3370,1.3330,1.3300,1.3280,1.3250,1.3230,1.3210,1.3190,1.3180,1.3160,1.3150,1.3140,1.3130,1.3110,1.3100,1.3090,1.3090,1.3080,1.3070,1.3060,1.3060,1.3050,1.3040,1.3040,1.3030,1.3030,1.3020,1.3020,1.3010,1.3010,1.3000,1.3000,1.2990,1.2990,1.2990,1.2980,1.2980,1.2980,1.2970,1.2970,1.2970,1.2970,1.2960,1.2960,1.2960,1.2960,1.2950,1.2950,1.2950,1.2950,1.2950,1.2940,1.2940,1.2940,1.2940,1.2940,1.2930,1.2930,1.2930,1.2930,1.2930,1.2930,1.2920,1.2920,1.2920,1.2920,1.2920,1.2920,1.2920,1.2920,1.2910,1.2910,1.2910,1.2910,1.2910,1.2910,1.2910,1.2910,1.2910,1.2910,1.2900,1.2900,1.2900,1.2900,1.2900,1.2820};
    // 90% -> 95
    double[] alph_05  = {6.3140,2.9200,2.3530,2.1320,2.0150,1.9430,1.8950,1.8600,1.8330,1.8120,1.7960,1.7820,1.7710,1.7610,1.7530,1.7460,1.7400,1.7340,1.7290,1.7250,1.7210,1.7170,1.7140,1.7110,1.7080,1.7060,1.7030,1.7010,1.6990,1.6970,1.6960,1.6940,1.6920,1.6910,1.6900,1.6880,1.6870,1.6860,1.6850,1.6840,1.6830,1.6820,1.6810,1.6800,1.6790,1.6790,1.6780,1.6770,1.6770,1.6760,1.6750,1.6750,1.6740,1.6740,1.6730,1.6730,1.6720,1.6720,1.6710,1.6710,1.6700,1.6700,1.6690,1.6690,1.6690,1.6680,1.6680,1.6680,1.6670,1.6670,1.6670,1.6660,1.6660,1.6660,1.6650,1.6650,1.6650,1.6650,1.6640,1.6640,1.6640,1.6640,1.6630,1.6630,1.6630,1.6630,1.6630,1.6620,1.6620,1.6620,1.6620,1.6620,1.6610,1.6610,1.6610,1.6610,1.6610,1.6610,1.6600,1.6600,1.6450};
    // 95% -> 97.5
    double[] alph_025 = {12.706,4.3030,3.1820,2.7760,2.5710,2.4470,2.3650,2.3060,2.2620,2.2280,2.2010,2.1790,2.1600,2.1450,2.1310,2.1200,2.1100,2.1010,2.0930,2.0860,2.0800,2.0740,2.0690,2.0640,2.0600,2.0560,2.0520,2.0480,2.0450,2.0420,2.0400,2.0370,2.0350,2.0320,2.0300,2.0280,2.0260,2.0240,2.0230,2.0210,2.0200,2.0180,2.0170,2.0150,2.0140,2.0130,2.0120,2.0110,2.0100,2.0090,2.0080,2.0070,2.0060,2.0050,2.0040,2.0030,2.0020,2.0020,2.0010,2.0000,2.0000,1.9990,1.9980,1.9980,1.9970,1.9970,1.9960,1.9950,1.9950,1.9940,1.9940,1.9930,1.9930,1.9930,1.9920,1.9920,1.9910,1.9910,1.9900,1.9900,1.9900,1.9890,1.9890,1.9890,1.9880,1.9880,1.9880,1.9870,1.9870,1.9870,1.9860,1.9860,1.9860,1.9860,1.9850,1.9850,1.9850,1.9840,1.9840,1.9840,1.9600};


    if (alpha > 0.09) {
      act = alph_10;
//      System.out.println("alpha 10");
    }
    else if(alpha > 0.04) {
      act = alph_05;
//      System.out.println("alpha 5");
    }
    else {
      act = alph_025;
//      System.out.println("alpha 2.5");
    }

    if (n <101) {
      return act[n-1];
    }
    else {
      return act[100];
    }

  }




}

