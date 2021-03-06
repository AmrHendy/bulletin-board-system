package communication.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import configuration.Configuration;
import utilities.Logger;
import communication.ExecutionStrategy;

public class RMIStrategy implements ExecutionStrategy {

	private int port,clientId ;
	private String serverIp,stubName ;
	private ApiInterface handle;

	public RMIStrategy() {
		super();
		port = Integer.valueOf(Configuration.getConfiguration().getConf("server-port")) ;
		serverIp = Configuration.getConfiguration().getConf("server-address") ;
		stubName = Configuration.getConfiguration().getConf("stub-name") ;
		clientId = Integer.valueOf(Configuration.getConfiguration().getConf("client-id")) ;		
		try {
			this.handle = get_handle(port, stubName, serverIp);
			System.out.println("client " + clientId +  " is connected to server : " +  serverIp + " on port : " + port + " on stub : " + stubName);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read(){
		try {
			String msg = handle.read(clientId) ;
			Logger.logClient(this.clientId, msg);
		} catch (NullPointerException | RemoteException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void write(int data){
		try {
			String msg = handle.write(clientId, data) ;
            Logger.logClient(this.clientId, msg);
		} catch (RemoteException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	private ApiInterface get_handle(int port, String stubName , String serverIp ) throws RemoteException, NotBoundException {
		System.setProperty("java.rmi.server.hostname", serverIp);
		Registry registry = LocateRegistry.getRegistry(serverIp, port);
		return (ApiInterface) registry.lookup(stubName);
	}
}