package abergaz.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transfer {
    private static Transfer instance;
    private static final Logger logger = LoggerFactory.getLogger(Transfer.class.getName());

    private Transfer() {}

    public static Transfer getInstance() {
        if (instance == null) {
            instance = new Transfer();
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
        File sbrfIn = new File(Start.settings.getSbrfIn());
        File sbrfInArch = new File(Start.settings.getSbrfInArch());
        File absSbrfCmSwiftF = new File(Start.settings.getAbsSbrfCmSwiftF());
        fileTransfer(sbrfIn, sbrfInArch, absSbrfCmSwiftF);
    }

    /**
     * Входящие в ВЭБ сообщения формируются в каталоге SBRF.ABS.CM.SWFIT.F
     * копируем сообщения в каталог текущей даты в sbrf_out.arch
     * переносим сообщения из SBRF.ABS.CM.SWFIT.F в sbrf_out
     */
    private void SbrfToAbs() {
        File sbrfAbsCmSwiftF = new File(Start.settings.getSbrfAbsCmSwiftF());
        File sbrfOutArch = new File(Start.settings.getSbrfOutArch());
        File sbrfOut = new File(Start.settings.getSbrfOut());
        fileTransfer(sbrfAbsCmSwiftF, sbrfOutArch, sbrfOut);
    }

    /**
     * Входящие в ВЭБ Ack/Nack формируются в каталоге SBRF.ABS.CM.SWFIT.AKC.F
     * копируем сообщения в каталог текущей даты в sbrf_out.arch
     * переносим сообщения из SBRF.ABS.CM.SWFIT.ACK.F в sbrf_out
     */
    private void SbrfToAbsAck() {
        File sbrfAbsCmSwiftAckF = new File(Start.settings.getSbrfAbsCmSwiftAckF());
        File sbrfOutArch = new File(Start.settings.getSbrfOutArch());
        File sbrfOut = new File(Start.settings.getSbrfOut());
        fileTransfer(sbrfAbsCmSwiftAckF, sbrfOutArch, sbrfOut);
    }

    private void fileTransfer(File sourceFolder, File copyFolder, File moveFolder) {
        if (sourceFolder.exists()) {
            if (sourceFolder.isDirectory()) {
                for (File sourceFile : sourceFolder.listFiles()) {
                    if (copyFolder.exists()) {
                        if (copyFolder.isDirectory()) {
                            copyFileToArcDate(sourceFile, copyFolder);
                        }
                    }
                    if (moveFolder.exists()) {
                        if (moveFolder.isDirectory()) {
                            FileUtil.moveFile(sourceFile, moveFolder);
                        }
                    }
                }
            }
        }
    }

    /**
     * Копирует файл в подакталог с текущей датой в указанный каталог
     */
    private void copyFileToArcDate(File file, File toArchFolder) {
        FileUtil.copyFile(file, FileUtil.createFolder(toArchFolder,getCurDate()));
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
