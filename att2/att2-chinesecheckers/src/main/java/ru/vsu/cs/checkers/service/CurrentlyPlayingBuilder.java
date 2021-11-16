package ru.vsu.cs.checkers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.game.Players;

import java.util.ArrayDeque;
import java.util.Queue;

public class CurrentlyPlayingBuilder {

    private static final Logger log = LoggerFactory.getLogger(CurrentlyPlayingBuilder.class);

    public CurrentlyPlayingBuilder() {
    }

    public Queue<Players> build(int countOfPlayers) {
        Queue<Players> currentlyPlaying = new ArrayDeque<>();
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
        return currentlyPlaying;
    }

}
