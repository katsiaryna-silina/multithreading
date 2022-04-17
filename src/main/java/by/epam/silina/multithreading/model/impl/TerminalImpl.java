package by.epam.silina.multithreading.model.impl;

import by.epam.silina.multithreading.model.Terminal;
import by.epam.silina.multithreading.model.Van;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.epam.silina.multithreading.config.Constant.*;

@Slf4j
@Data
public class TerminalImpl implements Terminal {
    private final Integer terminalNumber;
    private boolean isWorking = false;
    private Van currentVan;
    private boolean isFree = true;
    private Lock locker = new ReentrantLock();

    public TerminalImpl(Integer number) {
        this.terminalNumber = number;
    }

    @Override
    public void processVan(Van van) {
        log.info(TERMINAL_STARTED_PROCESSING_VAN, terminalNumber, van.getNumberInLine());
        TimeUnit time = TimeUnit.SECONDS;
        try {
            time.sleep(VAN_PROCESSING_TIME_IN_SECONDS);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        van.getState().setNextState(van);
        log.info(TERMINAL_ENDED_PROCESSING_VAN, terminalNumber, van.getNumberInLine());
    }
}
