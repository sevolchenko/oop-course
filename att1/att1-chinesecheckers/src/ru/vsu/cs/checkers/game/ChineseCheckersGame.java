package ru.vsu.cs.checkers.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.piece.*;
import ru.vsu.cs.checkers.structures.graph.GraphException;
import ru.vsu.cs.checkers.utils.GameUtils;

import java.util.ArrayDeque;
import java.util.Queue;

public class ChineseCheckersGame {

    private static final Logger log = LoggerFactory.getLogger(ChineseCheckersGame.class);

    private Queue<Players> currentlyPlaying;
    private Board board;

    private boolean isJump;
    private int lastMoved;
    private Players winner;

    public ChineseCheckersGame() {
        currentlyPlaying = new ArrayDeque<>();
        board = new Board();
        log.info("Game initialized.");
    }

    public void startGame(int countOfPlayers) throws ChineseCheckersGameException {
        GameUtils.fillPlayers(countOfPlayers, currentlyPlaying);
        GameUtils.putCheckersOnTheirPlaces(board, GameUtils.initCheckersForGame(currentlyPlaying));
        log.info("Game started.");
    }

    public void move(int from, int to) throws ChineseCheckersGameException, GraphException {
        if (from < 0 || to < 0 || from > 120 || to > 120) {
            throw new ChineseCheckersGameException("Invalid indices of cells: " + from + ", " + to);
        }
        Checker checker = board.getChecker(from);
        if (checker == null || checker.getOwner() != getWhoseMoving()) {
            log.info("Move is not possible. Place " + from + " is empty or checker's owner is not " + getWhoseMoving());
            return;
        }
        if (!isJump && board.isConnected(from, to) && board.getChecker(to) == null) {
            board.clear(from);
            board.put(to, checker);
            currentlyPlaying.offer(currentlyPlaying.poll());
            log.info(getWhoseMoving() + "'s checker " + from + " successfully moved at position " + to);
        } else if (board.isConnectedAcrossOne(from, to)) {
            if ((!isJump || lastMoved == from)) {
                board.clear(from);
                board.put(to, checker);
                isJump = true;
                lastMoved = to;
                log.info(getWhoseMoving() + "'s checker " + from + " successfully jumped at position " + to);
            }
            log.info("Move is not possible. Checker " + from + " is not jumped at last move");
        }
        winner = GameUtils.checkWinner(board, currentlyPlaying);
        if (winner != null) {
            log.info("Winner founded: " + winner);
        }
    }

    public void skipMove() {
        log.info("Player " + getWhoseMoving() + " skipped his move");
        currentlyPlaying.offer(currentlyPlaying.poll());
        isJump = false;
    }

    public Queue<Players> getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public Players getWhoseMoving() {
        return currentlyPlaying.peek();
    }

    public Players getWinner() {
        return winner;
    }

    public Checker getCheckerAt(int place) {
        return board.getChecker(place);
    }
}
