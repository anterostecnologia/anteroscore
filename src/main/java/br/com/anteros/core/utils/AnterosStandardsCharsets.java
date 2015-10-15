package br.com.anteros.core.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class AnterosStandardsCharsets {

	private AnterosStandardsCharsets() {
		throw new AssertionError("No br.com.anteros.core.utils.AnterosStandardsCharsets instances for you!");
	}

	/**
	 * Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the
	 * Unicode character set
	 */
	public static final Charset US_ASCII = Charset.forName("US-ASCII");
	/**
	 * ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
	 */
	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
	/**
	 * Eight-bit UCS Transformation Format
	 */
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	/**
	 * Sixteen-bit UCS Transformation Format, big-endian byte order
	 */
	public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
	/**
	 * Sixteen-bit UCS Transformation Format, little-endian byte order
	 */
	public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
	/**
	 * Sixteen-bit UCS Transformation Format, byte order identified by an
	 * optional byte-order mark
	 */
	public static final Charset UTF_16 = Charset.forName("UTF-16");

	public static Charset getCharsetByName(String charset) {
		return Charset.forName(charset);
	}

	public static List<String> getStandardsCharsets() {
		List<String> result = new ArrayList<String>();

		result.add(US_ASCII.name());
		result.add(ISO_8859_1.name());
		result.add(UTF_8.name());
		result.add(UTF_16BE.name());
		result.add(UTF_16LE.name());
		result.add(UTF_16.name());

		return result;
	}

}
