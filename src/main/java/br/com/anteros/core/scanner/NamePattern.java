package br.com.anteros.core.scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class NamePattern {

	static final Integer NAME_TYPE_PACKAGE = 1;
	static final Integer NAME_TYPE_RESOURCE = 2;
	static final Integer NAME_TYPE_CLASS = 3;
	static final Integer NAME_TYPE_DIRECTORY = 4;
	static final Integer NAME_TYPE_ARCHIVE = 5;
	private static final Pattern GRAB_SP_CHARS = Pattern.compile("([\\\\*+\\[\\](){}\\$.?\\^|])");
	
	private Pattern pattern;
	private boolean negative;
	
	NamePattern(String name) {
		this.negative = name.startsWith("!");
		String ptrn = negative ? prepName(name.substring(1)):prepName(name);
		this.pattern = Pattern.compile(ptrn);
	}
	
	Pattern getPattern() {
		return pattern;
	}
	boolean isNegative() {
		return negative;
	}
	boolean matches(String name){
		if(pattern.matcher(name).matches()){
			return !negative;
		}else{
			return negative;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (negative ? 1231 : 1237);
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamePattern other = (NamePattern) obj;
		if (negative != other.negative)
			return false;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		return true;
	}

	/**
	 * Escapes regular expression sensitive characters with in a regular text 
	 * @param str a string 
	 * @return regex escaped version of the given text 
	 */
	private static String escapeRE(String str) {
		if(str == null) return str;
		Matcher match = GRAB_SP_CHARS.matcher(str);
		return match.replaceAll("\\\\$1");
	}
	/**
	 * Converts given class or package name to regex pattern string, * char converted to .* and _ converted to . regex wildchars. 
	 * @param name a name string for class names, package names, etc 
	 * @return regex escaped version of the given text 
	 */
	private static String prepName(String name){
		return escapeRE(name.replaceAll("(\\.\\*)|\\*", "##-##")).replace(
				"##-##", ".*").replace("_", ".");
	}

	@Override
	public String toString() {
		return "NamePattern [negative=" + negative +",pattern=" + pattern  
				+ "]";
	}
	
	
}
