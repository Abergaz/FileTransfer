package abergaz.com;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Settings {
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
        //файл, который хранит свойства нашего проекта
        System.out.println("Настроечный файл:");
        System.out.println(new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().getPath()).toPath().getParent().resolve(fileName));
        curDir = new File(Settings.class.getProtectionDomain().getCodeSource().getLocation().getPath()).toPath().getParent().toFile();
        fileSettings =curDir.toPath().resolve(fileName).toFile();
        fileStop = curDir.toPath().resolve("stop").toFile();

        if (!fileSettings.exists()) {
            System.out.println("Настроечный файл не найден");
            System.exit(0);
        }
        if (fileSettings.isDirectory()) {
            System.out.println("Указан каталог а не файл");
            System.exit(0);
        }
        //читаем настройки из файла
        readProperties();
    }

    private void readProperties(){
        //создаем объект Properties и загружаем в него данные из файла.
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(fileSettings));
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл настроек:");
            e.printStackTrace();
            System.exit(0);
        }

        //получаем значения свойств из объекта Properties
        //sbrf_in
        sbrfIn= properties.getProperty("sbrf_in");
        if (sbrfIn != null){
            sbrfIn=sbrfIn.trim().replaceAll("\\s+","");
            if (!sbrfIn.equals("")){
                System.out.println("sbrf_in = "+sbrfIn);
            }else {
                System.out.println("В файле настроек не заполнена настройка: sbrf_in");
                System.exit(0);
            }
        }else{
            System.out.println("В файле настроек нет настройки: sbrf_in");
            System.exit(0);
        }
        //sbrf_in.arch
        sbrfInArch= properties.getProperty("sbrf_in.arch");
        if (sbrfInArch != null){
            sbrfInArch=sbrfInArch.trim().replaceAll("\\s+","");
            if (!sbrfInArch.equals("")){
                System.out.println("sbrf_in.arch = "+sbrfInArch);
            }else {
                System.out.println("В файле настроек не заполнена настройка: sbrf_in.arch");
                System.exit(0);
            }
        }else{
            System.out.println("В файле настроек нет настройки: sbrf_in.arch");
            System.exit(0);
        }
        //sbrf_out
        sbrfOut= properties.getProperty("sbrf_out");
        if (sbrfOut != null){
            sbrfOut=sbrfOut.trim().replaceAll("\\s+","");
            if (!sbrfOut.equals("")){
                System.out.println("sbrf_out = "+sbrfOut);
            }else {
                System.out.println("В файле настроек не заполнена настройка: sbrf_out");
                System.exit(0);
            }
        }else{
            System.out.println("В файле настроек нет настройки: sbrf_out");
            System.exit(0);
        }
        //sbrf_out.arch
        sbrfOutArch= properties.getProperty("sbrf_out.arch");
        if (sbrfOutArch != null){
            sbrfOutArch=sbrfOutArch.trim().replaceAll("\\s+","");
            if (!sbrfOutArch.equals("")){
                System.out.println("sbrf_out.arch = "+sbrfOutArch);
            }else {
                System.out.println("В файле настроек не заполнена настройка: sbrf_out.arch");
                System.exit(0);
            }
        }else{
            System.out.println("В файле настроек нет настройки: sbrf_out.arch");
            System.exit(0);
        }

        //ABS.SBRF.CM.SWIFT.F
        absSbrfCmSwiftF= properties.getProperty("ABS.SBRF.CM.SWIFT.F");
        if (absSbrfCmSwiftF != null){
            absSbrfCmSwiftF=absSbrfCmSwiftF.trim().replaceAll("\\s+","");
            if (!absSbrfCmSwiftF.equals("")){
                System.out.println("ABS.SBRF.CM.SWIFT.F = "+absSbrfCmSwiftF);
            }else {
                System.out.println("В файле настроек не заполнена настройка: ABS.SBRF.CM.SWIFT.F");
                System.exit(0);
            }
        }else{
            System.out.println("В файле настроек нет настройки: ABS.SBRF.CM.SWIFT.F");
            System.exit(0);
        }
        //SBRF.ABS.CM.SWFIT.F
        sbrfAbsCmSwiftF= properties.getProperty("SBRF.ABS.CM.SWFIT.F");
        if (sbrfAbsCmSwiftF != null){
            sbrfAbsCmSwiftF=sbrfAbsCmSwiftF.trim().replaceAll("\\s+","");
            if (!sbrfAbsCmSwiftF.equals("")){
                System.out.println("SBRF.ABS.CM.SWFIT.F = "+sbrfAbsCmSwiftF);
            }else {
                System.out.println("В файле настроек не заполнена настройка: SBRF.ABS.CM.SWFIT.F");
                System.exit(0);
            }
        }else{
            System.out.println("В файле настроек нет настройки: SBRF.ABS.CM.SWFIT.F");
            System.exit(0);
        }
        //SBRF.ABS.CM.SWFIT.F
        sbrfAbsCmSwiftAckF= properties.getProperty("SBRF.ABS.CM.SWFIT.ACK.F");
        if (sbrfAbsCmSwiftAckF != null){
            sbrfAbsCmSwiftAckF=sbrfAbsCmSwiftAckF.trim().replaceAll("\\s+","");
            if (!sbrfAbsCmSwiftAckF.equals("")){
                System.out.println("SBRF.ABS.CM.SWFIT.ACK.F = "+sbrfAbsCmSwiftAckF);
            }else {
                System.out.println("В файле настроек не заполнена настройка: SBRF.ABS.CM.SWFIT.ACK.F");
                System.exit(0);
            }
        }else{
            System.out.println("В файле настроек нет настройки: SBRF.ABS.CM.SWFIT.ACK.F");
            System.exit(0);
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
