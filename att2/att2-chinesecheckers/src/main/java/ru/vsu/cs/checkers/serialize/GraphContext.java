package ru.vsu.cs.checkers.serialize;

import ru.vsu.cs.checkers.structures.graph.Graph;
import ru.vsu.cs.checkers.structures.graph.GraphVertex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphContext<T> {

    private List<GraphVertexContext<T>> vEdjLists;
    private int vCount;
    private int eCount;

    public GraphContext(List<GraphVertex<T>> vEdjLists, int vCount, int eCount) {
        this.vEdjLists = new ArrayList<>();
        for (GraphVertex<T> vertex : vEdjLists) {
            GraphVertexContext<T> vertexContext = vertex.context();
            this.vEdjLists.add(vertexContext);
        }
        this.vCount = vCount;
        this.eCount = eCount;
    }

    public GraphContext() {

    }

    public List<GraphVertexContext<T>> getvEdjLists() {
        return vEdjLists;
    }

    public int getvCount() {
        return vCount;
    }

    public int geteCount() {
        return eCount;
    }
}
