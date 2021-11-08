package ru.vsu.cs.checkers;

import org.slf4j.*;
import ru.vsu.cs.checkers.display.console.ConsoleInterface;
import ru.vsu.cs.checkers.game.ChineseCheckersGameException;
import ru.vsu.cs.checkers.structures.graph.GraphException;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws ChineseCheckersGameException, InterruptedException, GraphException, FileNotFoundException {
        log.info("Application started.");

        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.begin();
    }

    private static final Logger log = LoggerFactory.getLogger(Main.class);
}
