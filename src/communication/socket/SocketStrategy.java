package communication.socket;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

import communication.CommunicationStrategy;
import communication.socket.request.RequestHandler;
import configuration.Configuration;
import message.Message;
import message.RequestType;

public class SocketStrategy implements CommunicationStrategy {

    private ServerSocket serverSocket;
    private Integer requestSeq;
    private Integer serviceSeq;
    private Integer newsValue;
    private Integer currentReaders;

    public SocketStrategy() {
        // Make sure that we create an instance of requestHandler at first to avoid
        // multiple instances in multi-threading
        RequestHandler requestHandler = RequestHandler.getRequestHandlerInstance();
        int serverPort = Integer.parseInt(Configuration.getConfiguration().getConf("server-port"));
        serverSocket = new ServerSocket(serverPort);
        // initialize the server values
        requestSeq = serviceSeq = 1;
        currentReaders = 0;
        newsValue = -1;
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

    private class HandlerSpawner extends Thread {
        private Socket requestSocket;

        public HandlerSpawner(Socket requestSocket) {
            this.requestSocket = requestSocket;
        }

        @Override
        public void run() {
            BufferedReader in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
            PrintWriter out = new PrintWriter(requestSocket.getOutputStream(), true);
            StringBuilder stringBuilder = new StringBuilder();
            // recieved the request, so store the request seq
            synchronized (requestSeq) {
                stringBuilder.append(requestSeq);
                requestSeq++;
            }
            // the request message must be on the following pattern:
            // clientId [space] type [space] value
            // in case of read request it will be: clientId [space] read [space] empty
            // in case of write request it will be: clientId [space] write [space] value
            String request = in.readLine();
            String[] tokens = request.split(" ");
            String clientId = token[0];
            String requestType = tokens[1];
            String value = (tokens.length > 2 ? tokens[2] : null);

            Message requestMessage;
            if (tokens[1].equalsIgnoreCase("read")) {
                synchronized (currentReaders) {
                    currentReaders++;
                }
                requestMessage = new Message(RequestType.READ, value);
            } else if (tokens[1].equalsIgnoreCase("write")) {
                requestMessage = new Message(RequestType.WRITE, value);
            } else {
                System.err.println("Error, not supported request type");
            }

            // handling the request
            RequestHandler.handle(clientSocket, newsValue);
            // finished serving the request
            if (tokens[1].equalsIgnoreCase("read")) {
                synchronized (currentReaders) {
                    currentReaders--;
                }
            }
            stringBuilder.append(" ");
            synchronized (serviceSeq) {
                stringBuilder.append(serviceSeq);
                serviceSeq++;
            }
            stringBuilder.append(" ");
            synchronized (newsValue) {
                stringBuilder.append(newsValue);
            }
            // send the response as following: rSeq [space] sSeq [space] value ["\n"]
            out.println(stringBuilder.toString());

            // server logging

        }
    }
}
