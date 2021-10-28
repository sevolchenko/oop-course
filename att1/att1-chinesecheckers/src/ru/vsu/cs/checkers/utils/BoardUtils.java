package ru.vsu.cs.checkers.utils;

import ru.vsu.cs.checkers.piece.Checker;
import ru.vsu.cs.checkers.structures.graph.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoardUtils {

    public static void initSectors(Graph<Checker> graph) {
        Set<Integer> other = new HashSet(List.of(1, 3, 5, 6, 8, 10));
        for (int i = 0; i < 12; i++) {
            if (!other.contains(i)) {
                graph.addAdge(10 * i, 2, 10 * i + 2);
                graph.addAdge(10 * i, 3, 10 * i + 1);

                graph.addAdge(10 * i + 1, 1, 10 * i + 2);
                graph.addAdge(10 * i + 1, 2, 10 * i + 4);
                graph.addAdge(10 * i + 1, 3, 10 * i + 3);

                graph.addAdge(10 * i + 2, 2, 10 * i + 5);
                graph.addAdge(10 * i + 2, 3, 10 * i + 4);

                graph.addAdge(10 * i + 3, 1, 10 * i + 4);
                graph.addAdge(10 * i + 3, 2, 10 * i + 7);
                graph.addAdge(10 * i + 3, 3, 10 * i + 6);

                graph.addAdge(10 * i + 4, 1, 10 * i + 5);
                graph.addAdge(10 * i + 4, 2, 10 * i + 8);
                graph.addAdge(10 * i + 4, 3, 10 * i + 7);

                graph.addAdge(10 * i + 5, 2, 10 * i + 9);
                graph.addAdge(10 * i + 5, 3, 10 * i + 8);

                graph.addAdge(10 * i + 6, 1, 10 * i + 7);

                graph.addAdge(10 * i + 7, 1, 10 * i + 8);

                graph.addAdge(10 * i + 8, 1, 10 * i + 9);

            } else {

                graph.addAdge(10 * i, 5, 10 * i + 2);
                graph.addAdge(10 * i, 0, 10 * i + 1);

                graph.addAdge(10 * i + 1, 4, 10 * i + 2);
                graph.addAdge(10 * i + 1, 5, 10 * i + 4);
                graph.addAdge(10 * i + 1, 0, 10 * i + 3);

                graph.addAdge(10 * i + 2, 5, 10 * i + 5);
                graph.addAdge(10 * i + 2, 0, 10 * i + 4);

                graph.addAdge(10 * i + 3, 4, 10 * i + 4);
                graph.addAdge(10 * i + 3, 5, 10 * i + 7);
                graph.addAdge(10 * i + 3, 0, 10 * i + 6);

                graph.addAdge(10 * i + 4, 4, 10 * i + 5);
                graph.addAdge(10 * i + 4, 5, 10 * i + 8);
                graph.addAdge(10 * i + 4, 0, 10 * i + 7);

                graph.addAdge(10 * i + 5, 5, 10 * i + 9);
                graph.addAdge(10 * i + 5, 0, 10 * i + 8);

                graph.addAdge(10 * i + 6, 4, 10 * i + 7);

                graph.addAdge(10 * i + 7, 4, 10 * i + 8);

                graph.addAdge(10 * i + 8, 4, 10 * i + 9);
            }
        }
    }

    public static void connectSectors(Graph<Checker> graph) {

        connectHorizontal(graph, 0, 6);
        connectHorizontal(graph, 9, 3);
        connectHorizontal(graph, 7, 8);
        connectHorizontal(graph, 11, 10);

        connectRightDirected(graph, 6, 7);
        connectRightDirected(graph, 8, 2);
        connectRightDirected(graph, 10, 9);
        connectRightDirected(graph, 5, 11);

        connectLeftDirected(graph, 7, 1);
        connectLeftDirected(graph, 11, 6);
        connectLeftDirected(graph, 4, 10);
        connectLeftDirected(graph, 9, 8);
    }

    private static void connectHorizontal(Graph<Checker> graph, int sectorAbove, int sectorUnder) {
        graph.addAdge(10 * sectorAbove + 6, 2, 10 * sectorUnder + 9);
        for (int i = 0; i < 3; i++) {
            graph.addAdge(sectorAbove * 10 + 7 + i, 3, sectorUnder * 10 + 9 - i);
            graph.addAdge(sectorAbove * 10 + 7 + i, 2, sectorUnder * 10 + 8 - i);
        }
    }

    private static void connectRightDirected(Graph<Checker> graph, int sectorLeft, int sectorRight) { // Like /
        List<Integer> list = new ArrayList<>(List.of(0, 1, 3, 6));
        int indexLeft = 2;
        int indexRight = 0;

        graph.addAdge(10 * sectorLeft + 6, 2, 10 * sectorRight);
        for (int i = 0; i < 3; i++) {
            graph.addAdge(sectorLeft * 10 + list.get(indexLeft), 1, sectorRight * 10 + list.get(indexRight));
            graph.addAdge(sectorLeft * 10 + list.get(indexLeft), 2, sectorRight * 10 + list.get(indexRight + 1));
            indexLeft--;
            indexRight++;
        }
    }

    private static void connectLeftDirected(Graph<Checker> graph, int sectorLeft, int sectorRight) { // Like \
        List<Integer> list = new ArrayList<>(List.of(0, 2, 5, 9));
        int indexLeft = 1;
        int indexRight = 3;

        graph.addAdge(10 * sectorLeft, 1, 10 * sectorRight + 9);
        for (int i = 0; i < 3; i++) {
            graph.addAdge(sectorLeft * 10 + list.get(indexLeft), 0, sectorRight * 10 + list.get(indexRight));
            graph.addAdge(sectorLeft * 10 + list.get(indexLeft), 1, sectorRight * 10 + list.get(indexRight - 1));
            indexLeft++;
            indexRight--;
        }
    }

    public static void connectWithCenter(Graph<Checker> graph) {
        graph.addAdge(120, 0, 60);
        graph.addAdge(120, 1, 76);
        graph.addAdge(120, 2, 89);
        graph.addAdge(120, 3, 90);
        graph.addAdge(120, 4, 106);
        graph.addAdge(120, 5, 119);
    }

    public static void connectWithoutFormulas(Graph<Checker> graph) {
        graph.addAdge(6, 3, 110);
        graph.addAdge(16, 4, 66);
        graph.addAdge(20, 5, 76);
        graph.addAdge(36, 0, 89);
        graph.addAdge(49, 1, 96);
        graph.addAdge(50, 2, 109);
    }

}
