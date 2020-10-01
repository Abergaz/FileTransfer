package abergaz.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

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
                CM cm = CM.getInstance();
                cm.process();
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            end();
        }
    }

    private static void initSettings(String[] args) {
        try {
            logger.info("Init settings start");
            settings = Settings.getInstance();
            if (args.length == 0 || args[0].trim().replaceAll("\\s+", "").equals("")) {
                logger.warn("Необходимо указать файл настроек в параметрах запуска");
                logger.warn("Например: java -jar FileTransfer.jar settings.properties");
                end();
            } else {
                settings.init(args[0]);
            }
            logger.info("Init settings end");
        } catch (Exception e) {
            logger.error(e.getMessage());
            end();
        }
    }

    public static void end() {
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
                    logger.error(e.getMessage());
                }
                end();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            end();
        }
    }
}
