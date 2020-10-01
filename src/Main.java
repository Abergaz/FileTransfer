import abergaz.com.Start;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());
    public static void main(String[] args) {
        try {
            Start.start(args);
        }catch (Exception e){
            Start.end(e);
        }
    }
}
