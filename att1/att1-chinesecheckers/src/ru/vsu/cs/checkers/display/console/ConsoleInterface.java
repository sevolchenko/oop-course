package ru.vsu.cs.checkers.display.console;

import ru.vsu.cs.checkers.commandProviders.CommandProvider;
import ru.vsu.cs.checkers.commandProviders.ConsoleCommandProvider;
import ru.vsu.cs.checkers.commandProviders.ScriptedCommandProvider;
import ru.vsu.cs.checkers.game.ChineseCheckersGame;
import ru.vsu.cs.checkers.game.ChineseCheckersGameException;
import ru.vsu.cs.checkers.game.Players;
import ru.vsu.cs.checkers.piece.Checker;
import ru.vsu.cs.checkers.requests.*;
import ru.vsu.cs.checkers.structures.graph.GraphException;
import ru.vsu.cs.checkers.utils.ArrayUtils;
import ru.vsu.cs.checkers.utils.DrawingUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ConsoleInterface {

    private ChineseCheckersGame game = new ChineseCheckersGame();
    private RequestProcesser rp = new RequestProcesser(game);
    private CommandProvider cp;

    Scanner sc = new Scanner(System.in);

    public void begin() throws FileNotFoundException, ChineseCheckersGameException, GraphException, InterruptedException {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Welcome to Chinese Checkers game!");
        System.out.println("You are in main menu");
        System.out.println("If you want to start game with some players (all the moves will be made by user) type \"game\"");
        System.out.println("If you want to see one simple game (without your ability to influence the game) type \"simulation\"");
        System.out.println("If you want to close the application type \"exit\"");
        System.out.println("--------------------------------------------------------------");

        parseBeginInput(sc.nextLine());
    }

    private void parseBeginInput(String message) throws FileNotFoundException, ChineseCheckersGameException, GraphException, InterruptedException {
        switch (message) {
            case "simulation" : {
                System.out.println("Type full path of source file");
                String path = sc.nextLine();
                cp = new ScriptedCommandProvider(new File(path));
                startGame();
                break;
            }
            case "game" : {
                cp = new ConsoleCommandProvider();
                startGame();
                break;
            }
            case "exit" : {
                System.exit(0);
            }
        }
    }

    private void startGame() throws ChineseCheckersGameException, GraphException, FileNotFoundException, InterruptedException {
        game = new ChineseCheckersGame();
        if (cp.getClass().equals(ConsoleCommandProvider.class)) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Type count of players");
        }
        int countOfPlayers = Integer.parseInt(cp.getNextLine());
        RequestStart rs = new RequestStart(countOfPlayers);
        rp.processStart(rs);

        gameProcess();
    }

    private void gameProcess() throws GraphException, ChineseCheckersGameException, FileNotFoundException, InterruptedException {
        printField();
        printWhoseMoving();
        System.out.println("--------------------------------------------------------------");
        System.out.println("Game started, type \"help\" for see available commands");
        System.out.println("--------------------------------------------------------------");
        while (true) {
            String command = cp.getNextLine();
            String[] arr = command.split("(\\s|[,;])+", 2);
            String firstWord = arr[0];
            switch (firstWord) {
                case "move" : {
                    int[] indices = ArrayUtils.toIntArray(arr[1]);
                    RequestMove rm = new RequestMove(indices[0], indices[1]);
                    rp.processMove(rm);
                    Players winner = rp.getWinner();
                    if (winner != null) {
                        System.out.println("--------------------------------------------------------------");
                        System.out.println("Player " + winner + " won this game!");
                        System.out.println("--------------------------------------------------------------");
                        System.out.println("Type any symbols to console for continue");
                        sc.nextLine();
                        begin();
                        break;
                    }
                    printField();
                    printWhoseMoving();
                    break;
                }
                case "skip" : {
                    RequestSkipMove rsm = new RequestSkipMove();
                    rp.processSkipMove(rsm);
                    printWhoseMoving();
                    break;
                }
                case "restart" : {
                    startGame();
                    break;
                }
                case "end" : {
                    begin();
                    break;
                }
                case "help" : {
                    System.out.println("Available commands:");
                    System.out.println("    move *int* *int* - move your checker from first position to second");
                    System.out.println("    skip - skip your move (or end your jumping)");
                    System.out.println("    restart - restart game");
                    System.out.println("    end - go to main menu");
                    break;
                }
            }
            if (cp.getClass().equals(ScriptedCommandProvider.class)) {
                TimeUnit.SECONDS.sleep(5);
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
        rows[10].append(DrawingUtils.getChar(rp.getCheckerAt(120)));
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

        rows[15].append("-----------           ---------");

        rows[16].append("           \\ ");
        rows[16].append(sectors[3][3].reverse());
        rows[16].append(" /");

        rows[17].append("            \\ ");
        rows[17].append(sectors[3][2].reverse());
        rows[17].append(" /");

        rows[18].append("             \\ ");
        rows[18].append(sectors[3][1].reverse());
        rows[18].append(" /");

        rows[19].append("              \\ ");
        rows[19].append(sectors[3][0].reverse());
        rows[19].append(" /");

        rows[20].append("                .");


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
                Checker checker = rp.getCheckerAt(sector * 10 + index);
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
