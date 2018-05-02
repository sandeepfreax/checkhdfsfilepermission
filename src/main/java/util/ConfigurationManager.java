package util;

import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;

public class ConfigurationManager {
    private static final Logger logger = Logger.getLogger(ConfigurationManager.class);

    private static Configuration configuration;

    public static Configuration getInstance() {
        logger.info("Getting default Hadoop Configuration.");
        if(configuration == null){
            configuration = new Configuration();
        }
        return configuration;
    }
}
