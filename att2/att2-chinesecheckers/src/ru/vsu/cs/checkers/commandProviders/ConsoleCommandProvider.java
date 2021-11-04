package ru.vsu.cs.checkers.commandProviders;

import java.util.Scanner;

public class ConsoleCommandProvider implements CommandProvider {

    Scanner sc;

    public ConsoleCommandProvider() {
        sc = new Scanner(System.in);
    }

    @Override
    public String getNextLine() {
        return sc.nextLine();
    }
}