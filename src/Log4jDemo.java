import org.apache.log4j.Logger;

public class Log4jDemo {

    private static Logger logger = Logger.getLogger(Log4jDemo.class);

    public static void main(String[] args) {
        System.out.println("Elo\n");

        logger.info("Das ist ein Info");
        logger.error("Ein Error");
        logger.warn("Eine Warnung");
        logger.fatal("Ein Fatal");

    }
}
