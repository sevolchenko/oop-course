package ru.vsu.cs.checkers.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.Main;
import ru.vsu.cs.checkers.game.*;
import ru.vsu.cs.checkers.piece.*;
import ru.vsu.cs.checkers.structures.graph.GraphException;

import java.util.List;

public class Request {

    private static final Logger log = LoggerFactory.getLogger(Request.class);

    private static ChineseCheckersGame game = new ChineseCheckersGame();


    public static void startGame(int n) throws ChineseCheckersGameException {
        log.info("Request.startGame(" + n + ") called.");
        game.startGame(n);
    }

    public static List<Players> getPlayingPlayers() {
        log.info("Request.getPlayingPlayers() called.");
        return game.getCurrentlyPlaying();
    }

    public static GameStates getGameState() {
        log.info("Request.getGameState() called.");
        return game.getGameState();
    }

    public static Checker getCheckerAt(int place) {
        log.info("Request.getCheckerAt(" + place + ") called.");
        return game.getCheckerAt(place);
    }

    public static Players getWinner() {
        log.info("Request.getWinner() called.");
        return game.getWinner();
    }

    public static void move(int from, int to) throws GraphException, ChineseCheckersGameException {
        log.info("Request.move(" + from + ", " + to + ") called.");
        game.move(from, to);
    }
}
