package communication.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import communication.CommunicationStrategy;
import configuration.Configuration;
import utilities.Logger;

public class RMIStrategy implements CommunicationStrategy {

	@Override
	public void run(){

		int port = Integer.valueOf(Configuration.getConfiguration().getConf("server-port")) ;
		String serverIp = Configuration.getConfiguration().getConf("server-address") ;
		String stubName = Configuration.getConfiguration().getConf("stub-name") ;
		
		System.out.println("client is connected to server : " +  serverIp + " on port : " + port + " on stub : " + stubName);

		Logger.logServer("read", "Readers");
		Logger.logServer("read", "sSeq\toVal\trID\trNum");
		Logger.logServer("write", "Writers");
		Logger.logServer("write", "sSeq\toVal\twID");
		
		System.setProperty("java.rmi.server.hostname", serverIp);

		try {
			Api api = new Api() ;
			Registry registry = LocateRegistry.createRegistry(port);
			registry.bind(stubName, api);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}