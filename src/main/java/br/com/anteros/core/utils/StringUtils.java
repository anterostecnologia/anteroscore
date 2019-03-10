/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package br.com.anteros.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Pattern;
import static java.lang.String.format;

public abstract class StringUtils {

	public static final String REPLACE_WORD_DELIMITERS = "+-*/(),. \n\r";

	private static final String FOLDER_SEPARATOR = "/";

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	private static final String TOP_PATH = "..";

	private static final String CURRENT_PATH = ".";

	private static final char EXTENSION_SEPARATOR = '.';

	public static final String EMPTY = "";

	private static final int PAD_LIMIT = 8192;

	public static String replaceAll(String input, String forReplace, String replaceWith) {
		if (input == null)
			return null;
		StringBuffer result = new StringBuffer();
		boolean hasMore = true;
		while (hasMore) {
			int start = input.toUpperCase().indexOf(forReplace.toUpperCase());
			int end = start + forReplace.length();
			if (start != -1) {
				result.append(input.substring(0, start) + replaceWith);
				input = input.substring(end);
			} else {
				hasMore = false;
				result.append(input);
			}
		}
		if (result.toString().equals(""))
			return input;

		return result.toString();
	}

	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsWhitespace(String str) {
		return containsWhitespace((CharSequence) str);
	}

	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		int index = 0;
		while (buf.length() > index) {
			if (Character.isWhitespace(buf.charAt(index))) {
				buf.deleteCharAt(index);
			} else {
				index++;
			}
		}
		return buf.toString();
	}

	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && buf.charAt(0) == leadingCharacter) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	public static String trimTrailingCharacter(String str, char trailingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && buf.charAt(buf.length() - 1) == trailingCharacter) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}
	
	

    //-----------------------------------------------------------------------

    /**
     * <p>Check if a CharSequence starts with a specified prefix.</p>
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is case sensitive.</p>
     *
     * <pre>
     * StringUtils.startsWith(null, null)      = true
     * StringUtils.startsWith(null, "abc")     = false
     * StringUtils.startsWith("abcdef", null)  = false
     * StringUtils.startsWith("abcdef", "abc") = true
     * StringUtils.startsWith("ABCDEF", "abc") = false
     * </pre>
     *
     * @see java.lang.String#startsWith(String)
     * @param str  the CharSequence to check, may be null
     * @param prefix the prefix to find, may be null
     * @return {@code true} if the CharSequence starts with the prefix, case sensitive, or
     *  both {@code null}
     * @since 2.4
     * @since 3.0 Changed signature from startsWith(String, String) to startsWith(CharSequence, CharSequence)
     */
    public static boolean startsWith(final CharSequence str, final CharSequence prefix) {
        return startsWith(str, prefix, false);
    }

    /**
     * <p>Case insensitive check if a CharSequence starts with a specified prefix.</p>
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is case insensitive.</p>
     *
     * <pre>
     * StringUtils.startsWithIgnoreCase(null, null)      = true
     * StringUtils.startsWithIgnoreCase(null, "abc")     = false
     * StringUtils.startsWithIgnoreCase("abcdef", null)  = false
     * StringUtils.startsWithIgnoreCase("abcdef", "abc") = true
     * StringUtils.startsWithIgnoreCase("ABCDEF", "abc") = true
     * </pre>
     *
     * @see java.lang.String#startsWith(String)
     * @param str  the CharSequence to check, may be null
     * @param prefix the prefix to find, may be null
     * @return {@code true} if the CharSequence starts with the prefix, case insensitive, or
     *  both {@code null}
     * @since 2.4
     * @since 3.0 Changed signature from startsWithIgnoreCase(String, String) to startsWithIgnoreCase(CharSequence, CharSequence)
     */
    public static boolean startsWithIgnoreCase(final CharSequence str, final CharSequence prefix) {
        return startsWith(str, prefix, true);
    }

    /**
     * <p>Check if a CharSequence starts with a specified prefix (optionally case insensitive).</p>
     *
     * @see java.lang.String#startsWith(String)
     * @param str  the CharSequence to check, may be null
     * @param prefix the prefix to find, may be null
     * @param ignoreCase indicates whether the compare should ignore case
     *  (case insensitive) or not.
     * @return {@code true} if the CharSequence starts with the prefix or
     *  both {@code null}
     */
    private static boolean startsWith(final CharSequence str, final CharSequence prefix, final boolean ignoreCase) {
        if (str == null || prefix == null) {
            return str == prefix;
        }
        if (prefix.length() > str.length()) {
            return false;
        }
        return regionMatches(str, ignoreCase, 0, prefix, 0, prefix.length());
    }
    
    /**
     * Green implementation of regionMatches.
     *
     * @param cs the {@code CharSequence} to be processed
     * @param ignoreCase whether or not to be case insensitive
     * @param thisStart the index to start on the {@code cs} CharSequence
     * @param substring the {@code CharSequence} to be looked for
     * @param start the index to start on the {@code substring} CharSequence
     * @param length character length of the region
     * @return whether the region matched
     */
    static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
            final CharSequence substring, final int start, final int length)    {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        // Extract these first so we detect NPEs the same as the java.lang.String version
        final int srcLen = cs.length() - thisStart;
        final int otherLen = substring.length() - start;

        // Check for invalid parameters
        if (thisStart < 0 || start < 0 || length < 0) {
            return false;
        }

        // Check that the regions are long enough
        if (srcLen < length || otherLen < length) {
            return false;
        }

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            // The same check as in String.regionMatches():
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                    && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>Check if a CharSequence starts with any of the provided case-sensitive prefixes.</p>
     *
     * <pre>
     * StringUtils.startsWithAny(null, null)      = false
     * StringUtils.startsWithAny(null, new String[] {"abc"})  = false
     * StringUtils.startsWithAny("abcxyz", null)     = false
     * StringUtils.startsWithAny("abcxyz", new String[] {""}) = true
     * StringUtils.startsWithAny("abcxyz", new String[] {"abc"}) = true
     * StringUtils.startsWithAny("abcxyz", new String[] {null, "xyz", "abc"}) = true
     * StringUtils.startsWithAny("abcxyz", null, "xyz", "ABCX") = false
     * StringUtils.startsWithAny("ABCXYZ", null, "xyz", "abc") = false
     * </pre>
     *
     * @param sequence the CharSequence to check, may be null
     * @param searchStrings the case-sensitive CharSequence prefixes, may be empty or contain {@code null}
     * @see StringUtils#startsWith(CharSequence, CharSequence)
     * @return {@code true} if the input {@code sequence} is {@code null} AND no {@code searchStrings} are provided, or
     *   the input {@code sequence} begins with any of the provided case-sensitive {@code searchStrings}.
     * @since 2.5
     * @since 3.0 Changed signature from startsWithAny(String, String[]) to startsWithAny(CharSequence, CharSequence...)
     */
    public static boolean startsWithAny(final CharSequence sequence, final CharSequence... searchStrings) {
        if (isEmpty(sequence) || ArrayUtils.isEmpty(searchStrings)) {
            return false;
        }
        for (final CharSequence searchString : searchStrings) {
            if (startsWith(sequence, searchString)) {
                return true;
            }
        }
        return false;
    }

    // endsWith
    //-----------------------------------------------------------------------

    /**
     * <p>Check if a CharSequence ends with a specified suffix.</p>
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is case sensitive.</p>
     *
     * <pre>
     * StringUtils.endsWith(null, null)      = true
     * StringUtils.endsWith(null, "def")     = false
     * StringUtils.endsWith("abcdef", null)  = false
     * StringUtils.endsWith("abcdef", "def") = true
     * StringUtils.endsWith("ABCDEF", "def") = false
     * StringUtils.endsWith("ABCDEF", "cde") = false
     * StringUtils.endsWith("ABCDEF", "")    = true
     * </pre>
     *
     * @see java.lang.String#endsWith(String)
     * @param str  the CharSequence to check, may be null
     * @param suffix the suffix to find, may be null
     * @return {@code true} if the CharSequence ends with the suffix, case sensitive, or
     *  both {@code null}
     * @since 2.4
     * @since 3.0 Changed signature from endsWith(String, String) to endsWith(CharSequence, CharSequence)
     */
    public static boolean endsWith(final CharSequence str, final CharSequence suffix) {
        return endsWith(str, suffix, false);
    }

    /**
     * <p>Case insensitive check if a CharSequence ends with a specified suffix.</p>
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is case insensitive.</p>
     *
     * <pre>
     * StringUtils.endsWithIgnoreCase(null, null)      = true
     * StringUtils.endsWithIgnoreCase(null, "def")     = false
     * StringUtils.endsWithIgnoreCase("abcdef", null)  = false
     * StringUtils.endsWithIgnoreCase("abcdef", "def") = true
     * StringUtils.endsWithIgnoreCase("ABCDEF", "def") = true
     * StringUtils.endsWithIgnoreCase("ABCDEF", "cde") = false
     * </pre>
     *
     * @see java.lang.String#endsWith(String)
     * @param str  the CharSequence to check, may be null
     * @param suffix the suffix to find, may be null
     * @return {@code true} if the CharSequence ends with the suffix, case insensitive, or
     *  both {@code null}
     * @since 2.4
     * @since 3.0 Changed signature from endsWithIgnoreCase(String, String) to endsWithIgnoreCase(CharSequence, CharSequence)
     */
    public static boolean endsWithIgnoreCase(final CharSequence str, final CharSequence suffix) {
        return endsWith(str, suffix, true);
    }

    /**
     * <p>Check if a CharSequence ends with a specified suffix (optionally case insensitive).</p>
     *
     * @see java.lang.String#endsWith(String)
     * @param str  the CharSequence to check, may be null
     * @param suffix the suffix to find, may be null
     * @param ignoreCase indicates whether the compare should ignore case
     *  (case insensitive) or not.
     * @return {@code true} if the CharSequence starts with the prefix or
     *  both {@code null}
     */
    private static boolean endsWith(final CharSequence str, final CharSequence suffix, final boolean ignoreCase) {
        if (str == null || suffix == null) {
            return str == suffix;
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        final int strOffset = str.length() - suffix.length();
        return regionMatches(str, ignoreCase, strOffset, suffix, 0, suffix.length());
    }
	

	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}

		String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}
	
	
	// Equals
    //-----------------------------------------------------------------------
    /**
     * <p>Compares two CharSequences, returning {@code true} if they represent
     * equal sequences of characters.</p>
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is <strong>case sensitive</strong>.</p>
     *
     * <pre>
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     * </pre>
     *
     * @param cs1  the first CharSequence, may be {@code null}
     * @param cs2  the second CharSequence, may be {@code null}
     * @return {@code true} if the CharSequences are equal (case-sensitive), or both {@code null}
     * @since 3.0 Changed signature from equals(String, String) to equals(CharSequence, CharSequence)
     * @see Object#equals(Object)
     * @see #equalsIgnoreCase(CharSequence, CharSequence)
     */
    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            return cs1.equals(cs2);
        }
        // Step-wise comparison
        final int length = cs1.length();
        for (int i = 0; i < length; i++) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Compares two CharSequences, returning {@code true} if they represent
     * equal sequences of characters, ignoring case.</p>
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered equal. The comparison is <strong>case insensitive</strong>.</p>
     *
     * <pre>
     * StringUtils.equalsIgnoreCase(null, null)   = true
     * StringUtils.equalsIgnoreCase(null, "abc")  = false
     * StringUtils.equalsIgnoreCase("abc", null)  = false
     * StringUtils.equalsIgnoreCase("abc", "abc") = true
     * StringUtils.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param cs1  the first CharSequence, may be {@code null}
     * @param cs2  the second CharSequence, may be {@code null}
     * @return {@code true} if the CharSequences are equal (case-insensitive), or both {@code null}
     * @since 3.0 Changed signature from equalsIgnoreCase(String, String) to equalsIgnoreCase(CharSequence, CharSequence)
     * @see #equals(CharSequence, CharSequence)
     */
    public static boolean equalsIgnoreCase(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        return regionMatches(cs1, true, 0, cs2, 0, cs1.length());
    }

    // Compare
    //-----------------------------------------------------------------------
    /**
     * <p>Compare two Strings lexicographically, as per {@link String#compareTo(String)}, returning :</p>
     * <ul>
     *  <li>{@code int = 0}, if {@code str1} is equal to {@code str2} (or both {@code null})</li>
     *  <li>{@code int < 0}, if {@code str1} is less than {@code str2}</li>
     *  <li>{@code int > 0}, if {@code str1} is greater than {@code str2}</li>
     * </ul>
     *
     * <p>This is a {@code null} safe version of :</p>
     * <blockquote><pre>str1.compareTo(str2)</pre></blockquote>
     *
     * <p>{@code null} value is considered less than non-{@code null} value.
     * Two {@code null} references are considered equal.</p>
     *
     * <pre>
     * StringUtils.compare(null, null)   = 0
     * StringUtils.compare(null , "a")   &lt; 0
     * StringUtils.compare("a", null)    &gt; 0
     * StringUtils.compare("abc", "abc") = 0
     * StringUtils.compare("a", "b")     &lt; 0
     * StringUtils.compare("b", "a")     &gt; 0
     * StringUtils.compare("a", "B")     &gt; 0
     * StringUtils.compare("ab", "abc")  &lt; 0
     * </pre>
     *
     * @see #compare(String, String, boolean)
     * @see String#compareTo(String)
     * @param str1  the String to compare from
     * @param str2  the String to compare to
     * @return &lt; 0, 0, &gt; 0, if {@code str1} is respectively less, equal or greater than {@code str2}
     * @since 3.5
     */
    public static int compare(final String str1, final String str2) {
        return compare(str1, str2, true);
    }

    /**
     * <p>Compare two Strings lexicographically, as per {@link String#compareTo(String)}, returning :</p>
     * <ul>
     *  <li>{@code int = 0}, if {@code str1} is equal to {@code str2} (or both {@code null})</li>
     *  <li>{@code int < 0}, if {@code str1} is less than {@code str2}</li>
     *  <li>{@code int > 0}, if {@code str1} is greater than {@code str2}</li>
     * </ul>
     *
     * <p>This is a {@code null} safe version of :</p>
     * <blockquote><pre>str1.compareTo(str2)</pre></blockquote>
     *
     * <p>{@code null} inputs are handled according to the {@code nullIsLess} parameter.
     * Two {@code null} references are considered equal.</p>
     *
     * <pre>
     * StringUtils.compare(null, null, *)     = 0
     * StringUtils.compare(null , "a", true)  &lt; 0
     * StringUtils.compare(null , "a", false) &gt; 0
     * StringUtils.compare("a", null, true)   &gt; 0
     * StringUtils.compare("a", null, false)  &lt; 0
     * StringUtils.compare("abc", "abc", *)   = 0
     * StringUtils.compare("a", "b", *)       &lt; 0
     * StringUtils.compare("b", "a", *)       &gt; 0
     * StringUtils.compare("a", "B", *)       &gt; 0
     * StringUtils.compare("ab", "abc", *)    &lt; 0
     * </pre>
     *
     * @see String#compareTo(String)
     * @param str1  the String to compare from
     * @param str2  the String to compare to
     * @param nullIsLess  whether consider {@code null} value less than non-{@code null} value
     * @return &lt; 0, 0, &gt; 0, if {@code str1} is respectively less, equal ou greater than {@code str2}
     * @since 3.5
     */
    public static int compare(final String str1, final String str2, final boolean nullIsLess) {
        if (str1 == str2) {
            return 0;
        }
        if (str1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (str2 == null) {
            return nullIsLess ? 1 : - 1;
        }
        return str1.compareTo(str2);
    }

    /**
     * <p>Compare two Strings lexicographically, ignoring case differences,
     * as per {@link String#compareToIgnoreCase(String)}, returning :</p>
     * <ul>
     *  <li>{@code int = 0}, if {@code str1} is equal to {@code str2} (or both {@code null})</li>
     *  <li>{@code int < 0}, if {@code str1} is less than {@code str2}</li>
     *  <li>{@code int > 0}, if {@code str1} is greater than {@code str2}</li>
     * </ul>
     *
     * <p>This is a {@code null} safe version of :</p>
     * <blockquote><pre>str1.compareToIgnoreCase(str2)</pre></blockquote>
     *
     * <p>{@code null} value is considered less than non-{@code null} value.
     * Two {@code null} references are considered equal.
     * Comparison is case insensitive.</p>
     *
     * <pre>
     * StringUtils.compareIgnoreCase(null, null)   = 0
     * StringUtils.compareIgnoreCase(null , "a")   &lt; 0
     * StringUtils.compareIgnoreCase("a", null)    &gt; 0
     * StringUtils.compareIgnoreCase("abc", "abc") = 0
     * StringUtils.compareIgnoreCase("abc", "ABC") = 0
     * StringUtils.compareIgnoreCase("a", "b")     &lt; 0
     * StringUtils.compareIgnoreCase("b", "a")     &gt; 0
     * StringUtils.compareIgnoreCase("a", "B")     &lt; 0
     * StringUtils.compareIgnoreCase("A", "b")     &lt; 0
     * StringUtils.compareIgnoreCase("ab", "ABC")  &lt; 0
     * </pre>
     *
     * @see #compareIgnoreCase(String, String, boolean)
     * @see String#compareToIgnoreCase(String)
     * @param str1  the String to compare from
     * @param str2  the String to compare to
     * @return &lt; 0, 0, &gt; 0, if {@code str1} is respectively less, equal ou greater than {@code str2},
     *          ignoring case differences.
     * @since 3.5
     */
    public static int compareIgnoreCase(final String str1, final String str2) {
        return compareIgnoreCase(str1, str2, true);
    }

    /**
     * <p>Compare two Strings lexicographically, ignoring case differences,
     * as per {@link String#compareToIgnoreCase(String)}, returning :</p>
     * <ul>
     *  <li>{@code int = 0}, if {@code str1} is equal to {@code str2} (or both {@code null})</li>
     *  <li>{@code int < 0}, if {@code str1} is less than {@code str2}</li>
     *  <li>{@code int > 0}, if {@code str1} is greater than {@code str2}</li>
     * </ul>
     *
     * <p>This is a {@code null} safe version of :</p>
     * <blockquote><pre>str1.compareToIgnoreCase(str2)</pre></blockquote>
     *
     * <p>{@code null} inputs are handled according to the {@code nullIsLess} parameter.
     * Two {@code null} references are considered equal.
     * Comparison is case insensitive.</p>
     *
     * <pre>
     * StringUtils.compareIgnoreCase(null, null, *)     = 0
     * StringUtils.compareIgnoreCase(null , "a", true)  &lt; 0
     * StringUtils.compareIgnoreCase(null , "a", false) &gt; 0
     * StringUtils.compareIgnoreCase("a", null, true)   &gt; 0
     * StringUtils.compareIgnoreCase("a", null, false)  &lt; 0
     * StringUtils.compareIgnoreCase("abc", "abc", *)   = 0
     * StringUtils.compareIgnoreCase("abc", "ABC", *)   = 0
     * StringUtils.compareIgnoreCase("a", "b", *)       &lt; 0
     * StringUtils.compareIgnoreCase("b", "a", *)       &gt; 0
     * StringUtils.compareIgnoreCase("a", "B", *)       &lt; 0
     * StringUtils.compareIgnoreCase("A", "b", *)       &lt; 0
     * StringUtils.compareIgnoreCase("ab", "abc", *)    &lt; 0
     * </pre>
     *
     * @see String#compareToIgnoreCase(String)
     * @param str1  the String to compare from
     * @param str2  the String to compare to
     * @param nullIsLess  whether consider {@code null} value less than non-{@code null} value
     * @return &lt; 0, 0, &gt; 0, if {@code str1} is respectively less, equal ou greater than {@code str2},
     *          ignoring case differences.
     * @since 3.5
     */
    public static int compareIgnoreCase(final String str1, final String str2, final boolean nullIsLess) {
        if (str1 == str2) {
            return 0;
        }
        if (str1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (str2 == null) {
            return nullIsLess ? 1 : - 1;
        }
        return str1.compareToIgnoreCase(str2);
    }

    /**
     * <p>Compares given <code>string</code> to a CharSequences vararg of <code>searchStrings</code>,
     * returning {@code true} if the <code>string</code> is equal to any of the <code>searchStrings</code>.</p>
     *
     * <pre>
     * StringUtils.equalsAny(null, (CharSequence[]) null) = false
     * StringUtils.equalsAny(null, null, null)    = true
     * StringUtils.equalsAny(null, "abc", "def")  = false
     * StringUtils.equalsAny("abc", null, "def")  = false
     * StringUtils.equalsAny("abc", "abc", "def") = true
     * StringUtils.equalsAny("abc", "ABC", "DEF") = false
     * </pre>
     *
     * @param string to compare, may be {@code null}.
     * @param searchStrings a vararg of strings, may be {@code null}.
     * @return {@code true} if the string is equal (case-sensitive) to any other element of <code>searchStrings</code>;
     * {@code false} if <code>searchStrings</code> is null or contains no matches.
     * @since 3.5
     */
    public static boolean equalsAny(final CharSequence string, final CharSequence... searchStrings) {
        if (!ArrayUtils.isEmpty(searchStrings)) {
            for (final CharSequence next : searchStrings) {
                if (equals(string, next)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * <p>Compares given <code>string</code> to a CharSequences vararg of <code>searchStrings</code>,
     * returning {@code true} if the <code>string</code> is equal to any of the <code>searchStrings</code>, ignoring case.</p>
     *
     * <pre>
     * StringUtils.equalsAnyIgnoreCase(null, (CharSequence[]) null) = false
     * StringUtils.equalsAnyIgnoreCase(null, null, null)    = true
     * StringUtils.equalsAnyIgnoreCase(null, "abc", "def")  = false
     * StringUtils.equalsAnyIgnoreCase("abc", null, "def")  = false
     * StringUtils.equalsAnyIgnoreCase("abc", "abc", "def") = true
     * StringUtils.equalsAnyIgnoreCase("abc", "ABC", "DEF") = true
     * </pre>
     *
     * @param string to compare, may be {@code null}.
     * @param searchStrings a vararg of strings, may be {@code null}.
     * @return {@code true} if the string is equal (case-insensitive) to any other element of <code>searchStrings</code>;
     * {@code false} if <code>searchStrings</code> is null or contains no matches.
     * @since 3.5
     */
    public static boolean equalsAnyIgnoreCase(final CharSequence string, final CharSequence...searchStrings) {
        if (!ArrayUtils.isEmpty(searchStrings)) {
            for (final CharSequence next : searchStrings) {
                if (equalsIgnoreCase(string, next)) {
                    return true;
                }
            }
        }
        return false;
    }

	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		for (int j = 0; j < substring.length(); j++) {
			int i = index + j;
			if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0, pos = 0, idx = 0;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	public static String replace(String inString, String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
			return inString;
		}
		StringBuffer sbuf = new StringBuffer();
		int pos = 0;
		int index = inString.indexOf(oldPattern);
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));
		return sbuf.toString();
	}

	public static String replaceAllWords(String original, String find, String replacement) {
		return replaceAllWords(original, find, replacement, REPLACE_WORD_DELIMITERS);
	}

	public static String replaceAllWords(String original, String find, String replacement, String delimiters) {
		StringBuilder result = new StringBuilder(original.length());
		StringTokenizer st = new StringTokenizer(original, delimiters, true);
		while (st.hasMoreTokens()) {
			String w = st.nextToken();
			if (w.equals(find)) {
				result.append(replacement);
			} else {
				result.append(w);
			}
		}
		return result.toString();
	}

	public static String replaceFirstWord(String original, String find, String replacement) {
		return replaceFirstWord(original, find, replacement, REPLACE_WORD_DELIMITERS);
	}

	public static String replaceFirstWord(String original, String find, String replacement, String delimiters) {
		StringBuilder result = new StringBuilder(original.length());
		StringTokenizer st = new StringTokenizer(original, delimiters, true);
		boolean replaced = false;
		while (st.hasMoreTokens()) {
			String w = st.nextToken();
			if (w.equals(find) && (!replaced)) {
				result.append(replacement);
				replaced = true;
			} else {
				result.append(w);
			}
		}
		return result.toString();
	}

	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	public static String deleteAny(String inString, String charsToDelete) {
		if (!hasLength(inString) || !hasLength(charsToDelete)) {
			return inString;
		}
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				out.append(c);
			}
		}
		return out.toString();
	}

	public static String quote(String str) {
		return (str != null ? "'" + str + "'" : null);
	}

	public static Object quoteIfString(Object obj) {
		return (obj instanceof String ? quote((String) obj) : obj);
	}

	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}

	public static String getFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(sepIndex + 1) : null);
	}

	public static String stripFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
	}

	public static String applyRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
				newPath += FOLDER_SEPARATOR;
			}
			return newPath + relativePath;
		} else {
			return relativePath;
		}
	}

	public static String cleanPath(String path) {
		if (path == null) {
			return null;
		}
		String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);

		int prefixIndex = pathToUse.indexOf(":");
		String prefix = "";
		if (prefixIndex != -1) {
			prefix = pathToUse.substring(0, prefixIndex + 1);
			pathToUse = pathToUse.substring(prefixIndex + 1);
		}
		if (pathToUse.startsWith(FOLDER_SEPARATOR)) {
			prefix = prefix + FOLDER_SEPARATOR;
			pathToUse = pathToUse.substring(1);
		}

		String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);
		List<String> pathElements = new LinkedList<String>();
		int tops = 0;

		for (int i = pathArray.length - 1; i >= 0; i--) {
			String element = pathArray[i];
			if (CURRENT_PATH.equals(element)) {
			} else if (TOP_PATH.equals(element)) {
				tops++;
			} else {
				if (tops > 0) {
					tops--;
				} else {
					pathElements.add(0, element);
				}
			}
		}
		for (int i = 0; i < tops; i++) {
			pathElements.add(0, TOP_PATH);
		}

		return prefix + collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
	}

	public static boolean pathEquals(String path1, String path2) {
		return cleanPath(path1).equals(cleanPath(path2));
	}

	public static Locale parseLocaleString(String localeString) {
		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
		String language = (parts.length > 0 ? parts[0] : "");
		String country = (parts.length > 1 ? parts[1] : "");
		String variant = "";
		if (parts.length >= 2) {
			int endIndexOfCountryCode = localeString.indexOf(country) + country.length();
			variant = trimLeadingWhitespace(localeString.substring(endIndexOfCountryCode));
			if (variant.startsWith("_")) {
				variant = trimLeadingCharacter(variant, '_');
			}
		}
		return (language.length() > 0 ? new Locale(language, country, variant) : null);
	}

	public static String toLanguageTag(Locale locale) {
		return locale.getLanguage() + (hasText(locale.getCountry()) ? "-" + locale.getCountry() : "");
	}

	public static String[] addStringToArray(String[] array, String str) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[] { str };
		}
		String[] newArr = new String[array.length + 1];
		System.arraycopy(array, 0, newArr, 0, array.length);
		newArr[array.length] = str;
		return newArr;
	}

	public static String[] concatenateStringArrays(String[] array1, String[] array2) {
		if (ObjectUtils.isEmpty(array1)) {
			return array2;
		}
		if (ObjectUtils.isEmpty(array2)) {
			return array1;
		}
		String[] newArr = new String[array1.length + array2.length];
		System.arraycopy(array1, 0, newArr, 0, array1.length);
		System.arraycopy(array2, 0, newArr, array1.length, array2.length);
		return newArr;
	}

	public static String[] mergeStringArrays(String[] array1, String[] array2) {
		if (ObjectUtils.isEmpty(array1)) {
			return array2;
		}
		if (ObjectUtils.isEmpty(array2)) {
			return array1;
		}
		List<String> result = new ArrayList<String>();
		result.addAll(Arrays.asList(array1));
		for (int i = 0; i < array2.length; i++) {
			String str = array2[i];
			if (!result.contains(str)) {
				result.add(str);
			}
		}
		return toStringArray(result);
	}

	public static String[] sortStringArray(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[0];
		}
		Arrays.sort(array);
		return array;
	}

	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}

	public static String[] toStringArray(Enumeration<?> enumeration) {
		if (enumeration == null) {
			return null;
		}
		List<?> list = Collections.list(enumeration);
		return list.toArray(new String[list.size()]);
	}

	public static String[] trimArrayElements(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return new String[0];
		}
		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			result[i] = (element != null ? element.trim() : null);
		}
		return result;
	}

	public static String[] removeDuplicateStrings(String[] array) {
		if (ObjectUtils.isEmpty(array)) {
			return array;
		}
		Set<String> set = new TreeSet<String>();
		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}
		return toStringArray(set);
	}

	public static String[] split(String toSplit, String delimiter) {
		if (!hasLength(toSplit) || !hasLength(delimiter)) {
			return null;
		}
		int offset = toSplit.indexOf(delimiter);
		if (offset < 0) {
			return null;
		}
		String beforeDelimiter = toSplit.substring(0, offset);
		String afterDelimiter = toSplit.substring(offset + delimiter.length());
		return new String[] { beforeDelimiter, afterDelimiter };
	}

	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
		return splitArrayElementsIntoProperties(array, delimiter, null);
	}

	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete) {

		if (ObjectUtils.isEmpty(array)) {
			return null;
		}
		Properties result = new Properties();
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			if (charsToDelete != null) {
				element = deleteAny(array[i], charsToDelete);
			}
			String[] splittedElement = split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
		}
		return result;
	}

	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens,
			boolean ignoreEmptyTokens) {

		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	public static String[] delimitedListToStringArray(String str, String delimiter) {
		return delimitedListToStringArray(str, delimiter, null);
	}

	public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] { str };
		}
		List<String> result = new ArrayList<String>();
		if ("".equals(delimiter)) {
			for (int i = 0; i < str.length(); i++) {
				result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
			}
		} else {
			int pos = 0;
			int delPos = 0;
			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
				result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
				pos = delPos + delimiter.length();
			}
			if (str.length() > 0 && pos <= str.length()) {
				result.add(deleteAny(str.substring(pos), charsToDelete));
			}
		}
		return toStringArray(result);
	}

	public static String[] commaDelimitedListToStringArray(String str) {
		return delimitedListToStringArray(str, ",");
	}

	public static Set<String> commaDelimitedListToSet(String str) {
		Set<String> set = new TreeSet<String>();
		String[] tokens = commaDelimitedListToStringArray(str);
		for (int i = 0; i < tokens.length; i++) {
			set.add(tokens[i]);
		}
		return set;
	}

	public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix,
			String suffix) {
		if (StringUtils.isEmpty(coll)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Iterator<?> it = coll.iterator();
		while (it.hasNext()) {
			sb.append(prefix).append(it.next()).append(suffix);
			if (it.hasNext()) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}

	public static boolean isEmpty(Collection<?> coll) {
		return (coll == null || coll.isEmpty());
	}

	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static String collectionToDelimitedString(Collection<?> coll, String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	public static String collectionToCommaDelimitedString(Collection<?> coll) {
		return collectionToDelimitedString(coll, ",");
	}

	public static String arrayToDelimitedString(Object[] arr, String delim) {
		if (ObjectUtils.isEmpty(arr)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	public static String arrayToCommaDelimitedString(Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	public static boolean isNotEmpty(String string) {
		return string != null && string.length() > 0;
	}

	public static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	public static boolean isEmptyOrSpace(String s) {
		return s == null || s.length() == 0 || s.trim().length() == 0;
	}

	public static boolean isInteger(String str) {
		return isInteger(str, false);
	}

	public static boolean isInteger(String str, boolean trimFirst) {
		if (str == null) {
			return false;
		}
		if (trimFirst) {
			str = str.trim();
		}
		if (str.length() == 0) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!(Character.isDigit(str.charAt(i)) || (i == 0 && (str.charAt(i) == '-') || str.charAt(i) == '+'))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumber(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		char[] chars = str.toCharArray();
		int sz = chars.length;
		boolean hasExp = false;
		boolean hasDecPoint = false;
		boolean allowSigns = false;
		boolean foundDigit = false;

		int start = (chars[0] == '-') ? 1 : 0;
		if (sz > start + 1) {
			if (chars[start] == '0' && chars[start + 1] == 'x') {
				int i = start + 2;
				if (i == sz) {
					return false;
				}

				for (; i < chars.length; i++) {
					if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f')
							&& (chars[i] < 'A' || chars[i] > 'F')) {
						return false;
					}
				}
				return true;
			}
		}
		sz--;
		int i = start;

		while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				foundDigit = true;
				allowSigns = false;

			} else if (chars[i] == '.') {
				if (hasDecPoint || hasExp) {

					return false;
				}
				hasDecPoint = true;
			} else if (chars[i] == 'e' || chars[i] == 'E') {

				if (hasExp) {

					return false;
				}
				if (!foundDigit) {
					return false;
				}
				hasExp = true;
				allowSigns = true;
			} else if (chars[i] == '+' || chars[i] == '-') {
				if (!allowSigns) {
					return false;
				}
				allowSigns = false;
				foundDigit = false;
			} else {
				return false;
			}
			i++;
		}
		if (i < chars.length) {
			if (chars[i] >= '0' && chars[i] <= '9') {

				return true;
			}
			if (chars[i] == 'e' || chars[i] == 'E') {

				return false;
			}
			if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
				return foundDigit;
			}
			if (chars[i] == 'l' || chars[i] == 'L') {

				return foundDigit && !hasExp;
			}

			return false;
		}

		return !allowSigns && foundDigit;
	}

	private static Pattern patternEmailAddr = null;

	public static boolean isEmailAddr(String s) {
		if (s == null) {
			return false;
		}
		if (patternEmailAddr == null) {
			patternEmailAddr = Pattern.compile("[\\w\\.%\\+-]+@[\\w\\.%\\+-]+\\.[A-Za-z]+");
		}
		return patternEmailAddr.matcher(s).matches();
	}

	public static String removeWordSpace(String word) {
		StringBuilder sb = new StringBuilder("");
		int len = word.length();
		for (int i = 0; i < len; i++) {
			Character c = word.charAt(i);
			if (!Character.isSpaceChar(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String join(String seperator, String[] strings) {
		int length = strings.length;
		if (length == 0)
			return "";
		StringBuffer buf = new StringBuffer(length * strings[0].length()).append(strings[0]);
		for (int i = 1; i < length; i++) {
			buf.append(seperator).append(strings[i]);
		}
		return buf.toString();
	}

	public static String handleQuotedString(String quotedString) {
		if (quotedString == null)
			return "null";
		String retVal = quotedString;
		if ((retVal.startsWith("'") && retVal.endsWith("'"))) {
			if (!retVal.equals("''")) {
				retVal = retVal.substring(retVal.indexOf("'") + 1, retVal.lastIndexOf("'"));
			} else {
				retVal = "";
			}
		}
		return retVal;
	}

	public static boolean containsDigitsOnly(String s) {
		for (int i = 0; s != null && i < s.length(); i++) {
			char c = s.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	public static boolean containsAsciiCharsOnly(String s) {
		for (int i = 0; s != null && i < s.length(); i++) {
			if (s.charAt(i) > 0x00ff) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	public static boolean endsWith(String str, String suffix) {
		return endsWith(str, suffix, false);
	}

	public static boolean endsWithAny(String string, String[] searchStrings) {
		if (isEmpty(string) || isEmpty(searchStrings)) {
			return false;
		}
		for (int i = 0; i < searchStrings.length; i++) {
			String searchString = searchStrings[i];
			if (endsWith(string, searchString)) {
				return true;
			}
		}
		return false;
	}

	private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
		if (str == null || suffix == null) {
			return (str == null && suffix == null);
		}
		if (suffix.length() > str.length()) {
			return false;
		}
		int strOffset = str.length() - suffix.length();
		return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
	}

	public static String leftTrim(String str, char[] charArray) {
		if ((str == null) || (charArray == null)) {
			return str;
		}
		Arrays.sort(charArray);

		int len = str.length();
		for (int i = 0; i < len; i++) {
			char c = str.charAt(i);
			if (Arrays.binarySearch(charArray, c) < 0)
				return str.substring(i);
		}
		return str;
	}

	public static String rightTrim(String str, char[] charArray) {
		if ((str == null) || (charArray == null)) {
			return str;
		}
		Arrays.sort(charArray);

		int len = str.length();
		for (int i = len - 1; i > 0; i--) {
			char c = str.charAt(i);
			if (Arrays.binarySearch(charArray, c) < 0)
				return str.substring(0, i);
		}
		return str;
	}

	public static String Trim(String str, char[] charArray) {
		if ((str == null) || (charArray == null)) {
			return str;
		}
		Arrays.sort(charArray);

		return rightTrim(leftTrim(str, charArray), charArray);
	}

	public static String[] toUpperCase(String[] strs) {
		if ((strs == null) || (strs.length == 0)) {
			return strs;
		}
		for (int i = 0; i < strs.length; i++) {
			strs[i] = strs[i].toUpperCase();
		}
		return strs;
	}

	public static String[] toLowerCase(String[] strs) {
		if ((strs == null) || (strs.length == 0)) {
			return strs;
		}
		for (int i = 0; i < strs.length; i++) {
			strs[i] = strs[i].toLowerCase();
		}
		return strs;
	}

	public static String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public static String replaceOnce(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, 1);
	}

	public static String replace(String text, String searchString, String replacement, int max) {
		if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1) {
			return text;
		}
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuffer buf = new StringBuffer(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static String repeat(String str, int repeat) {
		if (str == null) {
			return null;
		}
		if (repeat <= 0) {
			return EMPTY;
		}
		int inputLength = str.length();
		if (repeat == 1 || inputLength == 0) {
			return str;
		}
		if (inputLength == 1 && repeat <= PAD_LIMIT) {
			return padding(repeat, str.charAt(0));
		}

		int outputLength = inputLength * repeat;
		switch (inputLength) {
		case 1:
			char ch = str.charAt(0);
			char[] output1 = new char[outputLength];
			for (int i = repeat - 1; i >= 0; i--) {
				output1[i] = ch;
			}
			return new String(output1);
		case 2:
			char ch0 = str.charAt(0);
			char ch1 = str.charAt(1);
			char[] output2 = new char[outputLength];
			for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}
			return new String(output2);
		default:
			StringBuffer buf = new StringBuffer(outputLength);
			for (int i = 0; i < repeat; i++) {
				buf.append(str);
			}
			return buf.toString();
		}
	}

	private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
		if (repeat < 0) {
			throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
		}
		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = padChar;
		}
		return new String(buf);
	}

	public static String removeAllButAlphaNumericToFit(String s1, int maximumStringLength) {
		int s1Size = s1.length();
		if (s1Size <= maximumStringLength) {
			return s1;
		}
		StringBuffer buf = new StringBuffer();
		int numberOfCharsToBeRemoved = s1.length() - maximumStringLength;
		int s1Index = 0;
		while ((numberOfCharsToBeRemoved > 0) && (s1Index < s1Size)) {
			char currentChar = s1.charAt(s1Index);
			if (Character.isLetterOrDigit(currentChar)) {
				buf.append(currentChar);
			} else {
				numberOfCharsToBeRemoved--;
			}
			s1Index++;
		}

		while (s1Index < s1Size) {
			buf.append(s1.charAt(s1Index));
			s1Index++;
		}

		return buf.toString();
	}

	public static String removeCharacterToFit(String s1, char aChar, int maximumStringLength) {
		int s1Size = s1.length();
		if (s1Size <= maximumStringLength) {
			return s1;
		}

		StringBuffer buf = new StringBuffer();
		int numberOfCharsToBeRemoved = s1.length() - maximumStringLength;
		int s1Index = 0;
		while ((numberOfCharsToBeRemoved > 0) && (s1Index < s1Size)) {
			char currentChar = s1.charAt(s1Index);
			if (currentChar == aChar) {
				numberOfCharsToBeRemoved--;
			} else {
				buf.append(currentChar);
			}
			s1Index++;
		}

		while (s1Index < s1Size) {
			buf.append(s1.charAt(s1Index));
			s1Index++;
		}

		return buf.toString();
	}

	public static String shortenStringsByRemovingVowelsToFit(String s1, String s2, int maximumStringLength) {
		int size = s1.length() + s2.length();
		if (size <= maximumStringLength) {
			return s1 + s2;
		}

		int s1Size = s1.length();
		int s2Size = s2.length();
		StringBuffer buf1 = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();
		int numberOfCharsToBeRemoved = size - maximumStringLength;
		int s1Index = 0;
		int s2Index = 0;
		int modulo2 = 0;

		while ((numberOfCharsToBeRemoved > 0) && !((s1Index >= s1Size) && (s2Index >= s2Size))) {
			if ((modulo2 % 2) == 0) {
				if (s1Index < s1Size) {
					if (isVowel(s1.charAt(s1Index))) {
						numberOfCharsToBeRemoved--;
					} else {
						buf1.append(s1.charAt(s1Index));
					}
					s1Index++;
				}
			} else {
				if (s2Index < s2Size) {
					if (isVowel(s2.charAt(s2Index))) {
						numberOfCharsToBeRemoved--;
					} else {
						buf2.append(s2.charAt(s2Index));
					}
					s2Index++;
				}
			}
			modulo2++;
		}

		while (s1Index < s1Size) {
			buf1.append(s1.charAt(s1Index));
			s1Index++;
		}
		while (s2Index < s2Size) {
			buf2.append(s2.charAt(s2Index));
			s2Index++;
		}

		return buf1.toString() + buf2.toString();
	}

	public static boolean isVowel(char c) {
		return (c == 'A') || (c == 'a') || (c == 'e') || (c == 'E') || (c == 'i') || (c == 'I') || (c == 'o')
				|| (c == 'O') || (c == 'u') || (c == 'U');
	}

	public static String removeVowels(String s1) {
		StringBuffer buf = new StringBuffer();
		int s1Size = s1.length();
		int s1Index = 0;
		while (s1Index < s1Size) {
			char currentChar = s1.charAt(s1Index);
			if (!isVowel(currentChar)) {
				buf.append(currentChar);
			}
			s1Index++;
		}

		return buf.toString();
	}

	public static String truncate(String originalString, int size) {
		if (originalString.length() <= size) {
			return originalString;
		}
		String vowels = "AaEeIiOoUu";
		StringBuffer newStringBufferTmp = new StringBuffer(originalString.length());

		int counter = originalString.length() - size;
		for (int index = (originalString.length() - 1); index >= 0; index--) {
			if (vowels.indexOf(originalString.charAt(index)) == -1) {
				newStringBufferTmp.append(originalString.charAt(index));
			} else {
				counter--;
				if (counter == 0) {
					StringBuffer newStringBuffer = new StringBuffer(size);
					newStringBuffer.append(originalString.substring(0, index));
					newStringBuffer.append(newStringBufferTmp.reverse().toString());
					return newStringBuffer.toString();
				}
			}
		}
		return newStringBufferTmp.reverse().toString().substring(0, size);
	}

	public static String getNumericDigits(String value) {
		if (value == null) {
			return "";
		}
		String digits = "0123456789.,";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			if (digits.indexOf(value.substring(i, i + 1)) != -1) {
				sb.append(value.substring(i, i + 1));
			}
		}
		return sb.toString();
	}

	public static boolean contemString(String stringMaior, String subString) {
		String stringMenor = "";
		for (int i = 0; i < stringMaior.length(); i++) {
			stringMenor = stringMenor + stringMaior.substring(i, i + 1);
			if ((";").equals(stringMaior.substring(i, i + 1))) {
				if (stringMenor.equals(subString))
					return true;
				stringMenor = "";
			}
		}
		return false;
	}

	public static boolean subString(String stringMaior, String subString) {
		String stringMenor = "";
		String ch = "";
		for (int i = 0; i < stringMaior.length(); i++) {
			ch = stringMaior.substring(i, i + 1);
			if (!";".equals(ch)) {
				stringMenor = stringMenor + ch;
			} else {
				if (stringMenor.equals(subString)) {
					return true;
				}
				stringMenor = "";
			}
		}
		if (!"".equals(stringMenor) && stringMenor.equals(subString)) {
			return true;
		}
		return false;
	}

	public static String getNumbers(String value) {
		if (value == null) {
			return "";
		}
		String digits = "0123456789";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			if (digits.indexOf(value.substring(i, i + 1)) != -1)
				sb.append(value.substring(i, i + 1));
		}
		return sb.toString();
	}

	public static String replaceFirst(String input, String search, String replacement) {
		int pos = input.indexOf(search);
		if (pos != -1)
			input = input.substring(0, pos) + replacement + input.substring(pos + search.length());
		return input;
	}

	public static String replaceLast(String input, String search, String replacement) {
		int pos = input.indexOf(search);
		if (pos != -1) {
			int lastPos = pos;
			while (true) {
				pos = input.indexOf(search, lastPos + 1);
				if (pos == -1)
					break;
				else
					lastPos = pos;

			}
			input = input.substring(0, lastPos) + replacement + input.substring(lastPos + search.length());
		}
		return input;
	}

	public static String getRightString(String item, char letter) {
		int i = item.lastIndexOf(letter);
		if (i == -1) {
			return "";
		}
		return item.substring(i + 1, item.length());
	}

	public static String getLeftString(String item, char letter) {
		int i = item.indexOf(letter);
		return item.substring(0, i);
	}

	public static String getFirstPartIsEqual(String text1, String text2) {
		String result = "";

		for (int i = 0; i < text1.toCharArray().length; i++) {
			if (text1.toCharArray()[i] == text2.toCharArray()[i]) {
				result += text1.toCharArray()[i];
			} else {
				break;
			}
		}

		return result;
	}

	public static String getPartIsDifferent(String text1, String text2) {
		return text2.substring(getFirstPartIsEqual(text1, text2).length());
	}

	public static String padZero(final String s, final int chars) {
		final StringBuffer result = new StringBuffer();
		final int len = s.length();
		for (int i = len; i < chars; i++) {
			result.append('0');
		}
		result.append(s);
		return result.toString();
	}

	public static String padZeroEnd(final String s, final int chars) {
		final StringBuffer result = new StringBuffer();
		final int len = s.length();
		result.append(s);
		for (int i = len; i < chars; i++) {
			result.append('0');
		}
		return result.toString();
	}

	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(cs.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(CharSequence cs) {
		return !StringUtils.isBlank(cs);
	}

	public static String deleteWhitespace(String str) {
		if (isEmpty(str)) {
			return str;
		}
		int sz = str.length();
		char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}
		return new String(chs, 0, count);
	}

	public static boolean containsNone(final CharSequence cs, final char... searchChars) {
		if (cs == null || searchChars == null) {
			return true;
		}
		final int csLen = cs.length();
		final int csLast = csLen - 1;
		final int searchLen = searchChars.length;
		final int searchLast = searchLen - 1;
		for (int i = 0; i < csLen; i++) {
			final char ch = cs.charAt(i);
			for (int j = 0; j < searchLen; j++) {
				if (searchChars[j] == ch) {
					if (Character.isHighSurrogate(ch)) {
						if (j == searchLast) {
							return false;
						}
						if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean containsAny(final CharSequence cs, final char... searchChars) {
		if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
			return false;
		}
		final int csLength = cs.length();
		final int searchLength = searchChars.length;
		final int csLast = csLength - 1;
		final int searchLast = searchLength - 1;
		for (int i = 0; i < csLength; i++) {
			final char ch = cs.charAt(i);
			for (int j = 0; j < searchLength; j++) {
				if (searchChars[j] == ch) {
					if (Character.isHighSurrogate(ch)) {
						if (j == searchLast) {
							return true;
						}
						if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
							return true;
						}
					} else {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Joining
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No separator is added to the joined String. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null)            = null
	 * StringUtils.join([])              = ""
	 * StringUtils.join([null])          = ""
	 * StringUtils.join(["a", "b", "c"]) = "abc"
	 * StringUtils.join([null, "", "a"]) = "a"
	 * </pre>
	 *
	 * @param <T>
	 *            the specific type of values to join together
	 * @param elements
	 *            the values to join together, may be null
	 * @return the joined String, {@code null} if null array input
	 * @since 2.0
	 * @since 3.0 Changed signature to use varargs
	 */
	public static <T> String join(T... elements) {
		return join(elements, null);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 *
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, {@code null} if null array input
	 * @since 2.0
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}

		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)               = null
	 * StringUtils.join([], *)                 = ""
	 * StringUtils.join([null], *)             = ""
	 * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
	 * StringUtils.join(["a", "b", "c"], null) = "abc"
	 * StringUtils.join([null, "", "a"], ';')  = ";;a"
	 * </pre>
	 *
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use
	 * @param startIndex
	 *            the first index to start joining from. It is an error to pass
	 *            in an end index past the end of the array
	 * @param endIndex
	 *            the index to stop joining from (exclusive). It is an error to
	 *            pass in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 * @since 2.0
	 */
	public static String join(Object[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = (endIndex - startIndex);
		if (noOfItems <= 0) {
			return EMPTY;
		}

		StringBuilder buf = new StringBuilder(noOfItems * 16);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator
	 * is the same as an empty String (""). Null objects or empty strings within
	 * the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)                = null
	 * StringUtils.join([], *)                  = ""
	 * StringUtils.join([null], *)              = ""
	 * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], null)  = "abc"
	 * StringUtils.join(["a", "b", "c"], "")    = "abc"
	 * StringUtils.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 *
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null array input
	 */
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator
	 * is the same as an empty String (""). Null objects or empty strings within
	 * the array are represented by empty strings.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.join(null, *)                = null
	 * StringUtils.join([], *)                  = ""
	 * StringUtils.join([null], *)              = ""
	 * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], null)  = "abc"
	 * StringUtils.join(["a", "b", "c"], "")    = "abc"
	 * StringUtils.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 *
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @param startIndex
	 *            the first index to start joining from. It is an error to pass
	 *            in an end index past the end of the array
	 * @param endIndex
	 *            the index to stop joining from (exclusive). It is an error to
	 *            pass in an end index past the end of the array
	 * @return the joined String, {@code null} if null array input
	 */
	public static String join(Object[] array, String separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}

		// endIndex - startIndex > 0: Len = NofStrings *(len(firstString) +
		// len(separator))
		// (Assuming that all Strings are roughly equally long)
		int noOfItems = (endIndex - startIndex);
		if (noOfItems <= 0) {
			return EMPTY;
		}

		StringBuilder buf = new StringBuilder(noOfItems * 16);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterator} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings.
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],char)}.
	 * </p>
	 *
	 * @param iterator
	 *            the {@code Iterator} of values to join together, may be null
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.0
	 */
	public static String join(Iterator<?> iterator, char separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			return first == null ? "" : first.toString();
		}

		// two or more elements
		StringBuilder buf = new StringBuilder(256); // Java default is 16,
													// probably too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			buf.append(separator);
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}

		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterator} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator
	 * is the same as an empty String ("").
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 *
	 * @param iterator
	 *            the {@code Iterator} of values to join together, may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null iterator input
	 */
	public static String join(Iterator<?> iterator, String separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			return first == null ? "" : first.toString();
		}

		// two or more elements
		StringBuilder buf = new StringBuilder(256); // Java default is 16,
													// probably too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterable} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings.
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],char)}.
	 * </p>
	 *
	 * @param iterable
	 *            the {@code Iterable} providing the values to join together,
	 *            may be null
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.3
	 */
	public static String join(Iterable<?> iterable, char separator) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), separator);
	}

	/**
	 * <p>
	 * Joins the elements of the provided {@code Iterable} into a single String
	 * containing the provided elements.
	 * </p>
	 *
	 * <p>
	 * No delimiter is added before or after the list. A {@code null} separator
	 * is the same as an empty String ("").
	 * </p>
	 *
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 *
	 * @param iterable
	 *            the {@code Iterable} providing the values to join together,
	 *            may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, {@code null} if null iterator input
	 * @since 2.3
	 */
	public static String join(Iterable<?> iterable, String separator) {
		if (iterable == null) {
			return null;
		}
		return join(iterable.iterator(), separator);
	}

	/**
	 * Elimina simbolos como:
	 * 
	 * <pre>
	 * >
	 * <,;.:!*&%+-_<>[]\/
	 * </pre>
	 * 
	 * @param str
	 *            String com os símbolos a serem removidos.
	 * @return String sem símbolos.
	 * @since 0.2
	 */
	public static String eliminateSymbols(final String str) {

		String modifiedStr = str;

		if (isNotBlank(modifiedStr)) {

			modifiedStr = replace(modifiedStr, "-", EMPTY);
			modifiedStr = replace(modifiedStr, "_", EMPTY);
			modifiedStr = replace(modifiedStr, "=", EMPTY);
			modifiedStr = replace(modifiedStr, "+", EMPTY);
			modifiedStr = replace(modifiedStr, "%", EMPTY);
			modifiedStr = replace(modifiedStr, "*", EMPTY);
			modifiedStr = replace(modifiedStr, "@", EMPTY);
			modifiedStr = replace(modifiedStr, "#", EMPTY);
			modifiedStr = replace(modifiedStr, "&", EMPTY);
			modifiedStr = replace(modifiedStr, ":", EMPTY);
			modifiedStr = replace(modifiedStr, ".", EMPTY);
			modifiedStr = replace(modifiedStr, ";", EMPTY);
			modifiedStr = replace(modifiedStr, ",", EMPTY);
			modifiedStr = replace(modifiedStr, "!", EMPTY);
			modifiedStr = replace(modifiedStr, "?", EMPTY);
			modifiedStr = replace(modifiedStr, "(", EMPTY);
			modifiedStr = replace(modifiedStr, ")", EMPTY);
			modifiedStr = replace(modifiedStr, "{", EMPTY);
			modifiedStr = replace(modifiedStr, "}", EMPTY);
			modifiedStr = replace(modifiedStr, "[", EMPTY);
			modifiedStr = replace(modifiedStr, "]", EMPTY);
			modifiedStr = replace(modifiedStr, "/", EMPTY);
			modifiedStr = replace(modifiedStr, "\\", EMPTY);
			modifiedStr = replace(modifiedStr, ">", EMPTY);
			modifiedStr = replace(modifiedStr, "<", EMPTY);
			modifiedStr = replace(modifiedStr, "\"", EMPTY);
			modifiedStr = replace(modifiedStr, "'", EMPTY);
			modifiedStr = replace(modifiedStr, "`", EMPTY);
		}

		return modifiedStr;
	}

	/**
	 * Remove a acentuação do texto, que inclui os acentos:
	 * <ul>
	 * <li>Agudo. ex.: á</li>
	 * <li>Grave. ex.: à</li>
	 * <li>Til. ex.: ã</li>
	 * <li>Trema. ex.: ä</li>
	 * <li>Circunflexo. ex.: â</li>
	 * </ul>
	 * e o Cedilha (ç).
	 * <p>
	 * Os acentos são removidos tanto para letras minúsculas como para letras
	 * maiúsculas.
	 * </p>
	 * 
	 * @param value
	 *            String com os caracteres a serem removidos.
	 * @return String sem acentuação.
	 * @since 0.2
	 */
	public static String eliminateAccent(final String value) {

		String modifiedValue = value;

		// Para ç e Ç
		modifiedValue = replaceAll(modifiedValue, "\u00E7", "c");
		modifiedValue = replaceAll(modifiedValue, "\u00C7", "C");

		// Para à, á, â, ã e ä
		modifiedValue = replaceAll(modifiedValue, "\u00E0", "a");
		modifiedValue = replaceAll(modifiedValue, "\u00E1", "a");
		modifiedValue = replaceAll(modifiedValue, "\u00E2", "a");
		modifiedValue = replaceAll(modifiedValue, "\u00E3", "a");
		modifiedValue = replaceAll(modifiedValue, "\u00E4", "a");

		// Para è, é, ê e ë
		modifiedValue = replaceAll(modifiedValue, "\u00E8", "e");
		modifiedValue = replaceAll(modifiedValue, "\u00E9", "e");
		modifiedValue = replaceAll(modifiedValue, "\u00EA", "e");
		modifiedValue = replaceAll(modifiedValue, "\u00EB", "e");

		// Para ì, í, î e ï
		modifiedValue = replaceAll(modifiedValue, "\u00EC", "i");
		modifiedValue = replaceAll(modifiedValue, "\u00ED", "i");
		modifiedValue = replaceAll(modifiedValue, "\u00EE", "i");
		modifiedValue = replaceAll(modifiedValue, "\u00EF", "i");

		// Para ò, ó, ô, õ e ö
		modifiedValue = replaceAll(modifiedValue, "\u00F2", "o");
		modifiedValue = replaceAll(modifiedValue, "\u00F3", "o");
		modifiedValue = replaceAll(modifiedValue, "\u00F4", "o");
		modifiedValue = replaceAll(modifiedValue, "\u00F5", "o");
		modifiedValue = replaceAll(modifiedValue, "\u00F6", "o");

		// Para ù, ú, û e ü
		modifiedValue = replaceAll(modifiedValue, "\u00F9", "u");
		modifiedValue = replaceAll(modifiedValue, "\u00FA", "u");
		modifiedValue = replaceAll(modifiedValue, "\u00FB", "u");
		modifiedValue = replaceAll(modifiedValue, "\u00FC", "u");

		// Para À, Á, Â, Ã e Ä
		modifiedValue = replaceAll(modifiedValue, "\u00C0", "A");
		modifiedValue = replaceAll(modifiedValue, "\u00C1", "A");
		modifiedValue = replaceAll(modifiedValue, "\u00C2", "A");
		modifiedValue = replaceAll(modifiedValue, "\u00C3", "A");
		modifiedValue = replaceAll(modifiedValue, "\u00C4", "A");

		// Para È, É, Ê e Ë
		modifiedValue = replaceAll(modifiedValue, "\u00C8", "E");
		modifiedValue = replaceAll(modifiedValue, "\u00C9", "E");
		modifiedValue = replaceAll(modifiedValue, "\u00CA", "E");
		modifiedValue = replaceAll(modifiedValue, "\u00CB", "E");

		// Para Ì, Í, Î e Ï
		modifiedValue = replaceAll(modifiedValue, "\u00CC", "I");
		modifiedValue = replaceAll(modifiedValue, "\u00CD", "I");
		modifiedValue = replaceAll(modifiedValue, "\u00CE", "I");
		modifiedValue = replaceAll(modifiedValue, "\u00CF", "I");

		// Para Ò, Ó, Ô, Õ e Ö
		modifiedValue = replaceAll(modifiedValue, "\u00D2", "O");
		modifiedValue = replaceAll(modifiedValue, "\u00D3", "O");
		modifiedValue = replaceAll(modifiedValue, "\u00D4", "O");
		modifiedValue = replaceAll(modifiedValue, "\u00D5", "O");
		modifiedValue = replaceAll(modifiedValue, "\u00D6", "O");

		// Para Ù, Ú, Û e Ü
		modifiedValue = replaceAll(modifiedValue, "\u00D9", "U");
		modifiedValue = replaceAll(modifiedValue, "\u00DA", "U");
		modifiedValue = replaceAll(modifiedValue, "\u00DB", "U");
		modifiedValue = replaceAll(modifiedValue, "\u00DC", "U");

		return modifiedValue;
	}
	
	
	// Character Tests
    //-----------------------------------------------------------------------
    /**
     * <p>Checks if the CharSequence contains only Unicode letters.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.isAlpha(null)   = false
     * StringUtils.isAlpha("")     = false
     * StringUtils.isAlpha("  ")   = false
     * StringUtils.isAlpha("abc")  = true
     * StringUtils.isAlpha("ab2c") = false
     * StringUtils.isAlpha("ab-c") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains letters, and is non-null
     * @since 3.0 Changed signature from isAlpha(String) to isAlpha(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    public static boolean isAlpha(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetter(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
	

    /**
     * <p>Checks if the CharSequence contains only Unicode letters and
     * space (' ').</p>
     *
     * <p>{@code null} will return {@code false}
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isAlphaSpace(null)   = false
     * StringUtils.isAlphaSpace("")     = true
     * StringUtils.isAlphaSpace("  ")   = true
     * StringUtils.isAlphaSpace("abc")  = true
     * StringUtils.isAlphaSpace("ab c") = true
     * StringUtils.isAlphaSpace("ab2c") = false
     * StringUtils.isAlphaSpace("ab-c") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains letters and space,
     *  and is non-null
     * @since 3.0 Changed signature from isAlphaSpace(String) to isAlphaSpace(CharSequence)
     */
    public static boolean isAlphaSpace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetter(cs.charAt(i)) && cs.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the CharSequence contains only Unicode letters or digits.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.isAlphanumeric(null)   = false
     * StringUtils.isAlphanumeric("")     = false
     * StringUtils.isAlphanumeric("  ")   = false
     * StringUtils.isAlphanumeric("abc")  = true
     * StringUtils.isAlphanumeric("ab c") = false
     * StringUtils.isAlphanumeric("ab2c") = true
     * StringUtils.isAlphanumeric("ab-c") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains letters or digits,
     *  and is non-null
     * @since 3.0 Changed signature from isAlphanumeric(String) to isAlphanumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    public static boolean isAlphanumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetterOrDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * <p>Checks if the CharSequence contains only Unicode letters, digits
     * or space ({@code ' '}).</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isAlphanumericSpace(null)   = false
     * StringUtils.isAlphanumericSpace("")     = true
     * StringUtils.isAlphanumericSpace("  ")   = true
     * StringUtils.isAlphanumericSpace("abc")  = true
     * StringUtils.isAlphanumericSpace("ab c") = true
     * StringUtils.isAlphanumericSpace("ab2c") = true
     * StringUtils.isAlphanumericSpace("ab-c") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains letters, digits or space,
     *  and is non-null
     * @since 3.0 Changed signature from isAlphanumericSpace(String) to isAlphanumericSpace(CharSequence)
     */
    public static boolean isAlphanumericSpace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetterOrDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the CharSequence contains only ASCII printable characters.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isAsciiPrintable(null)     = false
     * StringUtils.isAsciiPrintable("")       = true
     * StringUtils.isAsciiPrintable(" ")      = true
     * StringUtils.isAsciiPrintable("Ceki")   = true
     * StringUtils.isAsciiPrintable("ab2c")   = true
     * StringUtils.isAsciiPrintable("!ab-c~") = true
     * StringUtils.isAsciiPrintable("\u0020") = true
     * StringUtils.isAsciiPrintable("\u0021") = true
     * StringUtils.isAsciiPrintable("\u007e") = true
     * StringUtils.isAsciiPrintable("\u007f") = false
     * StringUtils.isAsciiPrintable("Ceki G\u00fclc\u00fc") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if every character is in the range
     *  32 thru 126
     * @since 2.1
     * @since 3.0 Changed signature from isAsciiPrintable(String) to isAsciiPrintable(CharSequence)
     */
    public static boolean isAsciiPrintable(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!isAsciiPrintable(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isAsciiPrintable(final char ch) {
        return ch >= 32 && ch < 127;
    }

    /**
     * <p>Checks if the CharSequence contains only Unicode digits.
     * A decimal point is not a Unicode digit and returns false.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <p>Note that the method does not allow for a leading sign, either positive or negative.
     * Also, if a String passes the numeric test, it may still generate a NumberFormatException
     * when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range
     * for int or long respectively.</p>
     *
     * <pre>
     * StringUtils.isNumeric(null)   = false
     * StringUtils.isNumeric("")     = false
     * StringUtils.isNumeric("  ")   = false
     * StringUtils.isNumeric("123")  = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("12 3") = false
     * StringUtils.isNumeric("ab2c") = false
     * StringUtils.isNumeric("12-3") = false
     * StringUtils.isNumeric("12.3") = false
     * StringUtils.isNumeric("-123") = false
     * StringUtils.isNumeric("+123") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains digits, and is non-null
     * @since 3.0 Changed signature from isNumeric(String) to isNumeric(CharSequence)
     * @since 3.0 Changed "" to return false and not true
     */
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the CharSequence contains only Unicode digits or space
     * ({@code ' '}).
     * A decimal point is not a Unicode digit and returns false.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isNumericSpace(null)   = false
     * StringUtils.isNumericSpace("")     = true
     * StringUtils.isNumericSpace("  ")   = true
     * StringUtils.isNumericSpace("123")  = true
     * StringUtils.isNumericSpace("12 3") = true
     * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
     * StringUtils.isNumeric("\u0967\u0968 \u0969")  = true
     * StringUtils.isNumericSpace("ab2c") = false
     * StringUtils.isNumericSpace("12-3") = false
     * StringUtils.isNumericSpace("12.3") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains digits or space,
     *  and is non-null
     * @since 3.0 Changed signature from isNumericSpace(String) to isNumericSpace(CharSequence)
     */
    public static boolean isNumericSpace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a String {@code str} contains Unicode digits,
     * if yes then concatenate all the digits in {@code str} and return it as a String.</p>
     *
     * <p>An empty ("") String will be returned if no digits found in {@code str}.</p>
     *
     * <pre>
     * StringUtils.getDigits(null)  = null
     * StringUtils.getDigits("")    = ""
     * StringUtils.getDigits("abc") = ""
     * StringUtils.getDigits("1000$") = "1000"
     * StringUtils.getDigits("1123~45") = "112345"
     * StringUtils.getDigits("(541) 754-3010") = "5417543010"
     * StringUtils.getDigits("\u0967\u0968\u0969") = "\u0967\u0968\u0969"
     * </pre>
     *
     * @param str the String to extract digits from, may be null
     * @return String with only digits,
     *           or an empty ("") String if no digits found,
     *           or {@code null} String if {@code str} is null
     * @since 3.6
     */
    public static String getDigits(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        final int sz = str.length();
        final StringBuilder strDigits = new StringBuilder(sz);
        for (int i = 0; i < sz; i++) {
            final char tempChar = str.charAt(i);
            if (Character.isDigit(tempChar)) {
                strDigits.append(tempChar);
            }
        }
        return strDigits.toString();
    }

    /**
     * <p>Checks if the CharSequence contains only whitespace.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isWhitespace(null)   = false
     * StringUtils.isWhitespace("")     = true
     * StringUtils.isWhitespace("  ")   = true
     * StringUtils.isWhitespace("abc")  = false
     * StringUtils.isWhitespace("ab2c") = false
     * StringUtils.isWhitespace("ab-c") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains whitespace, and is non-null
     * @since 2.0
     * @since 3.0 Changed signature from isWhitespace(String) to isWhitespace(CharSequence)
     */
    public static boolean isWhitespace(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the CharSequence contains only lowercase characters.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.isAllLowerCase(null)   = false
     * StringUtils.isAllLowerCase("")     = false
     * StringUtils.isAllLowerCase("  ")   = false
     * StringUtils.isAllLowerCase("abc")  = true
     * StringUtils.isAllLowerCase("abC")  = false
     * StringUtils.isAllLowerCase("ab c") = false
     * StringUtils.isAllLowerCase("ab1c") = false
     * StringUtils.isAllLowerCase("ab/c") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if only contains lowercase characters, and is non-null
     * @since 2.5
     * @since 3.0 Changed signature from isAllLowerCase(String) to isAllLowerCase(CharSequence)
     */
    public static boolean isAllLowerCase(final CharSequence cs) {
        if (cs == null || isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLowerCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the CharSequence contains only uppercase characters.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty String (length()=0) will return {@code false}.</p>
     *
     * <pre>
     * StringUtils.isAllUpperCase(null)   = false
     * StringUtils.isAllUpperCase("")     = false
     * StringUtils.isAllUpperCase("  ")   = false
     * StringUtils.isAllUpperCase("ABC")  = true
     * StringUtils.isAllUpperCase("aBC")  = false
     * StringUtils.isAllUpperCase("A C")  = false
     * StringUtils.isAllUpperCase("A1C")  = false
     * StringUtils.isAllUpperCase("A/C")  = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains uppercase characters, and is non-null
     * @since 2.5
     * @since 3.0 Changed signature from isAllUpperCase(String) to isAllUpperCase(CharSequence)
     */
    public static boolean isAllUpperCase(final CharSequence cs) {
        if (cs == null || isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isUpperCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the CharSequence contains mixed casing of both uppercase and lowercase characters.</p>
     *
     * <p>{@code null} will return {@code false}. An empty CharSequence ({@code length()=0}) will return
     * {@code false}.</p>
     *
     * <pre>
     * StringUtils.isMixedCase(null)    = false
     * StringUtils.isMixedCase("")      = false
     * StringUtils.isMixedCase("ABC")   = false
     * StringUtils.isMixedCase("abc")   = false
     * StringUtils.isMixedCase("aBc")   = true
     * StringUtils.isMixedCase("A c")   = true
     * StringUtils.isMixedCase("A1c")   = true
     * StringUtils.isMixedCase("a/C")   = true
     * StringUtils.isMixedCase("aC\t")  = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence contains both uppercase and lowercase characters
     * @since 3.5
     */
    public static boolean isMixedCase(final CharSequence cs) {
        if (isEmpty(cs) || cs.length() == 1) {
            return false;
        }
        boolean containsUppercase = false;
        boolean containsLowercase = false;
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (containsUppercase && containsLowercase) {
                return true;
            } else if (Character.isUpperCase(cs.charAt(i))) {
                containsUppercase = true;
            } else if (Character.isLowerCase(cs.charAt(i))) {
                containsLowercase = true;
            }
        }
        return containsUppercase && containsLowercase;
    }


	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return padding(pads, padChar).concat(str);
	}

	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(padding(pads, padChar));
	}

	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	public static void checkNotNumeric(String value, String message) {
		checkNotNumeric(value, message, message);
	}

	public static void checkNotNumeric(String value) {
		checkNotNumeric(value, "String nula!", format("Valor inválido. String [\"%s\"] não numérica!", value));
	}

	private static void checkNotNumeric(String value, String messageNullPointer, String messageIllegalArgument) {

		Assert.notNull(value, messageNullPointer);
		if (!isNumber(value)) {
			throw new IllegalArgumentException(messageIllegalArgument);
		}
	}

	public static String removeStartWithZeros(final String str) {

		String withoutZeros = EMPTY;
		final String zero = "0";

		if (StringUtils.isNotEmpty(str)) {

			if (str.startsWith(zero)) {

				withoutZeros = removeStart(str, zero);

				while (withoutZeros.startsWith(zero)) {
					withoutZeros = removeStart(withoutZeros, zero);
				}

				if (withoutZeros.trim().length() == 0) {
					withoutZeros = zero;
				}

			} else {
				withoutZeros = str;
			}
		}

		return withoutZeros;
	}

	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	public static String strip(String str) {
		return strip(str, null);
	}

	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		}
		str = stripStart(str, stripChars);
		return stripEnd(str, stripChars);
	}

	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}
		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}

	public static boolean containsAny(String str, String searchChars) {
		if (searchChars == null) {
			return false;
		}
		return containsAny(str, searchChars.toCharArray());
	}

	public static String removeCRLF(String text) {
		if (text == null)
			return text;
		return text.replaceAll("(\\r|\\n)", "");
	}

	public static String difference(String str1, String str2) {
		if (str1 == null) {
			return str2;
		}
		if (str2 == null) {
			return str1;
		}
		int at = indexOfDifference(str1, str2);
		if (at == -1) {
			return EMPTY;
		}
		return str2.substring(at);
	}

	public static int indexOfDifference(String str1, String str2) {
		if (str1 == str2) {
			return -1;
		}
		if (str1 == null || str2 == null) {
			return 0;
		}
		int i;
		for (i = 0; i < str1.length() && i < str2.length(); ++i) {
			if (str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}
		if (i < str2.length() || i < str1.length()) {
			return i;
		}
		return -1;
	}
} 