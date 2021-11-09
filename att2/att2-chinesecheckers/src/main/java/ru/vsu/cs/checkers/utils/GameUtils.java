package ru.vsu.cs.checkers.utils;

public class GameUtils {

    public static int getOppositeDirection(int i) {
        if (i >= 3) {
            return i - 3;
        } else {
            return i + 3;
        }
    }

}
