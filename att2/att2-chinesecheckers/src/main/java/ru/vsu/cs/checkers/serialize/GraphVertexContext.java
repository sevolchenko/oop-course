package ru.vsu.cs.checkers.serialize;

import ru.vsu.cs.checkers.structures.graph.GraphVertex;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GraphVertexContext<T> {

    private int number;
    private T data;
    private Integer[] adjacencies;

    public int getNumber() {
        return number;
    }

    public T getData() {
        return data;
    }

    public Integer[] getAdjacencies() {
        return adjacencies;
    }

    public GraphVertexContext(int number, T data, GraphVertex<T>[] adjacencies) {
        this.number = number;
        this.data = data;
        this.adjacencies = new Integer[adjacencies.length];
        Arrays.stream(adjacencies)
                .map(x -> {
                    if (x != null) {
                        return x.getNumber();
                    }
                    return null;
                })
                .collect(Collectors.toList())
                .toArray(this.adjacencies);
    }

    public GraphVertexContext() {

    }
}
