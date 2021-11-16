package ru.vsu.cs.checkers.utils;

public class GameUtils {

    private static final int COUNT_OF_DIRECTIONS = 6;

    private GameUtils() {
    }

    public static int getOppositeDirection(int i) {
        if (i >= COUNT_OF_DIRECTIONS / 2) {
            return i - COUNT_OF_DIRECTIONS / 2;
        } else {
            return i + COUNT_OF_DIRECTIONS / 2;
        }
    }

}
