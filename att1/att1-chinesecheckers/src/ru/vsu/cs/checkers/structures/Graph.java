package ru.vsu.cs.checkers.structures;

import java.util.Arrays;
import java.util.Iterator;


public class Graph<T> {

    private boolean[][] adjMatrix = null;
    private GraphVertex<T>[] vertices = null;
    private int vCount = 0;
    private int eCount = 0;

    /**
     * Конструктор
     * @param vertexCount Кол-во вершин графа (может увеличиваться при добавлении ребер)
     */
    public Graph(int vertexCount) {
        adjMatrix = new boolean[vertexCount][vertexCount];
        vertices = new GraphVertex[vertexCount];
        vCount = vertexCount;
    }

    /**
     * Конструктор без парметров
     * (лучше не использовать, т.к. при добавлении вершин каждый раз пересоздается матрица)
     */
    public Graph() {
        this(0);
    }


    public int vertexCount() {
        return vCount;
    }


    public int edgeCount() {
        return eCount;
    }


    public void addAdge(int v1, int v2) {
        if (!adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = true;
            adjMatrix[v2][v1] = true;
            eCount++;
        }
    }


    public void removeAdge(int v1, int v2) {
        if (adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = false;
            adjMatrix[v2][v1] = false;
            eCount--;
        }
    }


    public Iterable<GraphVertex<T>> adjacencies(int v) {
        return new Iterable<GraphVertex<T>>() {
            GraphVertex<T> nextAdj = null;

            @Override
            public Iterator<GraphVertex<T>> iterator() {
                for (int i = 0; i < vCount; i++) {
                    if (adjMatrix[v][i]) {
                        nextAdj = vertices[i];
                        break;
                    }
                }

                return new Iterator<GraphVertex<T>>() {
                    @Override
                    public boolean hasNext() {
                        return nextAdj != null;
                    }

                    @Override
                    public GraphVertex<T> next() {
                        GraphVertex<T> result = nextAdj;
                        nextAdj = null;
                        for (int i = result.getNumber() + 1; i < vCount; i++) {
                            if (adjMatrix[v][i]) {
                                nextAdj = vertices[i];
                                break;
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }

    public boolean isAdj(int v1, int v2) {
        return adjMatrix[v1][v2];
    }
}
