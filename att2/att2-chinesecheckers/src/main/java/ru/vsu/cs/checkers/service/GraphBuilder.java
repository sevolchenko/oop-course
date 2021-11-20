package ru.vsu.cs.checkers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.piece.Checker;
import ru.vsu.cs.checkers.structures.graph.Graph;

import java.util.HashSet;
import java.util.List;

public class GraphBuilder {

    private static final Logger log = LoggerFactory.getLogger(GraphBuilder.class);

    private Graph<Checker> graph;

    public GraphBuilder() {
    }

    public Graph<Checker> build() {
        graph = new Graph<>();
        initSectors();
        connectSectors();
        connectWithoutFormulas();
        connectWithCenter();
        log.info("Build of graph is done.");
        return graph;
    }

    private void initSectors() {
        var other = new HashSet<>(List.of(1, 3, 5, 6, 8, 10));
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

    private void connectSectors() {

        connectHorizontal(0, 6, true);
        connectHorizontal(9, 3, true);
        connectHorizontal(7, 8, false);
        connectHorizontal(11, 10, false);

        connectRightDirected(6, 7, true);
        connectRightDirected(10, 9, true);
        connectRightDirected(8, 2, false);
        connectRightDirected(5, 11, false);

        connectLeftDirected(7, 1, true);
        connectLeftDirected(4, 10, true);
        connectLeftDirected(11, 6, false);
        connectLeftDirected(9, 8, false);
    }

    private void connectHorizontal(int sectorAbove, int sectorUnder, boolean isUnderRight) {
        int addition = isUnderRight ? 0 : 1;

        graph.addAdge(sectorAbove * 10 + 6, 2 + addition, sectorUnder * 10 + 9);
        graph.addAdge(sectorAbove * 10 + 7, 2 + addition, sectorUnder * 10 + 8);
        graph.addAdge(sectorAbove * 10 + 8, 2 + addition, sectorUnder * 10 + 7);
        graph.addAdge(sectorAbove * 10 + 9, 2 + addition, sectorUnder * 10 + 6);

        graph.addAdge(sectorAbove * 10 + 7 - addition, 3 - addition, sectorUnder * 10 + 9 - addition);
        graph.addAdge(sectorAbove * 10 + 8 - addition, 3 - addition, sectorUnder * 10 + 8 - addition);
        graph.addAdge(sectorAbove * 10 + 9 - addition, 3 - addition, sectorUnder * 10 + 7 - addition);
    }

    private void connectRightDirected(int sectorLeft, int sectorRight, boolean isRightUnder) { // Like /
        int addition = isRightUnder ? 0 : 1;

        graph.addAdge(sectorLeft * 10 + 6, 2 - addition, sectorRight * 10);
        graph.addAdge(sectorLeft * 10 + 3, 2 - addition, sectorRight * 10 + 1);
        graph.addAdge(sectorLeft * 10 + 1, 2 - addition, sectorRight * 10 + 3);
        graph.addAdge(sectorLeft * 10, 2 - addition, sectorRight * 10 + 6);

        if (isRightUnder) {
            graph.addAdge(sectorLeft * 10 + 3, 1, sectorRight * 10);
            graph.addAdge(sectorLeft * 10 + 1, 1, sectorRight * 10 + 1);
            graph.addAdge(sectorLeft * 10, 1, sectorRight * 10 + 3);
        } else {
            graph.addAdge(sectorLeft * 10 + 6, 2, sectorRight * 10 + 1);
            graph.addAdge(sectorLeft * 10 + 3, 2, sectorRight * 10 + 3);
            graph.addAdge(sectorLeft * 10 + 1, 2, sectorRight * 10 + 6);
        }
    }

    private void connectLeftDirected(int sectorLeft, int sectorRight, boolean isLeftUnder) { // Like \
        int addition = isLeftUnder ? 0 : 1;

        graph.addAdge(sectorRight * 10 + 9, 3 + addition, sectorLeft * 10);
        graph.addAdge(sectorRight * 10 + 5, 3 + addition, sectorLeft * 10 + 2);
        graph.addAdge(sectorRight * 10 + 2, 3 + addition, sectorLeft * 10 + 5);
        graph.addAdge(sectorRight * 10, 3 + addition, sectorLeft * 10 + 9);

        if (isLeftUnder) {
            graph.addAdge(sectorRight * 10 + 5, 4, sectorLeft * 10);
            graph.addAdge(sectorRight * 10 + 2, 4, sectorLeft * 10 + 2);
            graph.addAdge(sectorRight * 10, 4, sectorLeft * 10 + 5);
        } else {
            graph.addAdge(sectorRight * 10 + 9, 3, sectorLeft * 10 + 2);
            graph.addAdge(sectorRight * 10 + 5, 3, sectorLeft * 10 + 5);
            graph.addAdge(sectorRight * 10 + 2, 3, sectorLeft * 10 + 9);
        }
    }

    private void connectWithCenter() { // fixed
        graph.addAdge(120, 0, 60);
        graph.addAdge(120, 1, 76);
        graph.addAdge(120, 2, 89);
        graph.addAdge(120, 3, 90);
        graph.addAdge(120, 4, 106);
        graph.addAdge(120, 5, 119);
    }

    private void connectWithoutFormulas() { // fixed
        graph.addAdge(6, 3, 110);
        graph.addAdge(19, 4, 66);
        graph.addAdge(20, 5, 79);
        graph.addAdge(36, 0, 80);
        graph.addAdge(49, 1, 96);
        graph.addAdge(50, 2, 109);
    }
}
