package by.epam.silina.multithreading.util.impl;

import by.epam.silina.multithreading.util.FileUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static by.epam.silina.multithreading.config.Constant.*;

@Slf4j
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtilImpl implements FileUtil {
    private static final FileUtil instance = new FileUtilImpl();
    private int maxCapacityOfVans;
    private int numberOfTerminals;

    public static FileUtil getInstance() {
        return instance;
    }

    @Override
    public boolean isFileExists(File file) {
        if (file.exists()) {
            log.info(FILE_IS_EXISTS, file.getName());
            return true;
        } else {
            log.info(FILE_DOES_NOT_EXIST, file.getName());
            return false;
        }
    }

    @Override
    public void processFile(File file) {
        if (isFileExists(file)) {
            try (FileInputStream fileInputStream = new FileInputStream(FILE_PATH)) {
                Properties property = new Properties();
                property.load(fileInputStream);

                maxCapacityOfVans = Integer.parseInt(property.getProperty(LOGISTIC_BASE_VAN_CAPACITY));
                log.info(SCANNED_DATA_FROM_FILE, file.getName(), LOGISTIC_BASE_VAN_CAPACITY, maxCapacityOfVans);

                numberOfTerminals = Integer.parseInt(property.getProperty(LOGISTIC_BASE_TERMINAL_NUMBER));
                log.info(SCANNED_DATA_FROM_FILE, file.getName(), LOGISTIC_BASE_TERMINAL_NUMBER, numberOfTerminals);
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }
}
