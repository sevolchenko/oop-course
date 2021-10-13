package ru.vsu.cs.checkers.utils;

import javafx.scene.paint.Color;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Checker;

public class DrawingUtils {

    public static Color getColor(Checker checker) {
        switch (checker.getOwner()) {
            case BLACK -> {
                return Color.BLACK;
            }
            case BLUE -> {
                return Color.BLUE;
            }
            case RED -> {
                return Color.RED;
            }
            case WHITE -> {
                return new Color(239, 236, 174, 5);
            }
            case GREEN -> {
                return Color.GREEN;
            }
            case YELLOW -> {
                return Color.YELLOW;
            }
            default -> {
                return null;
            }
        }
    }

}
