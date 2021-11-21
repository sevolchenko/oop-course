package ru.vsu.cs.checkers.commandProviders;

public interface CommandProvider {

    String getNextLine();

    void close();

}
