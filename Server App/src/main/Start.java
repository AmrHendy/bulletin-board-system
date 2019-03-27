package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import configuration.Configuration;
import server.Server;
import ssh.SSHHandler;
import utilities.FileHandler;
import utilities.StrategyType;

public class Start {

	//TODO solve bugs here


	private static void spawnClients(){
		int clientCount = Integer.valueOf(Configuration.getConfiguration().getConf("client-count"));
		System.out.println(clientCount);
		for(int clientId = 0; clientId < clientCount; clientId++) {
			String clientAddress = Configuration.getConfiguration().getConf("client-address-" + clientId);
			// args = clientId, 'read'/'write', accessCount, strategyType, severAddress, serverPort, stubName
			String clientIdArg = String.valueOf(clientId);
			String clientTypeArg = Configuration.getConfiguration().getConf("client-type-" + clientId);
			String clientAccessCountArg = Configuration.getConfiguration().getConf("client-access");
			String clientStrategyTypeArg = Configuration.getConfiguration().getConf("type");
			String serverAddressArg = Configuration.getConfiguration().getConf("server-address");
			String serverPortArg = Configuration.getConfiguration().getConf("server-port");
			String stubNameArg = Configuration.getConfiguration().getConf("stub-name");

			try {
				
				SSHHandler ssh = new SSHHandler();
				
				String command = "cd Desktop ;"
								+ " java -jar client.jar "
								+ clientIdArg + " "
								+ clientTypeArg + " "
								+ clientAccessCountArg + " "
								+ clientStrategyTypeArg + " "
								+ serverAddressArg + " "
								+ serverPortArg + " "
								+ stubNameArg;

				
				System.out.println("ssh on abdelrhman@" + clientAddress);
				
				
				ssh.execCommand("username", clientAddress, 44444, "password", command);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		FileHandler.readConfig(fileName);
		runServer();
		spawnClients();
	}

}
