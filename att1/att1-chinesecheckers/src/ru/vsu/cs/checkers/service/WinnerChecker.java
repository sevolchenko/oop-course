package ru.vsu.cs.checkers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Board;
import ru.vsu.cs.checkers.piece.Checker;
import ru.vsu.cs.checkers.utils.GameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class WinnerChecker {

    private static final Logger log = LoggerFactory.getLogger(WinnerChecker.class);

    private Board board;
    private Queue<Players> currentlyPlaying;

    public WinnerChecker(Board board, Queue<Players> currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
        this.board = board;
    }

    public List<Players> check() {
        List<Players> winners = new ArrayList<>();
        for (Players player : currentlyPlaying) {
            int oppositeSector = GameUtils.getOppositeDirection(player.getSector());
            int count = 0;
            for (int i = 0; i < 10; i++) {
                Checker c = board.getChecker(oppositeSector * 10 + i);
                if (c != null && c.getOwner() == player) {
                    count++;
                }
            }
            if (count == 10) {
                winners.add(player);
            }
        }
        log.info("Checking for winner...");
        return winners;
    }

}
