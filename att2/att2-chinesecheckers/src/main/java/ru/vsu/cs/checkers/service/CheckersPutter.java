package ru.vsu.cs.checkers.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Board;
import ru.vsu.cs.checkers.piece.Checker;

import java.util.Queue;

public class CheckersPutter {

    private static final Logger log = LoggerFactory.getLogger(CheckersPutter.class);

    public CheckersPutter() {
    }

    public void put(Board board, Queue<Players> currentlyPlaying) {
        for (Players player : currentlyPlaying) {
            int sector = player.getSector();
            for (int i = 0; i < 10; i++) {
                board.put(10 * sector + i, new Checker(player));
            }
        }
        log.info("Checkers staying on their places");
    }
}
