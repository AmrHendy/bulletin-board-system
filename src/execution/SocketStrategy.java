package execution;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.Client;

public class SocketStrategy implements ExecutionStrategy{

	@Override
	public String read(int port, String stubName, String serverIp, Client client)
			throws RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(int port, String stubName, String serverIp, Client client, String data)
			throws RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		
	}

    
	
}
