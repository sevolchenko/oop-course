package ru.vsu.cs.checkers.game;

public enum Players {

    BLACK(0),
    BLUE(1),
    RED(2),
    WHITE(3),
    GREEN(4),
    YELLOW(5);

    private final int sector;

    private Players(int sector) {
        this.sector = sector;
    }

    public int getSector() {
        return sector;
    }
}
