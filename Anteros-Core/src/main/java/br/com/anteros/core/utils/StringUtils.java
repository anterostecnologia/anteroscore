/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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

public abstract class StringUtils {

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

	public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

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

	public static String collectionToDelimitedString(Collection<String> coll, String delim, String prefix, String suffix) {
		if (StringUtils.isEmpty(coll)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = coll.iterator();
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

	public static String collectionToDelimitedString(Collection<String> coll, String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	public static String collectionToCommaDelimitedString(Collection<String> coll) {
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
					if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
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
		return (c == 'A') || (c == 'a') || (c == 'e') || (c == 'E') || (c == 'i') || (c == 'I') || (c == 'o') || (c == 'O') || (c == 'u')
				|| (c == 'U');
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
	
	
	
	
}
