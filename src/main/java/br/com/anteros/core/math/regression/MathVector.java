package br.com.anteros.core.math.regression;

/**
 *
 * <p>Title: MathVector</p>
 * <p>Description: This class repreasents a numeric vector with typical <br>
 * functions known from programs like MatLab or R.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Matthias Maneck
 * @version 1.0
 */
public class MathVector {

  private int size = 0;
  private double[] values;



///////////////////////////////////////////////////////////////////////
// constructors
///////////////////////////////////////////////////////////////////////

  /**
   * Constructs a new MathVector with values taken from a double array.
   * @param val A double array which contains all entrys for the MathVector.
   */
  public MathVector(double[] val) {
    values = val;
    size = val.length;
  }

  /**
   * Constructs an empty MathVector of size i.
   * @param i The size of the new MathVector.
   */
  public MathVector(int i) {
    values = new double[i];
    size = i;
  }





///////////////////////////////////////////////////////////////////////
// public methods
///////////////////////////////////////////////////////////////////////


  /**
   * Adds all entrys of this MathVector together.
   * @return The sum of all entrys of this MathVector.
   */
  public synchronized double sum() {
    double result=0;

    for (int i=0; i<size; i++) {
      result += values[i];
    }

    return result;
  }



  /**
   * Calculates the mean of this MathVector.
   * @return The mean of this MathVector.
   */
  public synchronized double mean() {
    return (this.sum()/size);
  }



  /**
   * Retrievs the element at position i.
   * @param i the position of the selected element
   * @return A double value of the selected element.
   */
  public synchronized double at(int i) {
    return values[i];
  }


  /**
   * Sets a specified value at the selected element.
   * @param pos The position of the selected element.
   * @param value The value to which the element at position i is set.
   */
  public synchronized void set( int pos, double value) {
    values[pos] = value;
  }


  /**
   * Retrievs the size of this MathVector.
   * @return The size of this MathVector as an int.
   */
  public synchronized int size() {
    return size;
  }



  /**
   * Adds a MathVector to this MathVector.
   * @param vec The MathVector which is added to this MathVector.
   * @throws java.lang.ArrayIndexOutOfBoundsException
   */
  public synchronized MathVector add(MathVector vec) {

    MathVector result = new MathVector(size);

    for (int i = 0; i < size; i++) {
      result.set(i, values[i] + vec.at(i));
    }

    return result;

  }



  /**
   * Adds the double value k to all elements of this MathVector.
   * @param k A double value which is added to all elements.
   */
  public synchronized MathVector add(double k) {

    MathVector result = new MathVector(size);

    for (int i = 0; i < size; i++) {
      result.set(i, values[i] + k);
    }

    return result;

  }


  public synchronized MathVector sqrt() {

    MathVector result = new MathVector(size);

    for (int i = 0; i < size; i++) {
      result.set(i, Math.sqrt(values[i]));
    }

    return result;

  }



  /**
   * Substracs a given MathVector from the actual one.
   * @param vec A MathVector which is substracted form the actual one.
   * @return A new MathVector as the difference between the actual MathVector
   * and the given one.
   */
  public synchronized MathVector substract(MathVector vec) {

    MathVector result = new MathVector(size);

    for (int i = 0; i < size; i++) {
      result.set(i, values[i] - vec.at(i));
    }

    return result;
  }




  /**
   * Substracs k from all elements of this MathVector.
   * @param k A double value which is substracted form all elements of this
   * MathVector
   * @return A new MathVector as the difference between the actual MathVector
   * and k.
   */
  public synchronized MathVector substract(double k) {

    MathVector result = new MathVector(size);

    for(int i=0; i<size; i++) {
      result.set(i,values[i]-k);
    }

    return result;
  }





  /**
   * Multiplicates a vector to the actual one.
   * @param vec The elements of this vector are multiplied to the elements<br>
   * of this vector.
   * @throws java.lang.ArrayIndexOutOfBoundsException
   */
  public synchronized void mult(MathVector vec) throws ArrayIndexOutOfBoundsException {
    if (size==vec.size()) {
      for(int i=0; i<size; i++) {
        values[i] *= vec.at(i);
      }
    }
    else {
      throw new ArrayIndexOutOfBoundsException();
    }
  }



