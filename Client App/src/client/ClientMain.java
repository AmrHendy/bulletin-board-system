package client;

import configuration.Configuration;

public class ClientMain {
	public static void main(String[] args) {
		// args = clientId, 'read'/'write', accessCount, strategyType, severAddress, serverPort, stubName
		Configuration.getConfiguration().addConf("client-id", args[0]);
		Configuration.getConfiguration().addConf("access-count", args[2]);
		Configuration.getConfiguration().addConf("type", args[3]);
		Configuration.getConfiguration().addConf("server-address", args[4]);
		Configuration.getConfiguration().addConf("server-port", args[5]);
		Configuration.getConfiguration().addConf("stub-name", args[6]);
		if(args[1].equalsIgnoreCase("read")) {
			Client readerClient = new ReaderClient();
			readerClient.read();
		}
		else if(args[1].equalsIgnoreCase("write")) {
			Client writerClient = new WriterClient();
			writerClient.write();
		}
		else{
			System.err.println("Error, Not supported kind of client");
		}
	}
}
