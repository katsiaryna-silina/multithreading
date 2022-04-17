package by.epam.silina.multithreading.model;

import by.epam.silina.multithreading.model.impl.LogisticBaseImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;

import static by.epam.silina.multithreading.config.Constant.CANNOT_ENTER_TO_THE_BASE;
import static by.epam.silina.multithreading.config.Constant.WAITING_BASE_ENTRANCE_TIME_IN_SECONDS;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseCheckpoint implements Runnable {
    private static final BaseCheckpoint instance = new BaseCheckpoint();
    private final Deque<Van> vansWithoutPerishableGoodsOutOfBase = new ArrayDeque<>();
    private final Deque<Van> vansWithPerishableGoodsOutOfBase = new ArrayDeque<>();

    private final LogisticBase logisticBase = LogisticBaseImpl.getInstance();

    public static BaseCheckpoint getInstance() {
        return instance;
    }

    @Override
    public void run() {
        while (true) {
            if (logisticBase.canVanIn()) {
                Van van = null;
                if (!vansWithPerishableGoodsOutOfBase.isEmpty()) {
                    van = vansWithPerishableGoodsOutOfBase.pollFirst();
                } else if (!vansWithoutPerishableGoodsOutOfBase.isEmpty()) {
                    van = vansWithoutPerishableGoodsOutOfBase.pollFirst();
                }
                if (van != null) {
                    while (!logisticBase.letVanIn(van)) {
                        log.info(CANNOT_ENTER_TO_THE_BASE, WAITING_BASE_ENTRANCE_TIME_IN_SECONDS);
                    }
                }
            }
        }
    }

    public void addVanToQueue(Van van) {
        if (van.isWithPerishableGoods()) {
            vansWithPerishableGoodsOutOfBase.add(van);
        } else {
            vansWithoutPerishableGoodsOutOfBase.add(van);
        }
    }
}
