package abergaz.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private static final Logger logger = LoggerFactory.getLogger(Settings.class.getName());
    private static Settings instance;
    private File fileSettings;
    private File curDir;
    private File fileStop;
    private String sbrfIn;
    private String sbrfInArch;
    private String sbrfOut;
    private String sbrfOutArch;
    private String absSbrfCmSwiftF;
    private String sbrfAbsCmSwiftF;
    private String sbrfAbsCmSwiftAckF;

    private Settings() {
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public void init(String fileName) {
        try {
            //файл, который хранит свойства нашего проекта
            logger.info("Настроечный файл:");
            logger.info(new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().getPath()).toPath().getParent().resolve(fileName).toString());
            curDir = new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().getPath()).toPath().getParent().toFile();
            fileSettings = curDir.toPath().resolve(fileName).toFile();
            fileStop = curDir.toPath().resolve("stop").toFile();

            if (!fileSettings.exists()) {
                logger.warn("Настроечный файл не найден");
                Start.end();
            }
            if (fileSettings.isDirectory()) {
                logger.warn("Указан каталог а не файл");
                Start.end();
            }
            //читаем настройки из файла
            readProperties();
        } catch (Exception e) {
            logger.error(e.getMessage());
            Start.end();
        }
    }

    private void readProperties() {
        //создаем объект Properties и загружаем в него данные из файла.
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(fileSettings));
        } catch (IOException e) {
            logger.error("Не удалось прочитать файл настроек:");
            logger.error(e.getMessage());
            Start.end();
        }
        //получаем значения свойств из объекта Properties
        readProperty(properties,"sbrf_in");
        readProperty(properties,"sbrf_in.arch");
        readProperty(properties,"sbrf_out");
        readProperty(properties,"sbrf_out.arch");
        readProperty(properties,"ABS.SBRF.CM.SWIFT.F");
        readProperty(properties,"SBRF.ABS.CM.SWFIT.F");
        readProperty(properties,"SBRF.ABS.CM.SWFIT.ACK.F");
     }

    private void readProperty(Properties properties,String property){
        String value = properties.getProperty(property);
        if (value != null) {
            value = value.trim().replaceAll("\\s+", "");
            if (!value.equals("")) {
                logger.info(property + " = " + value);
                if (property.equals("sbrf_in")) sbrfIn=value;
                if (property.equals("sbrf_in.arch")) sbrfInArch=value;
                if (property.equals("sbrf_out")) sbrfOut=value;
                if (property.equals("sbrf_out.arch")) sbrfOutArch=value;
                if (property.equals("ABS.SBRF.CM.SWIFT.F")) absSbrfCmSwiftF=value;
                if (property.equals("SBRF.ABS.CM.SWFIT.F")) sbrfAbsCmSwiftF=value;
                if (property.equals("SBRF.ABS.CM.SWFIT.ACK.F")) sbrfAbsCmSwiftAckF=value;
            } else {
                logger.warn("В файле настроек не заполнена настройка: " +property);
                Start.end();
            }
        } else {
            logger.info("В файле настроек нет настройки: " + property);
            Start.end();
        }
    }

    public File getFileSettings() {
        return fileSettings;
    }

    public File getCurDir() {
        return curDir;
    }

    public String getSbrfIn() {
        return sbrfIn;
    }

    public String getSbrfInArch() {
        return sbrfInArch;
    }

    public String getSbrfOut() {
        return sbrfOut;
    }

    public String getSbrfOutArch() {
        return sbrfOutArch;
    }

    public String getAbsSbrfCmSwiftF() {
        return absSbrfCmSwiftF;
    }

    public String getSbrfAbsCmSwiftF() {
        return sbrfAbsCmSwiftF;
    }

    public String getSbrfAbsCmSwiftAckF() {
        return sbrfAbsCmSwiftAckF;
    }

    public File getFileStop() {
        return fileStop;
    }
}
