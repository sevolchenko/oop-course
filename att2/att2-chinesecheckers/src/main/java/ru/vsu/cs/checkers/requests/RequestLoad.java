package ru.vsu.cs.checkers.requests;

import ru.vsu.cs.checkers.serialize.GameContext;

public class RequestLoad {

    private GameContext context;

    public RequestLoad (GameContext context) {
        this.context = context;
    }

    public GameContext getContext() {
        return context;
    }
}
