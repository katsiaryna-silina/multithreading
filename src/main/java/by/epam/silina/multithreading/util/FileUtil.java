package by.epam.silina.multithreading.util;

import java.io.File;

public interface FileUtil {
    boolean isFileExists(File file);

    void processFile(File file);

    int getMaxCapacityOfVans();

    int getNumberOfTerminals();
}
