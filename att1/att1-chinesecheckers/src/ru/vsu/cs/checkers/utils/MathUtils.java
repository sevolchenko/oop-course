package ru.vsu.cs.checkers.utils;

public class MathUtils {

    public static int sum(int a) {
        int sum = 0;
        for (int i = 1; i <= a; i++) {
            sum += i;
        }
        return sum;
    }

}
