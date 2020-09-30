package abergaz.com;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CM {
    private Settings settings;
    private static CM instance;

    private CM() {
        settings = Settings.getInstance();
    }

    public static CM getInstance() {
        if (instance == null) {
            instance = new CM();
        }
        return instance;
    }

    public void process() {
        AbsToSbrf();
        SbrfToAbs();
        SbrfToAbsAck();
    }

    /**
     * Исходящие из ВЭБ сообщения формируются в каталоге sbrf_in
     * копируем сообщения в каталог текущей даты в sbrf_in.arch
     * переносим сообщения из sbrf_in в ABS.SBRF.CM.SWIFT.F
     */
    private void AbsToSbrf() {
        File sbrfIn = new File(settings.getSbrfIn());
        File sbrfInArch = new File(settings.getSbrfInArch());
        File absSbrfCmSwiftF = new File(settings.getAbsSbrfCmSwiftF());
        fileTransfer(sbrfIn, sbrfInArch, absSbrfCmSwiftF);
    }

    /**
     * Входящие в ВЭБ сообщения формируются в каталоге SBRF.ABS.CM.SWFIT.F
     * копируем сообщения в каталог текущей даты в sbrf_out.arch
     * переносим сообщения из SBRF.ABS.CM.SWFIT.F в sbrf_out
     */
    private void SbrfToAbs() {
        File sbrfAbsCmSwiftF = new File(settings.getSbrfAbsCmSwiftF());
        File sbrfOutArch = new File(settings.getSbrfOutArch());
        File sbrfOut = new File(settings.getSbrfOut());
        fileTransfer(sbrfAbsCmSwiftF, sbrfOutArch, sbrfOut);
    }

    /**
     * Входящие в ВЭБ Ack/Nack формируются в каталоге SBRF.ABS.CM.SWFIT.AKC.F
     * копируем сообщения в каталог текущей даты в sbrf_out.arch
     * переносим сообщения из SBRF.ABS.CM.SWFIT.ACK.F в sbrf_out
     */
    private void SbrfToAbsAck() {
        File sbrfAbsCmSwiftAckF = new File(settings.getSbrfAbsCmSwiftAckF());
        File sbrfOutArch = new File(settings.getSbrfOutArch());
        File sbrfOut = new File(settings.getSbrfOut());
        fileTransfer(sbrfAbsCmSwiftAckF, sbrfOutArch, sbrfOut);
    }

    private void fileTransfer(File sourceFolder, File copyFolder, File moveFolder) {
        if (sourceFolder.exists()) {
            if (sourceFolder.isDirectory()) {
                for (File sourceFile : sourceFolder.listFiles()) {
                    if (copyFolder.exists()) {
                        if (copyFolder.isDirectory()) {
                            copyFileToArc(sourceFile, copyFolder);
                        }
                    }
                    if (moveFolder.exists()) {
                        if (moveFolder.isDirectory()) {
                            moveFile(sourceFile, moveFolder);
                        }
                    }
                }
            }
        }
    }

    /**
     * Копирует файл в подакталог с текущей датой в указанный каталог
     */
    private void copyFileToArc(File file, File to) {
        File destinationFolder = new File(to.toPath().resolve(getCurDate()).toString());
        if (!destinationFolder.exists()) {
            destinationFolder.mkdir();
        }
        copyFile(file, destinationFolder);
    }

    /**
     * Копирует файл в указанный каталог
     */
    private void copyFile(File sourceFile, File to) {
        if (sourceFile.getName().endsWith(".txt")) {
            System.out.println("Copy file : " + sourceFile.getPath() + " to : " + to.getPath());
            try {
                Files.copy(sourceFile.toPath(), to.toPath().resolve(sourceFile.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Copy error file : " + sourceFile.getPath() + " to : " + to.getPath());
                e.printStackTrace();
            }
        }

    }

    /**
     * Перемещает файл в указанный каталог
     */
    private void moveFile(File sourceFile, File to) {
        if (sourceFile.getName().endsWith(".txt")) {
            System.out.println("Move file : " + sourceFile.getPath() + " to : " + to.getPath());
            try {
                Files.move(sourceFile.toPath(), to.toPath().resolve(sourceFile.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Move error file : " + sourceFile.getPath() + " to : " + to.getPath());
                e.printStackTrace();
            }
        }
    }

    /**
     * Возвразает строку с текщей дытой вида ггггммдд
     */
    private String getCurDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return formatter.format(date);
    }

}
