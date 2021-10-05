package ru.vsu.cs.checkers.piece;

import ru.vsu.cs.checkers.structures.graph.*;
import ru.vsu.cs.checkers.utils.MathUtils;

public class Board {

    private final int DEFAULT_TRIANGLE_SIZE = 4; // todo: перенести в класс игры

    Graph<Checker> graph;

    public Board(int size) {
        int triangleSquare = MathUtils.sum(size);
        int countOfCells = triangleSquare * 2 * 6 + 1;
        graph = new Graph<>(countOfCells);
    }

}
