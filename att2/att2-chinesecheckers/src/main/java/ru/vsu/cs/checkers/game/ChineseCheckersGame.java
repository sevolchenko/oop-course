package ru.vsu.cs.checkers.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.piece.*;
import ru.vsu.cs.checkers.serialize.GameContext;
import ru.vsu.cs.checkers.service.BoardBuilder;
import ru.vsu.cs.checkers.service.CheckersPutter;
import ru.vsu.cs.checkers.service.CurrentlyPlayingBuilder;
import ru.vsu.cs.checkers.service.WinnerChecker;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

public class ChineseCheckersGame {

    private static final Logger log = LoggerFactory.getLogger(ChineseCheckersGame.class);

    private GameState gameState;
    private Deque<Players> currentlyPlaying;
    private Board board;

    private boolean isJump = false;
    private int lastMoved = -1;

    private WinnerChecker wc;

    public ChineseCheckersGame() {
        gameState = GameState.INITIALIZED;
        log.info("Game initialized.");
    }

    public void startGame(int countOfPlayers) {
        board = new BoardBuilder().build();
        currentlyPlaying = new CurrentlyPlayingBuilder().build(countOfPlayers);
        new CheckersPutter().put(board, currentlyPlaying);
        wc = new WinnerChecker(board, currentlyPlaying);
        gameState = GameState.PLAYING;
        log.info("Game started.");
    }


    public void move (int from, int to) {
        if (from < 0 || to < 0 || from > 120 || to > 120) {
            log.error("Invalid indices of cells: " + from + ", " + to);
            return;
        }

        Checker checker = board.getChecker(from);

        if (checker == null) {
            log.info("Move is not possible. Place " + from + " is empty.");
            return;
        }
        if (checker.getOwner() != getWhoseMoving()) {
            log.info("Move is not possible. Checker's owner is not " + getWhoseMoving());
            return;
        }

        boolean connected = board.isConnected(from, to);
        boolean acrossOne = board.isConnectedAcrossOne(from, to);

        if ((!isJump && connected) || ((!isJump || (isJump && from == lastMoved)) && acrossOne)) {
            board.clear(from);
            board.put(to, checker);
            lastMoved = to;
            log.info(getWhoseMoving() + "'s checker " + from + " successfully moved at position " + to);
            if (connected) {
                nextMoving();
            } else if (acrossOne){
                isJump = true;
            }
        } else {
            log.info("Move from " + from + " to " + to + " is not possible.");
        }
    }

    private void checkWinner() {
        List<Players> winners = wc.check();
        if (winners.size() == 0) {
            log.info("Winner not founded.");
        } else {
            if (winners.size() == 1) {
                Players winner = winners.get(0);
                log.info("Winner founded: " + winner);
            }
            if (winners.size() > 1) {
                log.info("A lot of winners founded.");
            }
            gameState = GameState.WINNER_EXIST;
        }
    }

    public void continueAfterJump() {
        if (isJump) {
            nextMoving();
            log.info("Player " + getWhoseMoving() + " ended jumps");
        } else  {
            log.info("Player " + getWhoseMoving() + " can't continue");
        }
    }

    public void nextMoving() {
        if (currentlyPlaying.peekLast() == Players.DARK) {
            checkWinner();
        }
        currentlyPlaying.offer(currentlyPlaying.poll());
        isJump = false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Players getWhoseMoving() {
        return currentlyPlaying.peek();
    }

    public List<Players> getWinner() {
        return wc.check();
    }

    public Checker getCheckerAt(int place) {
        return board.getChecker(place);
    }

    public GameContext context() {
        return new GameContext(gameState, currentlyPlaying, board, isJump, lastMoved);
    }

    public void fromContext(GameContext gc) {
        gameState = gc.getGameState();
        currentlyPlaying = gc.getCurrentlyPlaying();
        board = new Board();
        board.fromContext(gc.getBoard());
        isJump = gc.isJump();
        lastMoved = gc.getLastMoved();
        wc =  new WinnerChecker(board, currentlyPlaying);
    }
}
