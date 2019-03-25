package execution.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("serial")
public class Api extends UnicastRemoteObject implements ApiInterface {

    private AtomicInteger Sseq , num_readers ;
    private String data ;

    // TODO
    // private Logger log

    public Api() throws RemoteException{
        super();
        Sseq = new AtomicInteger(1) ;
        num_readers = new AtomicInteger(1) ;
    }


    @Override
    public synchronized String read(int id) throws RemoteException {
        int current_readers = num_readers.incrementAndGet() ;
        // TODO
        // log.log_reader(Sseq + "\t\t" + value + "\t\t" + id + "\t\t" + current_readers + "\n")
        return new String("currentRseq" + "," + Sseq.getAndIncrement() + "," + data);
    }

    @Override
    public synchronized String write(int value, int id) throws RemoteException {
        // TODO
        // log.log_reader(Sseq + "\t\t" + value + "\t\t" + id + "\n")
        return new String("currentRseq" + "," + Sseq.getAndIncrement() + "," + data);
    }

}