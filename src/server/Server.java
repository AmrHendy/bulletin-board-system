package server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import communication.CommunicationStrategy;

public class Server {
    private String ipAddress;
    private int portNumber;
    CommunicationStrategy communication = null;

    public Server(String ipAddress, int portNumber, String communicationType){
        // TODO check type and set the obj
    }


    public void start() throws RemoteException, MalformedURLException, AlreadyBoundException{
        communication.run();
    }
}