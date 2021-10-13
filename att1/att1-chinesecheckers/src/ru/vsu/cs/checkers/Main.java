package ru.vsu.cs.checkers;

import javafx.application.*;
import org.slf4j.*;
import ru.vsu.cs.checkers.drawing.Graphics;

public class Main {

    public static void main(String[] args) {
        Application.launch(Graphics.class);
        log.info("Application started.");
    }

    private static final Logger log = LoggerFactory.getLogger(Main.class);
}
