package by.epam.silina.multithreading.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {
    public static final String FILE_PATH = "src/main/resources/config.properties";
    public static final String FILE_IS_EXISTS = "File=\"{}\" exists";
    public static final String FILE_DOES_NOT_EXIST = "File=\"{}\" doesn't exist";
    public static final String LOGISTIC_BASE_VAN_CAPACITY = "logistic.base.van.capacity";
    public static final String LOGISTIC_BASE_TERMINAL_NUMBER = "logistic.base.terminal.number";
    public static final String SCANNED_DATA_FROM_FILE = "Scanned data from file=\"{}\" : \"{}\"=\"{}\"";

    public static final String CANNOT_ADD_TERMINAL = "Cannot add terminal. Current number of terminals is maximum.";
    public static final long VAN_PROCESSING_TIME_IN_SECONDS = 5L;
    public static final int WAITING_BASE_ENTRANCE_TIME_IN_SECONDS = 60;
    public static final String CANNOT_ENTER_TO_THE_BASE = "Cannot enter to the base after {} sec waiting. Keep waiting.";
    public static final int WAITING_TERMINAL_ENTRANCE_TIME_IN_SECONDS = 30;
    public static final String ALL_TERMINALS_ARE_BUSY = "All terminals are busy after {} sec waiting :( Keep waiting.";
    public static final String VAN_IS_LEFT_THE_BASE = "Van {} is left the base.";
    public static final String TERMINAL_IS_FREE = "Terminal {} is free.";
    public static final String TERMINAL_STARTED_PROCESSING_VAN = "Terminal {} started processing Van {}.";
    public static final String TERMINAL_ENDED_PROCESSING_VAN = "Terminal {} ended processing Van {}.";
    public static final String TERMINAL_IS_READY_TO_PROCESS_ANY_VAN = "Terminal {} is ready to process any Van.";

    public static final String VAN_IS_IN_ITS_END_STATE = "Van is in its end state.";
    public static final String VAN_STATUS_IS_CHANGED_ON_PROCESSED = "Van {} status is changed on \"Processed\".";
    public static final String VAN_STATUS_IS_CHANGED_ON_ENTERED_THE_BASE = "Van {} status is changed on \"Entered the base\" and is waiting for terminal.";
    public static final String VAN_STATUS_IS_CHANGED_ON_PROCESSING = "Van {} status is changed on \"Processing\".";
}
