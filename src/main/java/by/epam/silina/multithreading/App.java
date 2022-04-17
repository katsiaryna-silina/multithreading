package by.epam.silina.multithreading;

import by.epam.silina.multithreading.model.BaseCheckpoint;
import by.epam.silina.multithreading.model.LogisticBase;
import by.epam.silina.multithreading.model.TerminalCheckpoint;
import by.epam.silina.multithreading.model.Van;
import by.epam.silina.multithreading.model.impl.LogisticBaseImpl;
import by.epam.silina.multithreading.model.impl.TerminalImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) {
        LogisticBase logisticBase = LogisticBaseImpl.getInstance();
        int numberOfTerminals = logisticBase.getNumberOfTerminals().get();

        ExecutorService terminalEntranceExecutor = Executors.newFixedThreadPool(numberOfTerminals);
        IntStream.rangeClosed(1, numberOfTerminals).boxed().forEach(i -> {
            logisticBase.addTerminal(new TerminalImpl(i));
            terminalEntranceExecutor.submit(new TerminalCheckpoint());
        });

        var baseCheckpoint = BaseCheckpoint.getInstance();
        Thread thread = new Thread(BaseCheckpoint.getInstance());
        thread.start();

        Van van1 = new Van(1, false);
        Van van2 = new Van(2, false);
        Van van3 = new Van(3, false);
        Van van4 = new Van(4, false);
        Van van5 = new Van(5, false);
        Van van6 = new Van(6, true);
        baseCheckpoint.addVanToQueue(van1);
        baseCheckpoint.addVanToQueue(van2);
        baseCheckpoint.addVanToQueue(van3);
        baseCheckpoint.addVanToQueue(van4);
        baseCheckpoint.addVanToQueue(van5);
        baseCheckpoint.addVanToQueue(van6);
    }
}
