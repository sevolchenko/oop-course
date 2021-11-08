package ru.vsu.cs.checkers.requests;

public class RequestStart {

    private int countOfPlayers;

    public RequestStart(int countOfPlayers) {
        this.countOfPlayers = countOfPlayers;
    }

    public int getCountOfPlayers() {
        return countOfPlayers;
    }
}
