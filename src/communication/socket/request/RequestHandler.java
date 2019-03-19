package communication.socket.request;

import java.net.Socket;

import org.omg.CORBA.Request;

public class RequestHandler {
    private static RequestHandler requestHandlerInstance = null;

    private RequestHandler(){

    }

    public static RequestHandler getRequestHandlerInstance(){
        if(requestHandlerInstance == null){
            requestHandlerInstance = new RequestHandler();
        }
        return requestHandlerInstance;
    }

    public static void handle(Socket socket){
        // TODO read request
    }

    private void handleRead(Request request){

    }

    private synchronized void handleWrite(Request request){

    }
}
