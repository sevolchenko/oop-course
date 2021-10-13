package ru.vsu.cs.checkers.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.piece.*;
import ru.vsu.cs.checkers.requests.Request;
import ru.vsu.cs.checkers.structures.graph.GraphException;
import ru.vsu.cs.checkers.utils.GameUtils;

import java.util.ArrayList;
import java.util.List;

public class ChineseCheckersGame {

    private static final Logger log = LoggerFactory.getLogger(ChineseCheckersGame.class);

    private List<Players> currentlyPlaying;
    private Board board;
    private List<Checker> checkers;

    private Players whoseMoving;
    private boolean isJump;
    private Players winner;

    private GameStates gameState;

    public ChineseCheckersGame() {
        List<Players> currentlyPlaying = new ArrayList<>();
        checkers = new ArrayList<>();
        board = new Board();
        gameState = GameStates.WAITING_FOR_BEGIN;
        log.info("Game initialized.");
    }

    public void startGame(int countOfPlayers) throws ChineseCheckersGameException {
        GameUtils.fillPlayers(countOfPlayers, currentlyPlaying);
        GameUtils.initCheckersForGame(currentlyPlaying, checkers);
        GameUtils.putCheckersOnTheirPlaces(board, checkers);
        whoseMoving = currentlyPlaying.get(0);
        gameState = GameStates.PLAYING;
        log.info("Game started.");
    }

    public void move(int from, int to) throws ChineseCheckersGameException, GraphException {
        if (from < 0 || to < 0 || from > 120 || to > 120) {
            throw new ChineseCheckersGameException("Invalid indices of cells: " + from + ", " + to);
        }
        Checker checker = board.getChecker(from);
        if (checker == null || checker.getOwner() != whoseMoving) {
            log.info("Move is not possible. Place " + from + " is empty or checker's owner is not " + whoseMoving);
            return;
        }
        if (!isJump && board.isConnected(from, to) && board.getChecker(to) == null) {
            board.clear(from);
            board.put(to, checker);
            whoseMoving = GameUtils.nextMoving(currentlyPlaying, whoseMoving);
            log.info(whoseMoving + "'s checker " + from + " successfully moved at position " + to);
        } else if (board.isConnectedAcrossOne(from, to)) {
            board.clear(from);
            board.put(to, checker);
            isJump = true;
            log.info(whoseMoving + "'s checker " + from + " successfully jumped at position " + to);
        }
        winner = GameUtils.checkWinner(board, currentlyPlaying);
        if (winner != null) {
            gameState = GameStates.WINNER_EXIST;
            log.info("Winner founded: " + winner);
        }
    }

    public void skipMove() {
        log.info("Player " + whoseMoving + " skipped his move");
        whoseMoving = GameUtils.nextMoving(currentlyPlaying, whoseMoving);
        isJump = false;
    }

    public List<Players> getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public GameStates getGameState() {
        return gameState;
    }

    public Players getWhoseMoving() {
        return whoseMoving;
    }

    public Players getWinner() {
        return winner;
    }

    public Checker getCheckerAt(int place) {
        return board.getChecker(place);
    }
}
