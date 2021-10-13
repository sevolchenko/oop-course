package ru.vsu.cs.checkers.utils;

import ru.vsu.cs.checkers.piece.Checker;
import ru.vsu.cs.checkers.structures.graph.Graph;

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
            if (i > 5) {
                graph.addAdge(120, i - 6, 10 * i);
            }
        }
    }

    public static void connectSectors(Graph<Checker> graph) {

        graph.addAdge(6, 2, 69);
        graph.addAdge(6, 3, 110);

        graph.addAdge(99, 2, 36);
        graph.addAdge(80, 3, 36);

        for (int i = 7; i < 10; i++) {
            graph.addAdge(i, 2, 75 - i);
            graph.addAdge(i, 3, 76 - i);

            graph.addAdge(89 + i, 2, 46 - i);
            graph.addAdge(90 + i, 3, 46 - i);
        }


    }

}
