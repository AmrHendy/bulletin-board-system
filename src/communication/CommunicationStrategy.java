package communication;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public interface CommunicationStrategy {

    public void run() throws AlreadyBoundException, RemoteException, MalformedURLException;

	void run(int port, String stubName, String serverIp) throws AlreadyBoundException, RemoteException, MalformedURLException;

}
