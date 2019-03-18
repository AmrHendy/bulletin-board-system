package communication.socket;

import communication.socket.request.RequestHandler;

public class HandlerSpawner extends Thread{

    private Socket clientSocket;
    public HandlerSpawner(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        // TODO while for batch requests
        RequestHandler.handle(clientSocket);
    }
}