  /**
   * Multiplies all elements of this vector with k.
   * @param k The double value k is multiplied to all elements of this
   * MathVector.
   */
  public synchronized void mult(double k) {
    for(int i=0; i<size; i++) {
      values[i] *= k;
    }
  }


  /**
   * Creates a new MathVector which is the product of the actual MathVector <br>
   * with a given one.
   * @param vec A MathVector whose elements are multiplied with the elements<br>
   * of the actual MathVector.
   * @return The product of the actual MathVector with vec.
   * @throws java.lang.ArrayIndexOutOfBoundsException
   */
  public synchronized MathVector multiply(MathVector vec) throws ArrayIndexOutOfBoundsException {
    MathVector result = new MathVector(size);

    for (int i=0; i<size; i++) {
      result.set(i, values[i] * vec.at(i));
    }

    return result;
  }


  /**
   * Creates a new MathVector which is a product of the actual one with the
   * double value k.
   * @param k A double value with which all elements of the actual <br>
   * MathVector are multiplied.
   * @return a new MathVector
   */
  public synchronized MathVector multiply(double k) {
    MathVector result = new MathVector(size);

    for (int i=0; i<size; i++) {
      result.set(i, values[i] * k);
    }

    return result;
  }



  /**
   * Creates a new MathVector which is the product of the actual MathVector <br>
   * with a given one.
   * @param vec A MathVector whose elements are multiplied with the elements<br>
   * of the actual MathVector.
   * @return The product of the actual MathVector with vec.
   * @throws java.lang.ArrayIndexOutOfBoundsException
   */
  public synchronized MathVector divide(MathVector vec) throws ArrayIndexOutOfBoundsException {
    MathVector result = new MathVector(size);

    for (int i=0; i<size; i++) {
      result.set(i, values[i] / vec.at(i));
    }

    return result;
  }


  /**
   * Creates a new MathVector which is a product of the actual one with the
   * double value k.
   * @param k A double value with which all elements of the actual <br>
   * MathVector are multiplied.
   * @return a new MathVector
   */
  public synchronized MathVector divide(double k) {
    MathVector result = new MathVector(size);

    for (int i=0; i<size; i++) {
      result.set(i, values[i] / k);
    }

    return result;
  }



  /**
   * Retrieves a new MathVector whose elements are the natural logarithm
   * of the values of the actual MathVector.
   * @return a new MathVector
   */
  public synchronized MathVector log() {
    MathVector result = new MathVector(size);

    for (int i=0; i<size; i++) {
      result.set(i, Math.log(values[i]));
    }

    return result;
  }



  /**
   * Retrieves a new MathVector whose elements are e<sup>i</sup>, if i are
   * the elements of the actual MathVector.
   * @return a new MathVector
   */
  public synchronized MathVector exp() {
    MathVector result = new MathVector(size);

    for (int i=0; i<size; i++) {
      result.set(i, Math.exp(values[i]));
    }

    return result;
  }



  /**
   * Retrieves a new MathVector whose elements are the k times powered <br>
   * elements of the actual MathVector.
   * @param k the power parameter
   * @return a new MathVector
   */
  public synchronized MathVector pow(double k) {
    MathVector result = new MathVector(size);

    for (int i=0; i<size; i++) {
      result.set(i, Math.pow(values[i],k));
    }

    return result;
  }


  /**
   * Returns the maximal value of a MathVector.
   * @return the maximum
   */
  public synchronized double max() {
    double result = values[0];
    for (int i=1; i<size; i++) {
      if (values[i] > result)
        result = values[i];
    }

    return result;
  }



  /**
   * Returns the minimal value of a MathVector.
   * @return the minimum
   */
  public synchronized double min() {
    double result = values[0];
    for (int i=1; i<size; i++) {
      if (values[i]<result)
        result = values[i];
    }

    return result;
  }


  /**
   * Returns the intern double array representation of the MathVector.
   * @return the intern double[]
   */
  public synchronized double[] getArray() {
    return values;
  }

}