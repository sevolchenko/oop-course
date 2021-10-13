package ru.vsu.cs.checkers.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.game.ChineseCheckersGame;
import ru.vsu.cs.checkers.game.ChineseCheckersGameException;
import ru.vsu.cs.checkers.game.GameStates;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Board;
import ru.vsu.cs.checkers.piece.Checker;

import java.util.List;

public class GameUtils {

    private static final Logger log = LoggerFactory.getLogger(GameUtils.class);

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
        log.info("Players identified: " + currentlyPlaying.toString());
    }

    public static void initCheckersForGame(List<Players> currentlyPlaying, List<Checker> checkers) {
        for (Players player : currentlyPlaying) {
            for (int i = 0; i < 10; i++) {
                checkers.add(new Checker(player));
            }
        }
        log.info("Checkers for this players are ready.");
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
        log.info("Checkers staying on their places.");
    }

    public static Players checkWinner(Board board, List<Players> currentlyPlaying) {
        for (Players player : currentlyPlaying) {
            int oppositeSector = getOpposite(player.getSector());
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
        log.info("Checking for winner...");
        return null;
    }

    public static Players nextMoving(List<Players> currentlyPlaying, Players whoseMoving) {
        int index = currentlyPlaying.indexOf(whoseMoving);
        if (index == currentlyPlaying.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        return currentlyPlaying.get(index);
    }

    public static int getOpposite(int i) {
        if (i >= 3) {
            return i - 3;
        } else {
            return i + 3;
        }
    }

}
