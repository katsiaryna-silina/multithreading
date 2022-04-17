package by.epam.silina.multithreading.model.state.impl;

import by.epam.silina.multithreading.model.Van;
import by.epam.silina.multithreading.model.state.VanState;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static by.epam.silina.multithreading.config.Constant.VAN_IS_IN_ITS_END_STATE;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessedVanState implements VanState {
    private static VanState instance;

    public static VanState getInstance() {
        if (instance == null) {
            instance = new ProcessedVanState();
        }
        return instance;
    }

    @Override
    public void setNextState(Van van) {
        log.info(VAN_IS_IN_ITS_END_STATE);
    }
}
