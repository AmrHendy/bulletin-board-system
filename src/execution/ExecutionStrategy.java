package execution;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.Client;

public interface ExecutionStrategy {

	String read(int port, String stubName, String serverIp, Client client) throws RemoteException, NotBoundException;

	void write(int port, String stubName, String serverIp, Client client, String data) throws RemoteException, NotBoundException;

}
