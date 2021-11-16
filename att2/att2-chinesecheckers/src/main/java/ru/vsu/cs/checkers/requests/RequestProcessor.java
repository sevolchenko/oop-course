package ru.vsu.cs.checkers.requests;

import ru.vsu.cs.checkers.game.ChineseCheckersGame;
import ru.vsu.cs.checkers.game.GameState;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Checker;
import ru.vsu.cs.checkers.serialize.GameContext;

import java.util.List;

public class RequestProcessor {

    private ChineseCheckersGame game;

    public RequestProcessor(ChineseCheckersGame game) {
        this.game = game;
    }

    public void processStart(RequestStart rs) {
        game.startGame(rs.getCountOfPlayers());
    }

    public void processMove(RequestMove rm) {
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

    public List<Players> getWinner() {
        return game.getWinner();
    }

    public GameState getGameState() {
        return game.getGameState();
    }

    public GameContext getContext() {
        return game.context();
    }

    public void processLoad(RequestLoad rl) {
        game.fromContext(rl.getContext());
    }
}
