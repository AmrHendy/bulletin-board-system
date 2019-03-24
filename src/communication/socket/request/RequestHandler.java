package communication.socket.request;

public class RequestHandler {
    private static requestHandlerInstance = null;

    private RequestHandler(){
        
    }

    public static RequestHandler getRequestHandlerInstance() {
        if (requestHandlerInstance == null) {
            requestHandlerInstance = new RequestHandler();
        }
        return requestHandlerInstance;
    }

    public static void handle(Socket socket) {
        // TODO read request
    }

    private void handleRead(Request request) {

    }

    private synchronized void handleWrite(Request request) {

    }
}
