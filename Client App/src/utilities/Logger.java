package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Logger {

    public static void logClient(int clientId, String message){
        String filePath = "log" + clientId + ".txt";
        logMessage(filePath, message);
    }

    public static synchronized void logServer(String clientType, String message){
        String filePath = "logs-server-";
        if(clientType.equalsIgnoreCase("read")){
            filePath += "read.txt";
        }
        else{
            filePath += "write.txt";
        }
        logMessage(filePath, message);
    }

    private static void logMessage(String filePath, String message){
        try{
            // remove extra new lines
            message = message.replace("\n", "");
            // true for append mode
            FileWriter fileWriter = new FileWriter(new File(filePath), true);
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
            printWriter.println(message);
            printWriter.close();
            fileWriter.close();
        } catch(Exception e){
            System.out.println("Error in logging for file path : " + filePath);
        }
    }
}
