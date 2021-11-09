package ru.vsu.cs.checkers.structures.graph;

import java.util.Arrays;

public class GraphVertex<T> {

    public GraphVertex(int number, T data) {
        this.number = number;
        this.data = data;
        this.adjacencies = new GraphVertex[6];
    }

    private int number;
    private T data;
    private GraphVertex<T>[] adjacencies;

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

        return Arrays.asList(adjacencies);
    }

    public GraphVertex<T> connectedOn(int direction) throws GraphException {
        if (direction < 0 || direction > 5) {
            throw new GraphException("Wrong direction " + direction);
        }
        return adjacencies[direction];
    }

    public void add(GraphVertex<T> vertex, int direction) {

        adjacencies[direction] = vertex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphVertex<T> other = (GraphVertex<T>) o;
        return number == other.number;
    }

}
