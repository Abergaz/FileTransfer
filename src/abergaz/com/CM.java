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
        copySbrfToAbsAck();
    }

    private void AbsToSbrf() {
        //копируем из sbrf_In в sbrf_In.Arch\дата и переносим в ABS.SBRF.SM.SWIFT.F
        File sbrfIn = new File(settings.getSbrfIn());
        File sbrfInArch = new File(settings.getSbrfInArch());
        File absSbrfCmSwiftF = new File(settings.getAbsSbrfCmSwiftF());
        fileTransfer(sbrfIn,sbrfInArch,absSbrfCmSwiftF);
    }

    private void SbrfToAbs() {
        //копируем из SBRF.ABS.CM.SWFIT.F в sbrf_Out.Arch\дата и переносим в sbrf_Out
        File sbrfAbsCmSwiftF = new File(settings.getSbrfAbsCmSwiftF());
        File sbrfOutArch = new File(settings.getSbrfOutArch());
        File sbrfOut = new File(settings.getSbrfOut());
        fileTransfer(sbrfAbsCmSwiftF,sbrfOutArch,sbrfOut);
    }

    private void copySbrfToAbsAck() {

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

    private void copyFileToArc(File file, File to) {
        File destinationFolder = new File(to.toPath().resolve(getCurDate()).toString());
        if (!destinationFolder.exists()) {
            destinationFolder.mkdir();
        }
        copyFile(file, destinationFolder);
    }

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

    private String getCurDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return formatter.format(date);
    }

}
