package ru.vsu.cs.checkers.structures.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Graph<T> {

    private List<GraphVertex<T>> vEdjLists = new ArrayList<>();
    private int vCount = 0;
    private int eCount = 0;

    private static Iterable<Integer> nullIterable = new Iterable<Integer>() {
        @Override
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Integer next() {
                    return null;
                }
            };
        }
    };


    public int vertexCount() {
        return vCount;
    }


    public int edgeCount() {
        return eCount;
    }


    public void addAdge(int v1, int v2) {
        int maxV = Math.max(v1, v2);

        for (; vCount <= maxV; vCount++) {
            vEdjLists.add(new GraphVertex<>(vCount, null));
        }
        if (!isAdj(v1, v2)) {
            vEdjLists.get(v1).add(vEdjLists.get(v2));
            vEdjLists.get(v2).add(vEdjLists.get(v2));
            eCount++;
        }
    }

    public boolean isAdj(int v1, int v2) {
        for (GraphVertex<T> adj : getVertex(v1).adjacencies()) {
            if (adj.equals(v2)) {
                return true;
            }
        }
        return false;
    }

    public GraphVertex<T> getVertex(int v) {
        return vEdjLists.get(v);
    }
}
