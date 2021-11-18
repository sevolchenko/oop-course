package ru.vsu.cs.checkers.piece;

import ru.vsu.cs.checkers.game.Players;

public class Checker {

    private Players owner;

    public void setOwner(Players owner) {
        this.owner = owner;
    }

    public Checker() {

    }

    public Checker(Players owner) {
        this.owner = owner;
    }

    public Players getOwner() {
        return owner;
    }
}
