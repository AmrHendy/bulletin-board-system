package excution.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import client.Client;
import execution.ExecutionStrategy;

public class RMIStrategy implements ExecutionStrategy {

	
	
    @Override
    public String read(int port, String stubName , String serverIp , Client client) throws RemoteException, NotBoundException {
    	ApiInterface handle = get_handle(port, stubName, serverIp) ;
    	// TODO
    	// String msg = handle.read(client.getId()) ;
    	
    	String msg = "";
    	return msg;
    }

    @Override
    public void write(int port, String stubName , String serverIp , Client client, String data) throws RemoteException, NotBoundException {
    	ApiInterface handle = get_handle(port, stubName, serverIp) ;
    	// TODO
    	// handle.wirte(client.getId(), data) ; ;
    }
    
    
    private ApiInterface get_handle(int port, String stubName , String serverIp ) throws RemoteException, NotBoundException {
    	System.setProperty("java.rmi.server.hostname", serverIp);
        String name = "Board";
        Registry registry = LocateRegistry.getRegistry(serverIp, port);
        return (ApiInterface) registry.lookup(name);
    }
}
