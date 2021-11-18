package ru.vsu.cs.checkers.display.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.cs.checkers.commandProviders.CommandProvider;
import ru.vsu.cs.checkers.commandProviders.ConsoleCommandProvider;
import ru.vsu.cs.checkers.commandProviders.ScriptedCommandProvider;
import ru.vsu.cs.checkers.game.ChineseCheckersGame;
import ru.vsu.cs.checkers.game.GameState;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Checker;
import ru.vsu.cs.checkers.requests.*;
import ru.vsu.cs.checkers.serialize.GameContext;
import ru.vsu.cs.checkers.utils.DrawingUtils;
import ru.vsu.cs.checkers.utils.FileUtils;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleInterface {

    private static final Logger log = LoggerFactory.getLogger(ConsoleInterface.class);

    private ChineseCheckersGame game = new ChineseCheckersGame();
    private RequestProcessor rp = new RequestProcessor(game);
    private CommandProvider cp;

    Scanner sc = new Scanner(System.in);

    public void begin() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Welcome to Chinese Checkers game!");
        System.out.println("You are in main menu");
        System.out.println("If you want to start game with some players (all the moves will be made by user) type \"game\"");
        System.out.println("If you want to see one simple game (without your ability to influence the game) type \"simulation\"");
        System.out.println("If you want to load saved game type \"load\"");
        System.out.println("If you want to close the application type \"exit\"");
        System.out.println("--------------------------------------------------------------");

        parseBeginInput(sc.nextLine());
    }

    private void parseBeginInput(String message) {
        switch (message) {
            case "game" -> {
                cp = new ConsoleCommandProvider();
                startGame();
                break;
            }
            case "simulation" -> {
                cp = new ScriptedCommandProvider();
                startGame();
                break;
            }
            case "load" -> {
                try {
                    File savesDirectory = FileUtils.getAbsolutePathOfSavesDirectory().toFile();
                    if (!savesDirectory.exists()) {
                        System.out.println("Can not find saves folder");
                        begin();
                    } else {
                        File[] savesList = savesDirectory.listFiles();
                        List<String> names = Arrays.stream(savesList)
                                .map(x -> x.toPath().getFileName().toString())
                                .filter(x -> x.endsWith(".json"))
                                .map(x -> {
                                    String filename = x;
                                    filename = filename.substring(0, filename.lastIndexOf('.'));
                                    return filename;
                                })
                                .collect(Collectors.toList());
                        if (names.size() > 0) {
                            System.out.println("Available saves files: ");
                            names.forEach(x -> System.out.println("    " + x));
                            System.out.println("Type name of save");
                            String filename = sc.nextLine();
                            int index = names.indexOf(filename);
                            if (index != -1) {
                                GameContext gc = GameContext.read(savesList[index]);
                                RequestLoad rl = new RequestLoad(gc);
                                rp.processLoad(rl);
                                cp = new ConsoleCommandProvider();
                                gameProcess();
                            } else {
                                System.out.println("Can not find this file");
                                begin();
                            }
                        } else {
                            System.out.println("Saves folder is empty.");
                            begin();
                        }
                    }
                } catch (IOException e) {
                    log.info("Can not read save file. Error: " + e.getMessage());
                    begin();
                }
                break;
            }
            case "exit" -> {
                System.exit(0);
            }
            default -> {
                System.out.println("Unknown command.");
                begin();
                break;
            }
        }
    }

    private void startGame() {
        game = new ChineseCheckersGame();
        if (cp instanceof ConsoleCommandProvider) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Type count of players");
        }
        int countOfPlayers = Integer.parseInt(cp.getNextLine());
        RequestStart rs = new RequestStart(countOfPlayers);
        rp.processStart(rs);

        gameProcess();
    }

    private void gameProcess() {
        printField();
        printWhoseMoving();
        System.out.println("--------------------------------------------------------------");
        System.out.println("Game started, type \"cmd\" for see available commands");
        System.out.println("--------------------------------------------------------------");
        while (true) {
            String command = cp.getNextLine();
            String[] arr = command.split("(\\s|[,;])+", 2);
            String firstWord = arr[0];
            switch (firstWord) {
                case "move" -> {
                    if (arr.length < 2) {
                        System.out.println("Type from and to parameters");
                        break;
                    }
                    String[] stringIndices = arr[1].split("(\\s|[,;])+", 2);
                    List<Integer> indices = Arrays.stream(stringIndices)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    if (indices.size() != 2) {
                        System.out.println("Wrong count of indices");
                        break;
                    }
                    RequestMove rm = new RequestMove(indices.get(0), indices.get(1));
                    rp.processMove(rm);
                    GameState gameState = rp.getGameState();
                    if (gameState == GameState.WINNER_EXIST) {
                        List<Players> winners = rp.getWinner();
                        System.out.println("--------------------------------------------------------------");
                        if (winners.size() == 1) {
                            System.out.println("Player " + winners.get(0) + " won this game!");
                        } else {
                            System.out.print("Players ");
                            String str = winners.toString();
                            System.out.print(str.substring(1, str.length() - 1));
                            System.out.println(" tied for first place");
                        }
                        System.out.println("--------------------------------------------------------------");
                        System.out.println("Type any symbols to console for continue");
                        sc.nextLine();
                        begin();
                        return;
                    }
                    printField();
                    printWhoseMoving();
                    break;
                }
                case "continue" -> {
                    rp.processContinue();
                    printWhoseMoving();
                    break;
                }
                case "save" -> {
                    try {
                        if (arr.length < 2) {
                            System.out.println("SaveName is not founded");
                            log.info("SaveName is not founded");
                            break;
                        }
                        String saveName = arr[1];
                        GameContext gc = rp.getContext();
                        GameContext.save(saveName, gc);
                    } catch (IOException e) {
                        System.out.println("Failed to save that game.");
                        log.info("Failed to save that game.\n Error: " + e.getMessage());
                    }
                    break;
                }
                case "info" -> {
                    try {
                        Path dir = FileUtils.getAbsolutePathOfCurrentDirectory();
                        File file = new File(dir.toString(), "README.txt");
                        FileOutputStream out = new FileOutputStream(file);
                        InputStream inputStream = FileUtils.getInputStreamFromResources("README.txt");
                        inputStream.transferTo(out);
                        out.close();
                        System.out.println("README file saved to " + file.getAbsolutePath());
                        log.info("README file saved to " + file.getAbsolutePath());
                    } catch (IOException e) {
                        System.out.println("Can not access to README file");
                        log.info("Can not access to README file. Error: " + e.getMessage());
                    }
                    break;
                }
                case "restart" -> {
                    log.info("Game restarted");
                    startGame();
                    break;
                }
                case "end" -> {
                    log.info("Game ended");
                    begin();
                    break;
                }
                case "cmd" -> {
                    System.out.println("--------------------------------------------------------------");
                    System.out.println("Available commands:");
                    System.out.println("    move *int* *int* - move your checker from first position to second");
                    System.out.println("    continue - end jumping");
                    System.out.println("    save *save name* - save current game state");
                    System.out.println("    info - save README file to current directory");
                    System.out.println("    restart - restart game");
                    System.out.println("    end - go to main menu");
                    System.out.println("--------------------------------------------------------------");
                    break;
                }
                default -> {
                    System.out.println("Unknown command. Try again.");
                    break;
                }
            }
        }
    }

    private void printWhoseMoving() {
        Players player = rp.getWhoseMoving();
        System.out.println("--------------------------------------------------------------");
        System.out.println("Now moving " + player);
        System.out.println("--------------------------------------------------------------");
    }


    private void printField() {
        StringBuilder[] rows = new StringBuilder[21];
        StringBuilder[][] sectors = new StringBuilder[12][];
        for (int i = 0; i < 12; i++) {
            sectors[i] = getRowsOfSector(i);
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i] = new StringBuilder();
        }

        rows[0].append("               .");

        rows[1].append("             / ");
        rows[1].append(sectors[0][0]);
        rows[1].append(" \\");

        rows[2].append("            / ");
        rows[2].append(sectors[0][1]);
        rows[2].append(" \\");

        rows[3].append("           / ");
        rows[3].append(sectors[0][2]);
        rows[3].append(" \\");

        rows[4].append("          / ");
        rows[4].append(sectors[0][3]);
        rows[4].append(" \\");

        rows[5].append("-----------         -----------");

        rows[6].append("\\ ");
        rows[6].append(sectors[5][3].reverse());
        rows[6].append("  ");
        rows[6].append(sectors[11][0]);
        rows[6].append(" ");
        rows[6].append(sectors[6][3].reverse());
        rows[6].append("  ");
        rows[6].append(sectors[1][3].reverse());
        rows[6].append(" /");

        rows[7].append(" \\ ");
        rows[7].append(sectors[5][2].reverse());
        rows[7].append("  ");
        rows[7].append(sectors[11][1]);
        rows[7].append(" ");
        rows[7].append(sectors[6][2].reverse());
        rows[7].append(" ");
        rows[7].append(sectors[7][0]);
        rows[7].append("  ");
        rows[7].append(sectors[1][2].reverse());
        rows[7].append(" /");

        rows[8].append("  \\ ");
        rows[8].append(sectors[5][1].reverse());
        rows[8].append("  ");
        rows[8].append(sectors[11][2]);
        rows[8].append(" ");
        rows[8].append(sectors[6][1].reverse());
        rows[8].append(" ");
        rows[8].append(sectors[7][1]);
        rows[8].append("  ");
        rows[8].append(sectors[1][1].reverse());
        rows[8].append(" /");

        rows[9].append("   \\ ");
        rows[9].append(sectors[5][0].reverse());
        rows[9].append("  ");
        rows[9].append(sectors[11][3]);
        rows[9].append(" ");
        rows[9].append(sectors[6][0].reverse());
        rows[9].append(" ");
        rows[9].append(sectors[7][2]);
        rows[9].append("  ");
        rows[9].append(sectors[1][0].reverse());
        rows[9].append(" /");

        rows[10].append("    |  ");
        rows[10].append(sectors[10][3].reverse());
        rows[10].append(" ");
        RequestChecker rc = new RequestChecker(120);
        rows[10].append(DrawingUtils.getChar(rp.getCheckerAt(rc)));
        rows[10].append(" ");
        rows[10].append(sectors[7][3]);
        rows[10].append("  |");

        rows[11].append("   / ");
        rows[11].append(sectors[4][0]);
        rows[11].append("  ");
        rows[11].append(sectors[10][2].reverse());
        rows[11].append(" ");
        rows[11].append(sectors[9][0]);
        rows[11].append(" ");
        rows[11].append(sectors[8][3].reverse());
        rows[11].append("  ");
        rows[11].append(sectors[2][0]);
        rows[11].append(" \\");

        rows[12].append("  / ");
        rows[12].append(sectors[4][1]);
        rows[12].append("  ");
        rows[12].append(sectors[10][1].reverse());
        rows[12].append(" ");
        rows[12].append(sectors[9][1]);
        rows[12].append(" ");
        rows[12].append(sectors[8][2].reverse());
        rows[12].append("  ");
        rows[12].append(sectors[2][1]);
        rows[12].append(" \\");

        rows[13].append(" / ");
        rows[13].append(sectors[4][2]);
        rows[13].append("  ");
        rows[13].append(sectors[10][0].reverse());
        rows[13].append(" ");
        rows[13].append(sectors[9][2]);
        rows[13].append(" ");
        rows[13].append(sectors[8][1].reverse());
        rows[13].append("  ");
        rows[13].append(sectors[2][2]);
        rows[13].append(" \\");

        rows[14].append("/ ");
        rows[14].append(sectors[4][3]);
        rows[14].append("  ");
        rows[14].append(sectors[9][3]);
        rows[14].append(" ");
        rows[14].append(sectors[8][0].reverse());
        rows[14].append("  ");
        rows[14].append(sectors[2][3]);
        rows[14].append(" \\");

        rows[15].append("-----------          ----------");

        rows[16].append("          \\ ");
        rows[16].append(sectors[3][3].reverse());
        rows[16].append(" /");

        rows[17].append("           \\ ");
        rows[17].append(sectors[3][2].reverse());
        rows[17].append(" /");

        rows[18].append("            \\ ");
        rows[18].append(sectors[3][1].reverse());
        rows[18].append(" /");

        rows[19].append("             \\ ");
        rows[19].append(sectors[3][0].reverse());
        rows[19].append(" /");

        rows[20].append("               .");


        System.out.println();
        for (int i = 0; i < rows.length; i++) {
            System.out.println(rows[i]);
        }
        System.out.println();

    }

    private StringBuilder[] getRowsOfSector(int sector) {
        StringBuilder[] str = new StringBuilder[4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            str[i] = new StringBuilder();
            for (int j = 0; j <= i; j++) {
                RequestChecker rc = new RequestChecker(sector * 10 + index);
                Checker checker = rp.getCheckerAt(rc);
                if (j > 0) {
                    str[i].append(" ");
                }
                str[i].append(DrawingUtils.getChar(checker));
                index++;
            }
        }
        return str;
    }

}