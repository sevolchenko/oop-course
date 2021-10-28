package ru.vsu.cs.checkers.requests;

public class RequestMove {

    private int from;
    private int to;

    public RequestMove(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
