package ru.vsu.cs.checkers.piece;

import ru.vsu.cs.checkers.structures.graph.*;
import ru.vsu.cs.checkers.utils.BoardUtils;
import ru.vsu.cs.checkers.utils.GameUtils;

public class Board {

    Graph<Checker> graph;

    public Board() {
        graph = new Graph<>();
        initBoard();
    }

    private void initBoard() {
        BoardUtils.initSectors(graph);
        BoardUtils.connectSectors(graph);
        BoardUtils.connectWithoutFormulas(graph);
        BoardUtils.connectWithCenter(graph);
    }

    public void put(int place, Checker checker) {
        graph.getVertex(place).setData(checker);
    }

    public void clear(int place) {
        graph.removeData(place);
    }

    public Checker getChecker(int place) {
        return graph.getVertex(place).getData();
    }

    public boolean isConnected(int place1, int place2) {
        return graph.isAdj(place1, place2);
    }

    public boolean isConnectedAcrossOne(int place1, int place2) throws GraphException {
        GraphVertex<Checker> checker1 = graph.getVertex(place1);
        GraphVertex<Checker> checker2 = graph.getVertex(place2);
        for (int i = 0; i < 6; i++) {
            GraphVertex<Checker> connected1 = checker1.connectedOn(i);
            GraphVertex<Checker> connected2 = checker2.connectedOn(GameUtils.getOpposite(i));
            if (connected1 != null && connected2 != null && connected1.getData() != null && connected1.equals(connected2)) {
                return true;
            }
        }
        return false;
    }
}
