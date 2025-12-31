package utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerUtil {
    private static Logger logger = Logger.getLogger(LoggerUtil.class);

    static {
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }
}
