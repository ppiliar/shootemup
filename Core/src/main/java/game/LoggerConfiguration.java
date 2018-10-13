package game;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;


public class LoggerConfiguration {

    private static final LogManager logManager = LogManager.getLogManager();

    public LoggerConfiguration(){
        try {
            InputStream inputStream = getClass().getResourceAsStream("/logger.properties");
                    //ClassLoader.class.getResourceAsStream("/logger.properties");

            //System.out.println(inputStream);
            logManager.readConfiguration(inputStream);
            //System.out.println("loaded logger.properties");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}