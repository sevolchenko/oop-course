package ru.vsu.cs.checkers.structures.graph;

import ru.vsu.cs.checkers.serialize.GraphContext;
import ru.vsu.cs.checkers.serialize.GraphVertexContext;
import ru.vsu.cs.checkers.utils.GameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Graph<T> {

    private List<GraphVertex<T>> vEdjLists = new ArrayList<>();
    private int vCount = 0;
    private int eCount = 0;


    public int vertexCount() {
        return vCount;
    }


    public int edgeCount() {
        return eCount;
    }


    public void addAdge(int v1, int direction, int v2) {
        int maxV = Math.max(v1, v2);

        for (; vCount <= maxV; vCount++) {
            vEdjLists.add(new GraphVertex<>(vCount, null));
        }
        if (!isAdj(v1, v2)) {
            vEdjLists.get(v1).add(vEdjLists.get(v2), direction);
            vEdjLists.get(v2).add(vEdjLists.get(v1), GameUtils.getOppositeDirection(direction));
            eCount++;
        }
    }

    public boolean isAdj(int v1, int v2) {
        for (GraphVertex<T> adj : getVertex(v1).adjacencies()) {
            if (adj != null && adj.getNumber() == v2) {
                return true;
            }
        }
        return false;
    }

    public T removeData(int v) {
        T data = vEdjLists.get(v).getData();
        vEdjLists.get(v).setData(null);
        return data;
    }

    public GraphVertex<T> getVertex(int v) {
        return vEdjLists.get(v);
    }

    public GraphContext<T> context() {
        return new GraphContext<>(vEdjLists, vCount, eCount);
    }

    public void fromContext(GraphContext<T> context) {
        vCount = context.getvCount();
        eCount = context.geteCount();
        int i = 0;
        for (GraphVertexContext<T> vertexContext: context.getvEdjLists()) {
            GraphVertex<T> vertex = new GraphVertex<>(vertexContext.getNumber(), vertexContext.getData());
            int j = 0;
            for (Integer connected : vertexContext.getAdjacencies()) {
                if (connected != null) {
                    addAdge(i, j, connected);
                }
                j++;
            }
            vEdjLists.add(vertex);
            i++;
        }
    }
}
