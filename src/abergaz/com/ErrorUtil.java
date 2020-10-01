package abergaz.com;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorUtil {
    public static String getStackTrace(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();
        return stackTrace;
    }

}
