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
        this(base.toCharArray());
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
        if (srcEnd - srcBegin >= 0)
            System.arraycopy(sequence, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomString that = (CustomString) o;
        return compareTo(that) == 0;
    }

    public boolean equalsIgnoreCase(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomString that = (CustomString) o;
        return compareToIgnoreCase(that) == 0;
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

    public int compareToIgnoreCase(final CustomString o) {
        return toLowerCase().compareTo(o.toLowerCase());
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
        return regionMatches(ignoreCase, toffset, other.toCharArray(), ooffset, len);
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
        CustomString thisCopy = new CustomString(this);
        char[] otherCopy = other.clone();
        if (ignoreCase) {
            thisCopy = thisCopy.toLowerCase();
            otherCopy = toLowerCase(otherCopy);
        }
        for (int index = 0; index < len; index++) {
            if (thisCopy.sequence[toffset + index] != otherCopy[ooffset + index]) {
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
        return regionMatches(size - cs.size, cs, 0, cs.size);
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
            res = new char[endIndex - beginIndex];
            if (endIndex >= beginIndex)
                System.arraycopy(sequence, beginIndex, res, 0, endIndex - beginIndex);
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
        return contains(cs.toCharArray());
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
        char[] res = toCharArray();
        for (int index = 0; index < res.length; index++) {
            if (res[index] == oldChar) {
                res[index] = newChar;
            }
        }
        return new CustomString(res);
    }

    public CustomString replace(final CustomString target,
                                final CustomString replacement) {
        CustomString res = new CustomString(this);
        int indexInRes = 0;
        char[] tar = target.toCharArray();
        char[] rep = replacement.toCharArray();
        for (int beginIndex = 0; size - beginIndex >= tar.length; beginIndex++) {
            if (regionMatches(beginIndex, tar, 0, tar.length)) {
                while (res.sequence.length < res.size - tar.length + rep.length) {
                    res.sequence = Arrays.copyOf(res.sequence, res.sequence.length * 2);
                }
                System.arraycopy(res.sequence, indexInRes + tar.length, res.sequence,
                        indexInRes + rep.length, res.size - indexInRes - tar.length + 1);
                res.size += rep.length - tar.length;
                if (tar.length > rep.length) {
                    for (int index = res.size; index < res.sequence.length; index++) {
                        res.sequence[index] = '\u0000';
                    }
                }
                System.arraycopy(rep, 0, res.sequence, indexInRes, rep.length);
                beginIndex += tar.length - 1;
                indexInRes += rep.length - 1;
            }
            indexInRes++;
        }
        return res;
    }

    /*
    //todo: functions with regex

    public CustomString replaceFirst(final CustomString regex,
                                     final CustomString replacement) {
        return null;
    }

    public CustomString replaceAll(final CustomString regex,
                                   final CustomString replacement) {
        return null;
    }

    public boolean matches(CustomString regex) {
        return false;
    }

    public CustomString[] split(final CustomString regex) {
        return null;
    }

    public CustomString[] split(final CustomString regex,
                          final int limit) {
        return null;
    }

    public static CustomString format(final CustomString format,
                                      final Object... args) {
        return null;
    }
    */

    public CustomString trim() {
        int beginIndex = 0;
        while (beginIndex < size && sequence[beginIndex] == ' ') {
            beginIndex++;
        }
        int endIndex = size - 1;
        while (endIndex >= 0 && sequence[endIndex] == ' ') {
            endIndex--;
        }
        return substring(beginIndex, endIndex + 1);
    }

    public CustomString toLowerCase() {
        CustomString res = new CustomString(this);
        for (int index = 0; index < res.size; index++) {
            res.sequence[index] = toLowerCase(res.sequence[index]);
        }
        return res;
    }

    private static char toLowerCase(final char c) {
        return Character.toLowerCase(c);
    }

    private static char[] toLowerCase(final char[] data) {
        char[] res = new char[data.length];
        for (int index = 0; index < res.length; index++) {
            res[index] = toLowerCase(res[index]);
        }
        return res;
    }

    public CustomString toUpperCase() {
        CustomString res = new CustomString(this);
        for (int index = 0; index < res.size; index++) {
            res.sequence[index] = toUpperCase(res.sequence[index]);
        }
        return res;
    }

    private static char toUpperCase(final char c) {
        return Character.toUpperCase(c);
    }

    @Override
    public String toString() {
        return new String(toCharArray());
    }

    public char[] toCharArray() {
        return Arrays.copyOf(sequence, size);
    }


    public static CustomString copyValueOf(final char[] data,
                                           final int offset,
                                           final int count) {
        char[] newData = new char[count];
        System.arraycopy(data, offset, newData, 0, count);
        return new CustomString(newData);
    }

    public static CustomString copyValueOf(char[] data) {
        return new CustomString(data);
    }

    public static CustomString valueOf(char c) {
        return new CustomString(c);
    }

    private void checkIndex(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
    }
}
