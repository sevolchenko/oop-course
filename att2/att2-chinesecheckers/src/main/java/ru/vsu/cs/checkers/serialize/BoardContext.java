package ru.vsu.cs.checkers.serialize;

import ru.vsu.cs.checkers.piece.Checker;
import ru.vsu.cs.checkers.structures.graph.Graph;

public class BoardContext {

    private GraphContext<Checker> field;

    public BoardContext(Graph<Checker> field) {
        this.field = field.context();
    }

    public BoardContext() {
    }

    public GraphContext<Checker> getField() {
        return field;
    }
}
