package ru.vsu.cs.checkers.utils;

import ru.vsu.cs.checkers.game.ChineseCheckersGameException;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Board;
import ru.vsu.cs.checkers.piece.Checker;

import java.util.List;

public class GameUtils {

    public static void fillPlayers(int countOfPlayers, List<Players> currentlyPlaying) throws ChineseCheckersGameException {
        currentlyPlaying.add(Players.BLACK);
        switch (countOfPlayers) {
            case 2: {
                currentlyPlaying.add(Players.WHITE);
                break;
            }
            case 3: {
                currentlyPlaying.add(Players.RED);
                currentlyPlaying.add(Players.GREEN);
                break;
            }
            case 4: {
                currentlyPlaying.add(Players.BLUE);
                currentlyPlaying.add(Players.WHITE);
                currentlyPlaying.add(Players.GREEN);
                break;
            }
            case 6: {
                currentlyPlaying.add(Players.BLUE);
                currentlyPlaying.add(Players.RED);
                currentlyPlaying.add(Players.WHITE);
                currentlyPlaying.add(Players.GREEN);
                currentlyPlaying.add(Players.YELLOW);
                break;
            }
            default: {
                throw new ChineseCheckersGameException("Wrong count of players. Expected 2, 3, 4, 6 but found " + countOfPlayers);
            }
        }
    }

    public static void initCheckersForGame(List<Players> currentlyPlaying, List<Checker> checkers) {
        for (Players player : currentlyPlaying) {
            for (int i = 0; i < 10; i++) {
                checkers.add(new Checker(player));
            }
        }
    }

    public static void putCheckersOnTheirPlaces(Board board, List<Checker> checkers) {
        int i = 0;
        for (Checker checker : checkers) {
            board.put(10 * checker.getOwner().getSector() + i, checker);
            i++;
            if (i == 10) {
                i = 0;
            }
        }
    }

    public static Players checkWin(Board board, List<Players> currentlyPlaying) {
        for (Players player : currentlyPlaying) {
            int oppositeSector = Math.abs(3 - player.getSector());
            int count = 0;
            for (int i = 0; i < 10; i++) {
                Checker c = board.getChecker(oppositeSector * 10 + i);
                if (c != null && c.getOwner() == player) {
                  count++;
                }
            }
            if (count == 10) {
                return player;
            }
        }
        return null;
    }

}
