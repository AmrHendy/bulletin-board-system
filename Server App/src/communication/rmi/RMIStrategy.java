package communication.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import communication.CommunicationStrategy;
import configuration.Configuration;

public class RMIStrategy implements CommunicationStrategy {

    @Override
    public void run(){
        
    	int port = Integer.valueOf(Configuration.getConfiguration().getConf("server-port")) ;
    	String serverIp = Configuration.getConfiguration().getConf("server-address") ;
    	String stubName = Configuration.getConfiguration().getConf("stub-name") ;
    	
    	System.setProperty("java.rmi.server.hostname", serverIp);
        
    	try {
        	Api api = new Api() ;
            ApiInterface stub = (ApiInterface) UnicastRemoteObject.exportObject(api, port);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind(stubName, stub);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

}