package ru.vsu.cs.checkers.requests;

import ru.vsu.cs.checkers.game.ChineseCheckersGame;
import ru.vsu.cs.checkers.game.ChineseCheckersGameException;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Checker;
import ru.vsu.cs.checkers.structures.graph.GraphException;

public class RequestProcesser {

    private ChineseCheckersGame game;

    public RequestProcesser(ChineseCheckersGame game) {
        this.game = game;
    }

    public void processStart(RequestStart rs) throws ChineseCheckersGameException {
        game.startGame(rs.getCountOfPlayers());
    }

    public void processMove(RequestMove rm) throws GraphException, ChineseCheckersGameException {
        game.move(rm.getFrom(), rm.getTo());
    }

    public void processContinue() {
        game.continueAfterJump();
    }

    public Players getWhoseMoving() {
        return game.getWhoseMoving();
    }

    public Checker getCheckerAt(RequestChecker rc) {
        return game.getCheckerAt(rc.getPlace());
    }

    public Players getWinner() {
        return game.getWinner();
    }
}
