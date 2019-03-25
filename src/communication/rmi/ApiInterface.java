package communication.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ApiInterface extends Remote{

    public String read(int id) throws RemoteException;

    public String write(int value, int id) throws RemoteException;

}