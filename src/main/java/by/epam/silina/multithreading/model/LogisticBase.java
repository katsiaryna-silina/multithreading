package by.epam.silina.multithreading.model;

import by.epam.silina.multithreading.model.impl.TerminalImpl;

import java.util.concurrent.atomic.AtomicInteger;

public interface LogisticBase {

    AtomicInteger getNumberOfTerminals();

    void addTerminal(TerminalImpl terminal);

    boolean canVanIn();

    Van getVanToProcess();

    boolean letVanIn(Van van);

    Terminal getFreeTerminal();

    void returnTerminal(Terminal terminal);

    void letVanOut(Van van);
}
