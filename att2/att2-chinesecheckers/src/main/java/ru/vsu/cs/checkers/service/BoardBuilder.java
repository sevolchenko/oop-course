package ru.vsu.cs.checkers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.piece.Board;

public class BoardBuilder {

    private static final Logger log = LoggerFactory.getLogger(BoardBuilder.class);

    public BoardBuilder() {
    }

    public Board build() {
        Board board = new Board();
        log.info("Board built.");
        return board;
    }

}
