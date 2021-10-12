package ru.vsu.cs.checkers.structures.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphVertex<T> {

    public GraphVertex(int number, T data) {
        this.number = number;
        this.data = data;
        this.adjacencies = new ArrayList<>();
    }

    private int number;
    private T data;
    private List<GraphVertex<T>> adjacencies;

    public int getNumber() {
        return number;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Iterable<GraphVertex<T>> adjacencies() {
        return adjacencies;
    }

    public void add(GraphVertex<T> vertex) {
        adjacencies.add(vertex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphVertex<T> other = (GraphVertex<T>) o;
        return number == other.number;
    }

}
