import abergaz.com.CM;
import abergaz.com.Settings;

import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Start FileTransfer");
            Settings settings =  Settings.getInstance();
            if (args.length == 0 || args[0].trim().replaceAll("\\s+", "").equals("")) {
                System.out.println("Необходимо указать файл настроек в параметрах запуска");
                System.out.println("Например: java -jar FileTransfer.jar settings.properties");
                System.exit(0);
            } else {
                settings.init(args[0]);
                while (true){
                    //проверка на завершение работы, если есть файл stop
                    CM cm = CM.getInstance();
                    cm.process();
                    if (settings.getFileStop().exists()){
                        System.out.println("End FileTransfer");
                        Files.delete(settings.getFileStop().toPath());
                        System.exit(0);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
