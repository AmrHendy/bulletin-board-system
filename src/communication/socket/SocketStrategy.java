package communication.socket;

import communication.CommunicationStrategy;
import communication.socket.request.RequestHandler;

public class SocketStrategy implements CommunicationStrategy {

    private RequestHandler requestHandler;

    public SocketStrategy(){
        requestHandler = RequestHandler.getRequestHandlerInstance();
    }

    @Override
    public void run() {
        while(1){
            // TODO listen on requests and create threads
        }
    }
}
