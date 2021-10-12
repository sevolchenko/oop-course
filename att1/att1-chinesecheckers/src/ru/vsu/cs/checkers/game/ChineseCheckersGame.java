package ru.vsu.cs.checkers.game;

import ru.vsu.cs.checkers.piece.*;
import ru.vsu.cs.checkers.utils.GameUtils;

import java.util.ArrayList;
import java.util.List;

public class ChineseCheckersGame {

    private List<Players> currentlyPlaying;
    private Board board;
    private List<Checker> checkers;

    private Players whoseMoving;

    public ChineseCheckersGame(int countOfPlayers) throws ChineseCheckersGameException {
        List<Players> currentlyPlaying = new ArrayList<>();
        GameUtils.fillPlayers(countOfPlayers, currentlyPlaying);
        checkers = new ArrayList<>();
        GameUtils.initCheckersForGame(currentlyPlaying, checkers);
        board = new Board();
        GameUtils.putCheckersOnTheirPlaces(board, checkers);
        whoseMoving = currentlyPlaying.get(0);
    }
}
