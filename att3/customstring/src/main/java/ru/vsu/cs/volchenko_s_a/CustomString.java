package ru.vsu.cs.volchenko_s_a;

import java.util.Arrays;
import java.util.Objects;

public class CustomString implements Comparable<CustomString> {

    private static final int DEFAULT_CAPACITY = 20;

    private char[] sequence;
    private int size;

    public CustomString() {
        this(DEFAULT_CAPACITY);
    }

    public CustomString(final int capacity) {
        sequence = new char[capacity];
        size = 0;
    }

    public CustomString(final String base) {
        sequence = base.toCharArray().clone();
        size = base.length();
    }

    public CustomString(final char c) {
        this(new char[] {c});
    }

    public CustomString(final char[] chars) {
        sequence = Arrays.copyOf(chars, Math.max(chars.length, DEFAULT_CAPACITY));
        size = Math.min(sequence.length, chars.length);
    }

    public CustomString(final CustomString base) {
        sequence = base.sequence.clone();
        size = base.size;
    }

    public int length() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public char charAt(final int index) {
        checkIndex(index);
        return sequence[index];
    }

    public void getChars(final int srcBegin,
                         final int srcEnd,
                         final char[] dst,
                         final int dstBegin) {
        checkIndex(srcBegin);
        checkIndex(srcEnd);
        for (int index = srcBegin; index < srcEnd; index++) {
            dst[dstBegin + index - srcBegin] = sequence[index];
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomString that = (CustomString) o;
        return compareTo(that) == 0;
    }

    public boolean equalsIgnoreCase(final Object o) { //todo: use toLowerCase;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomString that = (CustomString) o;
        return compareTo(that) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(sequence), size);
    }

    @Override
    public int compareTo(final CustomString o) {
        if (size != o.size) {
            return size - o.size;
        }
        for (int index = 0; index < size; index++) {
            if (sequence[index] != o.sequence[index]) {
                return sequence[index] - o.sequence[index];
            }
        }
        return 0;
    }

    public int compareToIgnoreCase(final CustomString o) { //todo: use toLowerCase;
        return 0;
    }

    public boolean regionMatches(final int toffset,
                                 final CustomString other,
                                 final int ooffset,
                                 final int len) {
        return regionMatches(false, toffset, other, ooffset, len);
    }

    public boolean regionMatches(final boolean ignoreCase,
                                 final int toffset,
                                 final CustomString other,
                                 final int ooffset,
                                 final int len) {
        return regionMatches(ignoreCase, toffset, other.sequence, ooffset, len);
    }

    public boolean regionMatches(final int toffset,
                                 final char[] other,
                                 final int ooffset,
                                 final int len) {
        return regionMatches(false, toffset, other, ooffset, len);
    }

    public boolean regionMatches(final boolean ignoreCase,
                                 final int toffset,
                                 final char[] other,
                                 final int ooffset,
                                 final int len) {
        if (ignoreCase) {
            //todo: toLowerCase
        }
        for (int index = 0; index < len; index++) {
            if (sequence[toffset + index] != other[ooffset + index]) {
                return false;
            }
        }
        return true;
    }

    public boolean startsWith(final CustomString cs) {
        return startsWith(cs, 0);
    }

    public boolean startsWith(final CustomString cs,
                              final int toffset) {
        return regionMatches(toffset, cs, 0, cs.size);
    }

    public boolean endsWith(final CustomString cs) {
        return regionMatches(size - cs.size + 1, cs, 0, cs.size);
    }

    public int indexOf(final char c) {
        return indexOf(c, 0);
    }

    public int indexOf(final char c,
                       final int fromIndex) {
        for (int index = fromIndex; index < size; index++) {
            if (sequence[index] == c) {
                return index;
            }
        }
        return -1;
    }

    public int indexOf(final CustomString cs) {
        return indexOf(cs, 0);
    }

    public int indexOf(final CustomString cs,
                       final int fromIndex) {
        for (int index = fromIndex; size - index >= cs.size; index++) {
            if (regionMatches(index, cs, 0, cs.size)) {
                return index;
            }
        }
        return -1;
    }

    public int lastIndexOf(final char c) {
        return lastIndexOf(c, 0);
    }

    public int lastIndexOf(final char c,
                       final int fromIndex) {
        for (int index = size - 1; index >= fromIndex; index--) {
            if (sequence[index] == c) {
                return index;
            }
        }
        return -1;
    }

    public int lastIndexOf(final CustomString cs) {
        return lastIndexOf(cs, 0);
    }

    public int lastIndexOf(final CustomString cs,
                           final int fromIndex) {
        for (int index = size - cs.size; index >= fromIndex; index--) {
            if (regionMatches(index, cs, 0, cs.size)) {
                return index;
            }
        }
        return -1;
    }

    public CustomString substring(final int beginIndex) {
        return substring(beginIndex, size);
    }

    public CustomString substring(final int beginIndex,
                                  final int endIndex) {
        char[] res;
        if (beginIndex == 0) {
            res = sequence.clone();
            for (int index = endIndex; index < size; index++) {
                res[index] = '\u0000';
            }
        } else {
            res = new char[endIndex - beginIndex + 1];
            for (int index = beginIndex; index < endIndex; index++) {
                res[index - beginIndex] = sequence[index];
            }
        }
        return new CustomString(res);
    }

    public CustomString concat(final CustomString str) {
        CustomString res = new CustomString(this);
        while (res.size + str.size > res.sequence.length) {
            res.sequence = Arrays.copyOf(res.sequence, res.sequence.length * 2);
        }
        for (int index = 0; index < str.size; index++) {
            res.sequence[size + index] = str.sequence[index];
            res.size++;
        }
        return res;
    }

    public boolean contains(final CustomString cs) {
        return contains(Arrays.copyOf(cs.sequence, cs.size));
    }

    public boolean contains(final char[] cs) {
        for (int beginIndex = 0; size - beginIndex >= cs.length; beginIndex++) {
            if (regionMatches(beginIndex, cs, 0, cs.length)) {
                return true;
            }
        }
        return false;
    }

    public CustomString replace(final char oldChar,
                                final char newChar) {
        char[] res = Arrays.copyOf(sequence, size);
        for (int index = 0; index < res.length; index++) {
            if (res[index] == oldChar) {
                res[index] = newChar;
            }
        }
        return new CustomString(res);
    }

    public CustomString replace(final CustomString old) {
        //todo
        return null;
    }

    public CustomString replace(final CustomString target,
                                final CustomString replacement) {
        return null;
    }

    public CustomString replaceFirst(final CustomString target,
                                     final CustomString replacement) {
        return null;
    }

    public CustomString replaceAll(final CustomString target,
                                   final CustomString replacement) {
        return null;
    }

    public boolean matches(String regex) {
        return false; //todo
    }

    public CustomString[] split(final CustomString regex) {
        return null;
    }

    public CustomString[] split(final CustomString regex,
                          final int limit) {
        return null;
    }

    public CustomString trim() {
        return null;
    }

    public CustomString toLowerCase() {
        return null;
    }

    public CustomString toUpperCase() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    public char[] toCharArray() {
        return Arrays.copyOf(sequence, size);
    }

    public static CustomString format(final CustomString format,
                                      final Object... args) {
        return null;
    }

    public static CustomString copyValueOf(final char[] data,
                                           final int offset,
                                           final int count) {
        return null;
    }

    public static CustomString copyValueOf(char[] data) {
        return null;
    }

    public static CustomString valueOf(char c) {
        return null;
    }

    /*

    public static CustomString (char[] data) {

    }

     */

    private void checkIndex(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
    }
}
