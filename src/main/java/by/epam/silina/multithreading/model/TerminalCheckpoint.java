package by.epam.silina.multithreading.model;

import by.epam.silina.multithreading.model.impl.LogisticBaseImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

import static by.epam.silina.multithreading.config.Constant.TERMINAL_IS_READY_TO_PROCESS_ANY_VAN;

@Slf4j
public class TerminalCheckpoint implements Runnable {
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private final LogisticBase logisticBase = LogisticBaseImpl.getInstance();

    @Override
    public void run() {
        while (true) {
            Terminal terminal = null;
            try {
                while (terminal == null) {
                    terminal = logisticBase.getFreeTerminal();
                }
                log.info(TERMINAL_IS_READY_TO_PROCESS_ANY_VAN, terminal.getTerminalNumber());
                Van van = null;
                while (van == null) {
                    reentrantLock.lock();
                    van = logisticBase.getVanToProcess();
                    reentrantLock.unlock();
                }
                terminal.processVan(van);
                van.getState().setNextState(van);
                logisticBase.letVanOut(van);
            } finally {
                if (terminal != null) {
                    LogisticBaseImpl.getInstance().returnTerminal(terminal);
                }
            }
        }
    }
}
