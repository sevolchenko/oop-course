package ru.vsu.cs.volchenko_s_a;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomStringTest {

    @Test
    public void testCreation1() {
        CustomString cs = new CustomString("asdf");
        Assertions.assertEquals("asdf", cs.toString());
    }

    @Test
    public void testCreation2() {
        CustomString cs = new CustomString(new char[] {'a', 's', 'd', 'f'});
        Assertions.assertEquals("asdf", cs.toString());
    }

    @Test
    public void testLength() {
        CustomString cs = new CustomString("asdf");
        Assertions.assertEquals(cs.length(), 4);
    }

    @Test
    public void testIsEmpty1() {
        CustomString cs = new CustomString("asdf");
        Assertions.assertFalse(cs.isEmpty());
    }

    @Test
    public void testIsEmpty2() {
        CustomString cs = new CustomString("");
        Assertions.assertTrue(cs.isEmpty());
    }

    @Test
    public void testCharAt() {
        CustomString cs = new CustomString("asdf");
        Assertions.assertEquals(cs.charAt(3), 'f');
    }

    @Test
    public void testEquals1() {
        CustomString cs1 = new CustomString("asdf");
        CustomString cs2 = new CustomString(new char[] {'a', 's', 'd', 'f'});
        Assertions.assertEquals(cs1, cs2);
    }

    @Test
    public void testEquals2() {
        CustomString cs1 = new CustomString("Asdf");
        CustomString cs2 = new CustomString("aSDf");
        Assertions.assertNotEquals(cs1, cs2);
    }

    @Test
    public void testEqualsIgnoreCase() {
        CustomString cs1 = new CustomString("Asdf");
        CustomString cs2 = new CustomString("aSDf");
        Assertions.assertTrue(cs1.equalsIgnoreCase(cs2));
    }

    @Test
    public void testRegionMatches1() {
        CustomString cs1 = new CustomString("dsadsl");
        CustomString cs2 = new CustomString("gasf");
        Assertions.assertTrue(cs1.regionMatches(1, cs2, 2, 1));
    }

    @Test
    public void testRegionMatches2() {
        CustomString cs1 = new CustomString("dsfdsl");
        CustomString cs2 = new CustomString("gasf");
        Assertions.assertTrue(cs1.regionMatches(1, cs2, 2, 2));
    }

    @Test
    public void testStartsWith() {
        CustomString cs1 = new CustomString("dsfdsl");
        CustomString cs2 = new CustomString("dsf");
        Assertions.assertTrue(cs1.startsWith(cs2));
    }

    @Test
    public void testEndsWith() {
        CustomString cs1 = new CustomString("dsfdsl");
        CustomString cs2 = new CustomString("sl");
        Assertions.assertTrue(cs1.endsWith(cs2));
    }

    @Test
    public void testIndexOf1() {
        CustomString cs = new CustomString("dsfdsl");
        Assertions.assertEquals(cs.indexOf('s'), 1);
    }

    @Test
    public void testIndexOf2() {
        CustomString cs = new CustomString("dsfdsl");
        Assertions.assertEquals(cs.indexOf('s', 2), 4);
    }

    @Test
    public void testIndexOf3() {
        CustomString cs = new CustomString("dsfdsl");
        Assertions.assertEquals(cs.indexOf('a'), -1);
    }

    @Test
    public void testIndexOf4() {
        CustomString cs = new CustomString("dsfdsl");
        Assertions.assertEquals(cs.indexOf(new CustomString("fd")), 2);
    }

    @Test
    public void testLastIndexOf1() {
        CustomString cs = new CustomString("aaaaaa");
        Assertions.assertEquals(cs.lastIndexOf('a'), 5);
    }

    @Test
    public void testLastIndexOf2() {
        CustomString cs = new CustomString("aaaaaa");
        Assertions.assertEquals(cs.lastIndexOf(new CustomString("aa")), 4);
    }

    @Test
    public void testSubstring1() {
        CustomString cs = new CustomString("dsfdsl");
        Assertions.assertEquals(cs.substring(2), new CustomString("fdsl"));
    }

    @Test
    public void testSubstring2() {
        CustomString cs = new CustomString("dsfdsl");
        Assertions.assertEquals(cs.substring(2, 4), new CustomString("fd"));
    }

    @Test
    public void testConcat1() {
        CustomString cs1 = new CustomString("dsfdsl");
        CustomString cs2 = new CustomString("a");
        Assertions.assertEquals(cs1.concat(cs2), new CustomString("dsfdsla"));
    }

    @Test
    public void testConcat2() {
        CustomString cs1 = new CustomString("dsfdsl");
        CustomString cs2 = new CustomString("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        Assertions.assertEquals(cs1.concat(cs2), new CustomString("dsfdslaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Test
    public void testReplace1() {
        CustomString cs = new CustomString("dsfdsl");
        Assertions.assertEquals(cs.replace('d', 'a'), new CustomString("asfasl"));
    }

    @Test
    public void testReplace2() {
        CustomString cs = new CustomString("dsfdsl");
        CustomString tar = new CustomString("ds");
        CustomString rep = new CustomString("a");
        Assertions.assertEquals(cs.replace(tar, rep), new CustomString("afal"));
    }

    @Test
    public void testReplace3() {
        CustomString cs = new CustomString("dsfdsl");
        CustomString tar = new CustomString("ds");
        CustomString rep = new CustomString("bbbb");
        Assertions.assertEquals(cs.replace(tar, rep), new CustomString("bbbbfbbbbl"));
    }

    @Test
    public void testTrim() {
        CustomString cs1 = new CustomString("  dsfdsl   ");
        CustomString cs2 = new CustomString("dsfdsl");
        Assertions.assertEquals(cs1.trim(), cs2);
    }

    @Test
    public void testToLowerCase1() {
        CustomString cs1 = new CustomString("AsDfsaA");
        CustomString cs2 = new CustomString("asdfsaa");
        Assertions.assertEquals(cs1.toLowerCase(), cs2);
    }

    @Test
    public void testToLowerCase2() {
        CustomString cs1 = new CustomString("asdfsaa");
        Assertions.assertEquals(cs1.toLowerCase(), cs1);
    }

    @Test
    public void testToUpperCase1() {
        CustomString cs1 = new CustomString("asdfsaa");
        CustomString cs2 = new CustomString("ASDFSAA");
        Assertions.assertEquals(cs1.toUpperCase(), cs2);
    }

    @Test
    public void testCopyValueOf() {
        char[] data = new char[] {'a', 'f', 's', 'w'};
        CustomString cs = new CustomString("fs");
        Assertions.assertEquals(CustomString.copyValueOf(data, 1, 2), cs);
    }

    @Test
    public void testValueOf() {
        Assertions.assertEquals(CustomString.valueOf('a'), new CustomString('a'));
    }
}
