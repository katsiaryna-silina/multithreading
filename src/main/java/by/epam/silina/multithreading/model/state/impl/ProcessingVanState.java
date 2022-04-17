package by.epam.silina.multithreading.model.state.impl;

import by.epam.silina.multithreading.model.Van;
import by.epam.silina.multithreading.model.state.VanState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static by.epam.silina.multithreading.config.Constant.VAN_STATUS_IS_CHANGED_ON_PROCESSED;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessingVanState implements VanState {
    private static VanState instance;

    public static VanState getInstance() {
        if (instance == null) {
            instance = new ProcessingVanState();
        }
        return instance;
    }

    @Override
    public void setNextState(Van van) {
        van.setState(ProcessedVanState.getInstance());
        log.info(VAN_STATUS_IS_CHANGED_ON_PROCESSED, van.getNumberInLine());
    }
}
