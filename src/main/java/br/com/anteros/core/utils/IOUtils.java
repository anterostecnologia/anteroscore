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
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class IOUtils {

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final int EOF = -1;

	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	public static int copy(final InputStream input, final OutputStream output) throws IOException {
		final long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	public static long copyLarge(final InputStream input, final OutputStream output) throws IOException {
		return copy(input, output, DEFAULT_BUFFER_SIZE);
	}

	public static long copy(final InputStream input, final OutputStream output, final int bufferSize)
			throws IOException {
		return copyLarge(input, output, new byte[bufferSize]);
	}

	public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
			throws IOException {
		long count = 0;
		int n = 0;
		while (EOF != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static byte[] toByteArray(final InputStream input) throws IOException {
		final ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	public static String readFileToString(File file, String encoding) throws IOException {
		return readFileToString(file, (encoding == null ? Charset.defaultCharset() : Charset.forName(encoding)));
	}

	public static String readFileToString(File file, Charset encoding) throws IOException {
		InputStream in = null;
		try {
			in = openInputStream(file);
			return IOUtils.toString(in, (encoding == null ? Charset.defaultCharset() : encoding));
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}
		return new FileInputStream(file);
	}

	public static String toString(InputStream input, String encoding) throws IOException {
		return toString(input, (encoding == null ? Charset.defaultCharset() : Charset.forName(encoding)));
	}

	public static String toString(InputStream input) throws IOException {
		return toString(input, Charset.defaultCharset());
	}

	public static String toString(InputStream input, Charset encoding) throws IOException {
		StringBuilderWriter sw = new StringBuilderWriter();
		copy(input, sw, encoding);
		return sw.toString();
	}

	public static void copy(InputStream input, Writer output, Charset encoding) throws IOException {
		InputStreamReader in = new InputStreamReader(input, (encoding == null ? Charset.defaultCharset() : encoding));
		copy(in, output);
	}

	public static int copy(Reader input, Writer output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	public static long copyLarge(Reader input, Writer output) throws IOException {
		return copyLarge(input, output, new char[DEFAULT_BUFFER_SIZE]);
	}

	public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
		long count = 0;
		int n = 0;
		while (EOF != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	public static void closeQuietly(Reader input) {
		closeQuietly((Closeable) input);
	}

	public static void closeQuietly(Writer output) {
		closeQuietly((Closeable) output);
	}

	public static void closeQuietly(OutputStream output) {
		closeQuietly((Closeable) output);
	}

	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ioe) {
		}
	}

	public static void closeQuietly(Socket sock) {
		if (sock != null) {
			try {
				sock.close();
			} catch (IOException ioe) {
			}
		}
	}

	public static void closeQuietly(Selector selector) {
		if (selector != null) {
			try {
				selector.close();
			} catch (IOException ioe) {
			}
		}
	}

	public static void closeQuietly(ServerSocket sock) {
		if (sock != null) {
			try {
				sock.close();
			} catch (IOException ioe) {
			}
		}
	}

	public static void writeLines(Collection<String> lines, String lineEnding, OutputStream output, String encoding)
			throws IOException {
		if (encoding == null) {
			writeLines(lines, lineEnding, output);
		} else {
			if (lines == null) {
				return;
			}
			if (lineEnding == null) {
				lineEnding = LINE_SEPARATOR;
			}
			for (Iterator<String> it = lines.iterator(); it.hasNext();) {
				Object line = it.next();
				if (line != null) {
					output.write(line.toString().getBytes(encoding));
				}
				output.write(lineEnding.getBytes(encoding));
			}
		}
	}

	public static void writeLines(Collection<String> lines, String lineEnding, OutputStream output) throws IOException {
		if (lines == null) {
			return;
		}
		if (lineEnding == null) {
			lineEnding = LINE_SEPARATOR;
		}
		for (Iterator<String> it = lines.iterator(); it.hasNext();) {
			Object line = it.next();
			if (line != null) {
				output.write(line.toString().getBytes());
			}
			output.write(lineEnding.getBytes());
		}
	}

	public static List<String> readLines(InputStream input, String encoding) throws IOException {
		if (encoding == null) {
			return readLines(input);
		} else {
			InputStreamReader reader = new InputStreamReader(input, encoding);
			return readLines(reader);
		}
	}

	public static List<String> readLines(InputStream input) throws IOException {
		InputStreamReader reader = new InputStreamReader(input);
		return readLines(reader);
	}

	public static List<String> readLines(Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		List<String> list = new ArrayList<String>();
		String line = reader.readLine();
		while (line != null) {
			list.add(line);
			line = reader.readLine();
		}
		return list;
	}

}
