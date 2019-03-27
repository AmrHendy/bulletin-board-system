package communication.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

import utilities.Logger;

@SuppressWarnings("serial")
public class Api extends UnicastRemoteObject implements ApiInterface {

    private AtomicInteger data , Sseq , Rseq , num_readers ;

    // TODO
    // private Logger log

    public Api() throws RemoteException{
        Sseq = new AtomicInteger(1) ;
        Rseq = new AtomicInteger(1) ;
        num_readers = new AtomicInteger(1) ;
        data = new AtomicInteger(-1) ;
    }

    @Override
    public String read(int id)throws RemoteException{
        int current_readers = num_readers.incrementAndGet() ;
        int currentRseq = Rseq.getAndIncrement();
        int data_g = data.get();
        int sseq_g = Sseq.getAndIncrement();
        
        Logger.logServer("read", sseq_g + "\t\t" + data_g + "\t\t" + String.valueOf(id) + "\t\t" + current_readers + "\n");
        return new String(currentRseq + "," + sseq_g + "," + data_g);
    }

    @Override
    public synchronized String write(int value, int id)throws RemoteException{
    	int currentRseq = Rseq.getAndIncrement();
    	data.set(value);
        int data_g = data.get();
        int sseq_g = Sseq.getAndIncrement();
        
        Logger.logServer("write", sseq_g + "\t\t" + data_g + "\t\t" + id + "\n");
        return new String(currentRseq + "," + sseq_g);
    }

}