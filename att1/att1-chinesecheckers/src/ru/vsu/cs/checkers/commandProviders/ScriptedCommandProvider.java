package ru.vsu.cs.checkers.commandProviders;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScriptedCommandProvider implements CommandProvider{

    Scanner sc;


    public ScriptedCommandProvider(File file) throws FileNotFoundException {
        sc = new Scanner(file);
    }

    @Override
    public String getNextLine() {
        return sc.nextLine();
    }

    @Override
    public void stop() {
    }

}
