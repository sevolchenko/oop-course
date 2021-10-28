package ru.vsu.cs.checkers.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.game.ChineseCheckersGameException;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Board;
import ru.vsu.cs.checkers.piece.Checker;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GameUtils {

    private static final Logger log = LoggerFactory.getLogger(GameUtils.class);

    public static void fillPlayers(int countOfPlayers, Queue<Players> currentlyPlaying) throws ChineseCheckersGameException {
        currentlyPlaying.offer(Players.DARK);
        switch (countOfPlayers) {
            case 2: {
                currentlyPlaying.offer(Players.WHITE);
                break;
            }
            case 3: {
                currentlyPlaying.offer(Players.RED);
                currentlyPlaying.offer(Players.GREEN);
                break;
            }
            case 4: {
                currentlyPlaying.offer(Players.BLUE);
                currentlyPlaying.offer(Players.WHITE);
                currentlyPlaying.offer(Players.GREEN);
                break;
            }
            case 6: {
                currentlyPlaying.offer(Players.BLUE);
                currentlyPlaying.offer(Players.RED);
                currentlyPlaying.offer(Players.WHITE);
                currentlyPlaying.offer(Players.GREEN);
                currentlyPlaying.offer(Players.YELLOW);
                break;
            }
            default: {
                throw new ChineseCheckersGameException("Wrong count of players. Expected 2, 3, 4, 6 but found " + countOfPlayers);
            }
        }
        log.info("Players identified: " + currentlyPlaying.toString());
    }

    public static List<Checker> initCheckersForGame(Queue<Players> currentlyPlaying) {
        List<Checker> checkers = new ArrayList<>();
        for (Players player : currentlyPlaying) {
            for (int i = 0; i < 10; i++) {
                checkers.add(new Checker(player));
            }
        }
        log.info("Checkers for this players are ready.");
        return checkers;
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

    public static Players checkWinner(Board board, Queue<Players> currentlyPlaying) {
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


    public static int getOpposite(int i) {
        if (i >= 3) {
            return i - 3;
        } else {
            return i + 3;
        }
    }

}
