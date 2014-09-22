package br.com.anteros.core.scanner;

/**
 * This interface is designed to apply to resource scanner as a filter 
 *
 */
public interface ClassPathResourceFilter  {
	/**
	 * Tells whether the given subject is accepted or not.
	 * It always returns true if the given subject is not filterable
	 * @param subject parameter to check
	 * @return true if accepted
	 */
	public  boolean  accept(Object subject);
	/**
	 * Tells if the given subject is filterable by this interface
	 * @param subject 
	 * @return true if the given subject is target to this filter
	 */
	public boolean filterable(Object subject);
}
