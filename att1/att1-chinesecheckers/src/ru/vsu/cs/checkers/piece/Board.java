package ru.vsu.cs.checkers.piece;

import ru.vsu.cs.checkers.structures.graph.*;

public class Board {

    Graph<Checker> graph;

    public Board() {
        graph = new Graph<>();
        initBoard();
    }

    private void initBoard() {
        //todo: сделать добавление всех нужных связей
    }

    public void put(int place, Checker checker) {
        graph.getVertex(place).setData(checker);
    }

    public Checker getChecker(int place) {
        return graph.getVertex(place).getData();
    }

}
