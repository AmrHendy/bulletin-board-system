package communication.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("serial")
public class Api extends UnicastRemoteObject implements ApiInterface {

    private AtomicInteger Sseq , Rseq , num_readers ;
    private int data = -1 ;


    // TODO
    // private Logger log

    public Api() throws RemoteException{
        Sseq = new AtomicInteger(1) ;
        Rseq = new AtomicInteger(1) ;
        num_readers = new AtomicInteger(1) ;
    }


    @Override
    public String read(int id)throws RemoteException{
        int current_readers = num_readers.incrementAndGet() ;
        int currentRseq = Rseq.getAndIncrement();
        // TODO
        // log.log_reader(Sseq + "\t\t" + data + "\t\t" + id + "\t\t" + current_readers + "\n")
        return new String(currentRseq + "," + Sseq.getAndIncrement() + "," + data);
    }

    @Override
    public synchronized String write(int value, int id)throws RemoteException{
        int currentRseq = Rseq.getAndIncrement();
        // TODO
        // log.log_reader(Sseq + "\t\t" + value + "\t\t" + id + "\n")
        return new String(currentRseq + "," + Sseq.getAndIncrement() + "," + data);
    }

}