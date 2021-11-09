package ru.vsu.cs.checkers.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Board;
import ru.vsu.cs.checkers.piece.Checker;

import java.util.Queue;

public class GameBuilder {

    private static final Logger log = LoggerFactory.getLogger(GameBuilder.class);

    private Queue<Players> currentlyPlaying;
    private Board board;
    private int countOfPlayers;

    public GameBuilder(Queue<Players> currentlyPlaying, Board board, int countOfPlayers) {
        this.currentlyPlaying = currentlyPlaying;
        this.board = board;
        this.countOfPlayers = countOfPlayers;
    }

    public void build() {
        fillPlayers();
        putCheckersOnTheirPlaces();
    }

    private void fillPlayers() {
        currentlyPlaying.offer(Players.DARK);
        switch (countOfPlayers) {
            case 2: {
                currentlyPlaying.offer(Players.WHITE);
                break;
            }
            case 3: {
                currentlyPlaying.offer(Players.RED);
                currentlyPlaying.offer(Players.GREEN);
                break;
            }
            case 4: {
                currentlyPlaying.offer(Players.BLUE);
                currentlyPlaying.offer(Players.WHITE);
                currentlyPlaying.offer(Players.GREEN);
                break;
            }
            case 6: {
                currentlyPlaying.offer(Players.BLUE);
                currentlyPlaying.offer(Players.RED);
                currentlyPlaying.offer(Players.WHITE);
                currentlyPlaying.offer(Players.GREEN);
                currentlyPlaying.offer(Players.YELLOW);
                break;
            }
            default: {
                log.error("Wrong count of players. Expected 2, 3, 4, 6 but found " + countOfPlayers);
                System.exit(2);
            }
        }
        log.info("Players initialized: " + currentlyPlaying.toString());
    }

    public void putCheckersOnTheirPlaces() {
        for (Players player : currentlyPlaying) {
            int sector = player.getSector();
            for (int i = 0; i < 10; i++) {
                board.put(10 * sector + i, new Checker(player));
            }
        }
        log.info("Checkers staying on their places");
    }
}
