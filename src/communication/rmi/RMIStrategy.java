package communication.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import communication.CommunicationStrategy;

public class RMIStrategy implements CommunicationStrategy {

    @Override
    public void run(int port, String stubName , String serverIp) throws AlreadyBoundException, RemoteException, MalformedURLException {
        System.setProperty("java.rmi.server.hostname", serverIp);
        Api api = new Api() ;
        ApiInterface stub = (ApiInterface) UnicastRemoteObject.exportObject(api, port);
        Registry registry = LocateRegistry.createRegistry(port);
        registry.bind(stubName, stub);
    }

    @Override
    public void run() throws RemoteException, MalformedURLException, AlreadyBoundException {
        String defaultServerIp = "127.168.1.1";
        int defaultPort = 1900 ;
        String stubName = "Board" ;

        System.setProperty("java.rmi.server.hostname", defaultServerIp);
        Api api = new Api() ;
        ApiInterface stub = (ApiInterface) UnicastRemoteObject.exportObject(api, defaultPort);
        Registry registry = LocateRegistry.createRegistry(defaultPort);
        registry.bind(stubName, stub);
    }

}