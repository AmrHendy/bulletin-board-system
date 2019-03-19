package communication.socket;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import communication.CommunicationStrategy;
import communication.socket.request.RequestHandler;

public class SocketStrategy implements CommunicationStrategy {

    private RequestHandler requestHandler;

    public SocketStrategy(){
        requestHandler = RequestHandler.getRequestHandlerInstance();
    }

    @Override
    public void run() {
        while(true){
            // TODO listen on requests and create threads
        }
    }

	@Override
	public void run(int port, String stubName, String serverIp) throws RemoteException, MalformedURLException {
		// TODO Auto-generated method stub
		
	}

}
