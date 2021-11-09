package ru.vsu.cs.checkers;

import org.slf4j.*;
import ru.vsu.cs.checkers.display.console.ConsoleInterface;

public class Main {

    public static void main(String[] args) {
        log.info("Application started.");

        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.begin();
    }

    private static final Logger log = LoggerFactory.getLogger(Main.class);
}
