package ru.vsu.cs.checkers.serialize;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.game.GameState;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Board;
import ru.vsu.cs.checkers.service.WinnerChecker;
import ru.vsu.cs.checkers.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Queue;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GameContext {

    private static final Logger log = LoggerFactory.getLogger(GameContext.class);

    private GameState gameState;
    private Queue<Players> currentlyPlaying;
    private BoardContext board;

    private boolean jump;
    private int lastMoved;

    public GameState getGameState() {
        return gameState;
    }

    public Queue<Players> getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public BoardContext getBoard() {
        return board;
    }

    public boolean isJump() {
        return jump;
    }

    public int getLastMoved() {
        return lastMoved;
    }

    public GameContext(GameState gameState, Queue<Players> currentlyPlaying, Board board, boolean isJump, int lastMoved, WinnerChecker wc) {
        this.gameState = gameState;
        this.currentlyPlaying = currentlyPlaying;
        this.board = board.context();
        this.jump = isJump;
        this.lastMoved = lastMoved;
    }

    public GameContext() {

    }

    public void save(String saveName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        File savesDir = FileUtils.getAbsolutePathOfSavesDirectory().toFile();
        if (savesDir.mkdir()) {
            log.info("Saves directory " + savesDir.getAbsolutePath() + " was made");
        }
        File save = new File(savesDir.getAbsolutePath(), saveName + ".json");
        if (save.createNewFile()) {
            log.info("Save file " + save.getAbsolutePath() + " was made");
        }
        objectMapper.writeValue(save, this);
    }

    public static GameContext read(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        return objectMapper.readValue(file, GameContext.class);
    }

}
