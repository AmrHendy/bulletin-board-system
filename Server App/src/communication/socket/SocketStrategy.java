package communication.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import communication.CommunicationStrategy;
import communication.socket.request.RequestHandler;
import configuration.Configuration;
import message.Message;
import utilities.RequestType;

public class SocketStrategy implements CommunicationStrategy {

    private ServerSocket serverSocket;
    private Integer requestSeq;
    private Integer serviceSeq;
    private Integer newsValue;
    private Integer currentReaders;

    private List<String> serverReadLogs;
    private List<String> serverWriteLogs;

    public SocketStrategy() {
        // Make sure that we create an instance of requestHandler at first to avoid
        // multiple instances in multi-threading
        RequestHandler requestHandler = RequestHandler.getRequestHandlerInstance();
        int serverPort = Integer.parseInt(Configuration.getConfiguration().getConf("server-port"));
        
        try {
			serverSocket = new ServerSocket(serverPort);
			System.out.println("Starting Server Using Socket");
        } catch (IOException e) {
			e.printStackTrace();
		}
        
        // initialize the server values
        requestSeq = serviceSeq = 1;
        currentReaders = 0;
        newsValue = -1;
        // initialize the server logs
        serverReadLogs = new ArrayList<>();
        serverWriteLogs = new ArrayList<>();

        serverReadLogs.add("sSeq\toVal\trID\trNum\n");
        serverReadLogs.add("sSeq\toVal\twID\n");
    }

    @Override
    public void run() {
        int totalRequests = Integer.parseInt(Configuration.getConfiguration().getConf("total-requests"));
        while (totalRequests-- > 0) {
            // accept the new request and automatically open a new socket
            Socket requestSocket;
			try {
				requestSocket = serverSocket.accept();
				System.out.println("Server received request");
				Thread requestThread = new HandlerSpawner(requestSocket);
				System.out.println("Server created request spawner");
				requestThread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    private class HandlerSpawner extends Thread {
        private Socket requestSocket;

        public HandlerSpawner(Socket requestSocket) {
            this.requestSocket = requestSocket;
        }

        @Override
        public void run() {
            	
        	try {		
        		DataInputStream in = new DataInputStream(requestSocket.getInputStream()); ;
        		System.out.println("100");
				DataOutputStream out = new DataOutputStream(requestSocket.getOutputStream());
        		// string builder to store the client log
        		System.out.println("101");
        		StringBuilder stringBuilder = new StringBuilder();
        		// recieved the request, so store the request seq
        		synchronized (requestSeq) {
        			stringBuilder.append(requestSeq);
        			requestSeq++;
        		}
        		System.out.println("102");
        		// the request message must be on the following pattern:
        		// clientId [space] type [space] value
        		// in case of read request it will be: clientId [space] read [space] empty
        		// in case of write request it will be: clientId [space] write [space] value
        		String request = in.readUTF();
        		String[] tokens = request.split("\t");
        		String clientId = tokens[0];
        		String requestType = tokens[1];
        		String value = (tokens.length > 2 ? tokens[2] : null);
        		
        		System.out.println(request);
        		
        		Message requestMessage ;
        		if (requestType.equalsIgnoreCase("read")) {
        			System.out.println("103");
        			synchronized (currentReaders) {
        				currentReaders++;
        			}
        			requestMessage = new Message(RequestType.READ, -1);
        		} else if (requestType.equalsIgnoreCase("write")) {
        			requestMessage = new Message(RequestType.WRITE, Integer.valueOf(value));
        		} else {
        			System.err.println("Error, not supported request type");
        			return ;
        		}

        		System.out.println("received the request");
        		// handling the request
        		RequestHandler.handle(requestMessage, newsValue);
				System.out.println("handled the request");

				stringBuilder.append("\t");
        		synchronized (serviceSeq) {
        			stringBuilder.append(serviceSeq);
        			serviceSeq++;
        		}
        		stringBuilder.append("\t");
        		synchronized (newsValue) {
        			stringBuilder.append(newsValue);
        		}
        		// send the response as following: rSeq [tab] sSeq [tab] value ["\n"]
        		out.writeUTF(stringBuilder.toString());
        		System.out.println("response sent to client = " + stringBuilder.toString());
        		// server logging
        		String[] logTokens = stringBuilder.toString().split("\t");
        		stringBuilder = new StringBuilder();
        		stringBuilder.append(logTokens[1]);
        		stringBuilder.append("\t");
        		stringBuilder.append(logTokens[2]);
        		stringBuilder.append("\t");
        		stringBuilder.append(logTokens[0]);
        		if (tokens[1].equalsIgnoreCase("read")) {
        			stringBuilder.append("\t");
        			synchronized (currentReaders) {
        				stringBuilder.append(currentReaders);
        				currentReaders--;
        			}
        			stringBuilder.append("\n");
        			synchronized (serverReadLogs) {
        				serverReadLogs.add(stringBuilder.toString());
        			}
        		} else if (tokens[1].equalsIgnoreCase("write")) {
        			stringBuilder.append("\n");
        			synchronized (serverWriteLogs) {
        				serverWriteLogs.add(stringBuilder.toString());
        			}
        		}

        		in.close();
        		out.close();
        		requestSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }        	
    }
}
