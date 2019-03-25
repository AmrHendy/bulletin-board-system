package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import configuration.Configuration;
import server.Server;
import utilities.FileHandler;
import utilities.StrategyType;

public class Start {

	//TODO solve bugs here


	private static void spawnClients(){
		int clientCount = Integer.valueOf(Configuration.getConfiguration().getConf("client-count"));
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
				Process proc =Runtime.getRuntime().exec("ssh shaban@172.20.10.2 cd Desktop ;"
						+ " java -jar client.jar "
						+ clientIdArg + " "
						+ clientTypeArg + " "
						+ clientAccessCountArg + " "
						+ clientStrategyTypeArg + " "
						+ serverAddressArg + " "
						+ serverPortArg + " "
						+ stubNameArg);

				BufferedReader stdInput = new BufferedReader(new
						InputStreamReader(proc.getInputStream()));

				BufferedReader stdError = new BufferedReader(new
						InputStreamReader(proc.getErrorStream()));

				// read the output from the command
				/*
				System.out.println("Here is the standard output of the command:\n");
				String s = null;
				while ((s = stdInput.readLine()) != null) {
					System.out.println(s);
				}
				*/

				// read any errors from the attempted command
				/*
				System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) {
					System.out.println(s);
				}
				*/

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