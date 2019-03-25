package main;

import client.Client;
import client.ReaderClient;
import client.WriterClient;
import configuration.Configuration;
import server.Server;
import utilities.StrategyType;

public class Start {

	//TODO solve bugs here 
	
	
    private static void spawnClients(){
        int clientCount = Integer.valueOf(Configuration.getConfiguration().getConf("client-count"));
        for(int clientId = 1; clientId <= clientCount; clientId++) {
        	String clientAddress = Configuration.getConfiguration().getConf("client-address-" + clientId);
    		// args = clientId, 'read'/'write', accessCount, strategyType, severAddress, serverPort, stubName
        	String clientIdArg = String.valueOf(clientId);
        	String clientTypeArg = Configuration.getConfiguration().getConf("client-type-" + clientId);
        	String clientAccessCountArg = Configuration.getConfiguration().getConf("client-access-count-" + clientId);
        	String clientStrategyTypeArg = Configuration.getConfiguration().getConf("type");
        	String serverAddressArg = Configuration.getConfiguration().getConf("server-address");
        	String serverPortArg = Configuration.getConfiguration().getConf("server-port");
        	String stubNameArg = Configuration.getConfiguration().getConf("stub-name");
        	
        	//TODO ssh
        	// String path = "";
        	// Runtime.getRuntime().exec("ssh " + clientAddress + " cd " + path + " ;" + "java  " + ... arguments ... );
        }
        
        
        
    	/* reader client */
        Client c1 = new ReaderClient(StrategyType.SOCKET);
        String news = c1.read();
        System.log.print(news);

        /* writer client */
        String someNews = "hello world";
        Client c2 = new WriterClient(StrategyType.SOCKET);
        c2.write(someNews);
        System.log.print(someNews);
    }

    private static void runServer(){
    	String strategyType = Configuration.getConfiguration().getConf("type");
    	StrategyType type;
    	if(strategyType.equalsIgnoreCase("socket")) {
    		type = StrategyType.SOCKET;
    	}
    	else {
    		type = StrategyType.RMI;
    	}
        Server server = new Server(type);
        server.start();
    }

    public static void main(String args[]){
    	String fileName = args[0];
        FileHandler.readConfiguration(fileName);
    	
        runServer();
        
        
        spawnClients();
    }

}