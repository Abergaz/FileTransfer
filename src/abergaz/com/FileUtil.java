package abergaz.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class.getName());
    /**
     * Копирует файл в указанный каталог
     */
    public static void copyFile(File sourceFile, File to) {
        if (sourceFile.getName().endsWith(".txt")) {
            logger.info("Copy file : " + sourceFile.getPath() + " to : " + to.getPath());
            try {
                Files.copy(sourceFile.toPath(), to.toPath().resolve(sourceFile.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error("Copy error file : " + sourceFile.getPath() + " to : " + to.getPath());
                logger.error(ErrorUtil.getStackTrace(e));
            }
        }

    }

    /**
     * Перемещает файл в указанный каталог
     */
    public static void moveFile(File sourceFile, File to) {
        if (sourceFile.getName().endsWith(".txt")) {
            logger.info("Move file : " + sourceFile.getPath() + " to : " + to.getPath());
            try {
                Files.move(sourceFile.toPath(), to.toPath().resolve(sourceFile.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error("Move error file : " + sourceFile.getPath() + " to : " + to.getPath());
                logger.error(ErrorUtil.getStackTrace(e));
            }
        }
    }

    public static File createFolder(File parentFolder, String newFolderName){
        File dateFolder = new File(parentFolder.toPath().resolve(newFolderName).toString());
        if (!dateFolder.exists()) {
            dateFolder.mkdir();
        }
        return dateFolder;
    }

}
