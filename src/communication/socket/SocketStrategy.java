package communication.socket;

import java.net.ServerSocket;

import communication.CommunicationStrategy;
import communication.socket.request.RequestHandler;
import configuration.Configuration;

public class SocketStrategy implements CommunicationStrategy {

    private ServerSocket serverSocket;

    public SocketStrategy() {
        // Make sure that we create an instance of requestHandler at first to avoid
        // multiple instances in multi-threading
        RequestHandler requestHandler = RequestHandler.getRequestHandlerInstance();
        int serverPort = Integer.parseInt(Configuration.getConfiguration().getConf("server-port"));
        serverSocket = new ServerSocket(serverPort);
    }

    @Override
    public void run() {
        int totalRequests = Integer.parseInt(Configuration.getConfiguration().getConf("total-requests"));
        while (totalRequests--) {
            // accept the new request and automatically open a new socket
            Socket requestSocket = serverSocket.accept();
            Thread requestThread = new HandlerSpawner(requestSocket);
            requestThread.start();
        }
    }
}
