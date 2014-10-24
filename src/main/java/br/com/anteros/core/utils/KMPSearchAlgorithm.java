package br.com.anteros.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class KMPSearchAlgorithm {

    public static long search(final InputStream inputStream,
                              final byte[] pattern,
                              int[] table) throws IOException {

        if (inputStream == null || pattern == null || pattern.length == 0) {
            return -1;
        }
        final int patternLength = pattern.length;
        long streamIndex = -1;
        int  currentByte; 

        if (patternLength == 1) {
            final int byteToFind = pattern[0];

            while (-1 != (currentByte = inputStream.read())) {
                streamIndex++;

                if (currentByte == byteToFind) {
                    return streamIndex;
                }
            }
            return -1;
        }
        int patternIndex = 0;
        if (table == null) {
            table = computeTable(pattern);
        }

        while (-1 != (currentByte = inputStream.read())) {
            streamIndex++;

            if (currentByte == pattern[patternIndex]) {
                patternIndex++;
            } else if (patternIndex > 0) {
                patternIndex = table[patternIndex];

                patternIndex++;
            }

            if (patternIndex == patternLength) {
                return streamIndex - (patternLength - 1);
            }
        }

        return -1;
    }

    public static long search(final Reader reader, final char[] pattern,
                              int[] table) throws IOException {

        if (reader == null || pattern == null || pattern.length == 0) {
            return -1;
        }
        final int patternLength = pattern.length;
        long streamIndex = -1;
        int  currentCharacter;

        if (patternLength == 1) {
            final int characterToFind = pattern[0];

            while (-1 != (currentCharacter = reader.read())) {
                streamIndex++;

                if (currentCharacter == characterToFind) {
                    return streamIndex;
                }
            }
            return -1;
        }

        int patternIndex = 0;
        if (table == null) {
            table = computeTable(pattern);
        }

        while (-1 != (currentCharacter = reader.read())) {
            streamIndex++;

            if (currentCharacter == pattern[patternIndex]) {
                patternIndex++;
            } else if (patternIndex > 0) {
                patternIndex = table[patternIndex];

                patternIndex++;
            }

            if (patternIndex == patternLength) {
                return streamIndex - (patternLength - 1);
            }
        }

        return -1;
    }

   
    public static int search(final byte[] source, final byte[] pattern,
                             int[] table, final int start) {

        if (source == null || pattern == null || pattern.length == 0) {
            return -1;
        }

        final int sourceLength  = source.length;
        final int patternLength = pattern.length;
        int sourceIndex = start;

        if (patternLength == 1) {
            final int byteToFind = pattern[0];

            for (; sourceIndex < sourceLength; sourceIndex++) {
                if (source[sourceIndex] == byteToFind) {
                    return sourceIndex;
                }
            }
            return -1;
        }

        int matchStart   = start;
        int patternIndex = 0;
        if (table == null) {
            table = computeTable(pattern);
        }

        while ((sourceIndex < sourceLength)
                && (patternIndex < patternLength)) {
            if (source[sourceIndex] == pattern[patternIndex]) {
                patternIndex++;
            } else {
                final int tableVaue = table[patternIndex];
                matchStart += (patternIndex - tableVaue);
                if (patternIndex > 0) {
                    patternIndex = tableVaue;
                }
                patternIndex++;
            }
            sourceIndex = (matchStart + patternIndex);
        }

        if (patternIndex == patternLength) {
            return matchStart;
        } else {
            return -1;
        }
    }

    public static int search(final char[] source, final char[] pattern,
                             int[] table, final int start) {

        if (source == null || pattern == null || pattern.length == 0) {
            return -1;
        }

        final int sourceLength  = source.length;
        final int patternLength = pattern.length;
        int       sourceIndex   = start;

        if (patternLength == 1) {
            final int characterToFind = pattern[0];

            for (; sourceIndex < sourceLength; sourceIndex++) {
                if (source[sourceIndex] == characterToFind) {
                    return sourceIndex;
                }
            }
            return -1;
        }

        int matchStart   = start;
        int patternIndex = 0;

        if (table == null) {
            table = computeTable(pattern);
        }

        while ((sourceIndex < sourceLength)
                && (patternIndex < patternLength)) {
            if (source[sourceIndex] == pattern[patternIndex]) {
                patternIndex++;
            } else {
                final int tableValue = table[patternIndex];
                matchStart += (patternIndex - tableValue);
                if (patternIndex > 0) {
                    patternIndex = tableValue;
                }
                patternIndex++;
            }

            sourceIndex = (matchStart + patternIndex);
        }

        if (patternIndex == patternLength) {
            return matchStart;
        } else {
            return -1;
        }
    }

    
    public static int search(final String source, final String pattern,
                             int[] table, final int start) {

        if (source == null || pattern == null || pattern.length() == 0) {
            return -1;
        }

        final int patternLength = pattern.length();
        if (patternLength == 1) {
            return source.indexOf(pattern, start);
        }

        final int sourceLength = source.length();

        int matchStart   = start;
        int sourceIndex  = start;
        int patternIndex = 0;

        if (table == null) {
            table = computeTable(pattern);
        }

        //
        while ((sourceIndex < sourceLength)
                && (patternIndex < patternLength)) {
            if (source.charAt(sourceIndex) == pattern.charAt(patternIndex)) {
                patternIndex++;
            } else {
                final int tableValue = table[patternIndex];
                matchStart += (patternIndex - tableValue);
                if (patternIndex > 0) {
                    patternIndex = tableValue;
                }
                patternIndex++;
            }

            sourceIndex = matchStart + patternIndex;
        }

        if (patternIndex == patternLength) {
            return matchStart;
        } else {
            return -1;
        }
    }

    public static int[] computeTable(final byte[] pattern) {

        if (pattern == null) {
            throw new IllegalArgumentException("Pattern must  not be null.");
        } else if (pattern.length < 2) {
            throw new IllegalArgumentException("Pattern length must be > 1.");
        }

        final int[] table = new int[pattern.length];
        int         i     = 2;
        int         j     = 0;
        table[0] = -1;
        table[1] = 0;
        while (i < pattern.length) {
            if (pattern[i - 1] == pattern[j]) {
                table[i] = j + 1;
                j++;
                i++;
            } else if (j > 0) {
                j = table[j];
            } else {
                table[i] = 0;
                i++;
                j = 0;
            }
        }
        return table;
    }

    public static int[] computeTable(final char[] pattern) {

        if (pattern == null) {
            throw new IllegalArgumentException("Pattern must  not be null.");
        } else if (pattern.length < 2) {
            throw new IllegalArgumentException("Pattern length must be > 1.");
        }

        int[] table = new int[pattern.length];
        int   i     = 2;
        int   j     = 0;

        table[0] = -1;
        table[1] = 0;

        while (i < pattern.length) {
            if (pattern[i - 1] == pattern[j]) {
                table[i] = j + 1;

                j++;
                i++;
            } else if (j > 0) {
                j = table[j];
            } else {
                table[i] = 0;

                i++;

                j = 0;
            }
        }

        return table;
    }

    public static int[] computeTable(final String pattern) {

        if (pattern == null) {
            throw new IllegalArgumentException("Pattern must  not be null.");
        } else if (pattern.length() < 2) {
            throw new IllegalArgumentException("Pattern length must be > 1.");
        }

        final int patternLength = pattern.length();
        int[] table = new int[patternLength];
        int   i     = 2;
        int   j     = 0;

        table[0] = -1;
        table[1] = 0;

        while (i < patternLength) {
            if (pattern.charAt(i - 1) == pattern.charAt(j)) {
                table[i] = j + 1;

                j++;
                i++;
            } else if (j > 0) {
                j = table[j];
            } else {
                table[i] = 0;

                i++;

                j = 0;
            }
        }

        return table;
    }
}