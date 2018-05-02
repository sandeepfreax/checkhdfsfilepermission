package jobs;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.log4j.Logger;
import util.ConfigurationManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] str) throws IOException{
        logger.info("Starting the application.");

        if(str.length > 1){
            String path = str[1];
            Path pathFs = new Path(path);
            List<String> permissionsNotAvailable = new ArrayList<String>();
            logger.info("Checking permission for : " + path);

            FileSystem fileSystem = FileSystem.get(ConfigurationManager.getInstance());
            FsPermission fsPermission = fileSystem.getFileStatus(pathFs).getPermission();
            logger.info("File permissions : " + fsPermission.toString());

            String userPermission = fsPermission.getUserAction().toString();
            if (!userPermission.equalsIgnoreCase("ALL")){
                if (!userPermission.contains("READ")){
                    permissionsNotAvailable.add("READ");
                }
                if (!userPermission.contains("WRITE")){
                    permissionsNotAvailable.add("WRITE");
                }
                if (!userPermission.contains("EXECUTE")){
                    permissionsNotAvailable.add("EXECUTE");
                }
            }

            if (permissionsNotAvailable.size() > 0){
                String errorMsg = String.join(" and ", permissionsNotAvailable);
                throw new SecurityException("User does not have " + errorMsg + " permission on " + path);
            }else {
                logger.info("User has got all the permissions on : " + path);
            }
        } else {
            logger.error("Insufficient arguments supplied. Please provide path of the folder whose permission we need to check.");
        }
    }
}
