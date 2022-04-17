package by.epam.silina.multithreading.model.impl;

import by.epam.silina.multithreading.model.LogisticBase;
import by.epam.silina.multithreading.model.Terminal;
import by.epam.silina.multithreading.model.Van;
import by.epam.silina.multithreading.util.FileUtil;
import by.epam.silina.multithreading.util.impl.FileUtilImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import static by.epam.silina.multithreading.config.Constant.*;

@Slf4j
@Data
public class LogisticBaseImpl implements LogisticBase {
    private static final LogisticBase instance = new LogisticBaseImpl();
    private static ReentrantLock reentrantLock = new ReentrantLock();
    private static Deque<Van> vansWithoutPerishableGoodsOnBase = new ArrayDeque<>();
    private static Deque<Van> vansWithPerishableGoodsOnBase = new ArrayDeque<>();
    private AtomicInteger maxCapacityOfVans;
    private AtomicInteger numberOfTerminals;
    private Deque<Terminal> terminals = new ArrayDeque<>();
    private Semaphore terminalSemaphore;
    private AtomicInteger currentNumberOfVans = new AtomicInteger(0);
    private Semaphore baseSemaphore;

    private LogisticBaseImpl() {
        File file = new File(FILE_PATH);

        FileUtil fileUtil = FileUtilImpl.getInstance();
        fileUtil.processFile(file);
        maxCapacityOfVans = new AtomicInteger(fileUtil.getMaxCapacityOfVans());
        numberOfTerminals = new AtomicInteger(fileUtil.getNumberOfTerminals());

        terminalSemaphore = new Semaphore(numberOfTerminals.get(), true);
        baseSemaphore = new Semaphore(maxCapacityOfVans.get(), true);
    }

    public static LogisticBase getInstance() {
        return instance;
    }

    @Override
    public void addTerminal(TerminalImpl terminal) {
        if (terminals.size() < numberOfTerminals.get()) {
            terminals.add(terminal);
        } else {
            log.error(CANNOT_ADD_TERMINAL);
        }
    }

    @Override
    public Van getVanToProcess() {
        reentrantLock.lock();
        Van van = null;
        if (!vansWithPerishableGoodsOnBase.isEmpty()) {
            van = vansWithPerishableGoodsOnBase.pollFirst();
        } else if (!vansWithoutPerishableGoodsOnBase.isEmpty()) {
            van = vansWithoutPerishableGoodsOnBase.pollFirst();
        }
        reentrantLock.unlock();
        return van;
    }


    @Override
    public boolean canVanIn() {
        return currentNumberOfVans.get() < maxCapacityOfVans.get();
    }

    public boolean letVanIn(Van van) {
        try {
            if (baseSemaphore.tryAcquire(WAITING_BASE_ENTRANCE_TIME_IN_SECONDS, TimeUnit.SECONDS)) {
                try {
                    reentrantLock.lock();
                    if (van.isWithPerishableGoods()) {
                        vansWithPerishableGoodsOnBase.add(van);
                    } else {
                        vansWithoutPerishableGoodsOnBase.add(van);
                    }
                    currentNumberOfVans.addAndGet(1);
                    van.getState().setNextState(van);
                    return true;
                } finally {
                    reentrantLock.unlock();
                }
            }
        } catch (InterruptedException e) {
            log.error("", e);
        }
        return false;
    }

    public Terminal getFreeTerminal() {
        Terminal terminal = null;
        try {
            if (terminalSemaphore.tryAcquire(WAITING_TERMINAL_ENTRANCE_TIME_IN_SECONDS, TimeUnit.SECONDS)) {
                try {
                    reentrantLock.lock();
                    terminal = terminals.pollFirst();
                } finally {
                    reentrantLock.unlock();
                }
            } else {
                log.info(ALL_TERMINALS_ARE_BUSY, WAITING_TERMINAL_ENTRANCE_TIME_IN_SECONDS);
            }
        } catch (InterruptedException e) {
            log.error("", e);
        }
        return terminal;
    }

    @Override
    public void returnTerminal(Terminal terminal) {
        try {
            reentrantLock.lock();
            terminals.offer(terminal);
            terminalSemaphore.release();
            log.info(TERMINAL_IS_FREE, terminal.getTerminalNumber());
        } finally {
            reentrantLock.unlock();
        }
    }

    public void letVanOut(Van van) {
        try {
            reentrantLock.lock();
            vansWithoutPerishableGoodsOnBase.remove(van);
            currentNumberOfVans.decrementAndGet();
            baseSemaphore.release();
            log.info(VAN_IS_LEFT_THE_BASE, van.getNumberInLine());
        } finally {
            reentrantLock.unlock();
        }
    }
}
