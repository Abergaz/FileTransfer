package abergaz.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;

public class Start {
    private static final Logger logger = LoggerFactory.getLogger(Start.class.getName());
    public static Settings settings;

    public static void start(String[] args) {
        try {
            logger.info("Start FileTransfer");
            initSettings(args);
            while (true) {
                //проверка на завершение работы, если есть файл stop
                checkStop();
                Transfer transfer = Transfer.getInstance();
                transfer.process();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            end(e);
        }
    }

    private static void initSettings(String[] args) {
        try {
            logger.info("Init settings start");
            settings = Settings.getInstance();
            if (args.length == 0 || args[0].trim().replaceAll("\\s+", "").equals("")) {
                end("Необходимо указать файл настроек в параметрах запуска. \n" +
                        "Например: java -jar FileTransfer.jar settings.properties");
            } else {
                settings.init(args[0]);
            }
            logger.info("Init settings end");
        } catch (Exception e) {
            end(e);
        }
    }

    /**
     * Выводит, что программа завершила работу и завершает работу
     */
    public static void end() {
        logger.info("End FileTransfer");
        System.exit(0);
    }

    /**
     * Выводит сообщение, затем , что программа завершила работу и завершает работу
     */
    public static void end(String message) {
        logger.info(message);
        logger.info("End FileTransfer");
        System.exit(0);
    }

    /**
     * Выводит стектрейс ошибки, затем , что программа завершила работу и завершает работу
     */
    public static void end(Exception e) {
        logger.error(ErrorUtil.getStackTrace(e));
        logger.info("End FileTransfer");
        System.exit(0);
    }

    /**
     * Выводит сообщение, стектрейс ошибки, затем , что программа завершила работу и завершает работу
     */
    public static void end(Exception e, String message) {
        logger.error(message);
        logger.error(ErrorUtil.getStackTrace(e));
        logger.info("End FileTransfer");
        System.exit(0);
    }

    private static void checkStop() {
        try {
            if (settings.getFileStop().exists()) {
                try {
                    //удаляем файл stop
                    Files.delete(settings.getFileStop().toPath());
                } catch (IOException e) {
                    logger.error(ErrorUtil.getStackTrace(e));
                }
                end();
            }
        } catch (Exception e) {
            end(e);
        }
    }
}
