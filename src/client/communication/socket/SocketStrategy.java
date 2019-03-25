package client.communication.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import client.communication.CommunicationStrategy;
import client.communication.socket.request.RequestHandler;
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
				Thread requestThread = new HandlerSpawner(requestSocket);
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
        		BufferedReader in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
        		PrintWriter out = new PrintWriter(requestSocket.getOutputStream(), true);
        		// string builder to store the client log
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
        		String[] tokens = request.split("\t");
        		String clientId = tokens[0];
        		String requestType = tokens[1];
        		String value = (tokens.length > 2 ? tokens[2] : null);
        		
        		Message requestMessage ;
        		if (tokens[1].equalsIgnoreCase("read")) {
        			synchronized (currentReaders) {
        				currentReaders++;
        			}
        			requestMessage = new Message(RequestType.READ, Integer.valueOf(value));
        		} else if (tokens[1].equalsIgnoreCase("write")) {
        			requestMessage = new Message(RequestType.WRITE, Integer.valueOf(value));
        		} else {
        			System.err.println("Error, not supported request type");
        			return ;
        		}
        		
        		// handling the request
        		RequestHandler.handle(requestMessage, newsValue);
        		
        		stringBuilder.append("\t");
        		synchronized (serviceSeq) {
        			stringBuilder.append(serviceSeq);
        			serviceSeq++;
        		}
        		stringBuilder.append("\t");
        		synchronized (newsValue) {
        			stringBuilder.append(newsValue);
        		}
        		stringBuilder.append("\n");
        		// send the response as following: rSeq [tab] sSeq [tab] value ["\n"]
        		out.println(stringBuilder.toString());
        		
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
			} catch (Exception e) {
				e.printStackTrace();
			}
        }        	
    }
}
