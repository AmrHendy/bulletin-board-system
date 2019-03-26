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
import utilities.Logger;
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
			System.out.println("Starting Server Using Socket on port " + serverPort);
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

        serverReadLogs.add("sSeq\toVal\trID\trNum");
        serverReadLogs.add("sSeq\toVal\twID");
		Logger.logServer("read", "Readers");
		Logger.logServer("read", "sSeq\toVal\trID\trNum");
		Logger.logServer("write", "Writers");
		Logger.logServer("write", "sSeq\toVal\twID");
    }

    @Override
    public void run() {
        int totalRequests = Integer.parseInt(Configuration.getConfiguration().getConf("total-requests"));
        while (totalRequests-- > 0) {
            // accept the new request and automatically open a new socket
            Socket requestSocket;
			try {
				requestSocket = serverSocket.accept();
				System.out.println("Server accepted the request");
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
				DataOutputStream out = new DataOutputStream(requestSocket.getOutputStream());
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
        		String request = in.readUTF();
        		String[] tokens = request.split("\t");
        		String clientId = tokens[0];
        		String requestType = tokens[1];
        		String value = (tokens.length > 2 ? tokens[2] : null);

				System.out.println("request = " + request);
        		
        		Message requestMessage ;
        		if (requestType.equalsIgnoreCase("read")) {
        			synchronized (currentReaders) {
        				currentReaders++;
        			}
        			requestMessage = new Message(RequestType.READ, -1);
        		} else if (requestType.equalsIgnoreCase("write")) {
        			requestMessage = new Message(RequestType.WRITE, Integer.valueOf(value));
        		} else {
        			System.err.println("Error, not supported request type");
        			return;
        		}

        		System.out.println("Server received the client request");
        		// handling the request
        		RequestHandler.handle(requestMessage, newsValue);
				System.out.println("Server handled the request");

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
        		System.out.println("Server sent the response to the client");
				System.out.println("response = " + stringBuilder.toString());
				System.out.println("====================================================");

				// server logging
        		String[] logTokens = stringBuilder.toString().split("\t");
        		stringBuilder = new StringBuilder();
        		stringBuilder.append(logTokens[1]);
        		stringBuilder.append("\t\t");
        		stringBuilder.append(logTokens[2]);
        		stringBuilder.append("\t\t");
        		stringBuilder.append(clientId);
        		if (tokens[1].equalsIgnoreCase("read")) {
        			stringBuilder.append("\t\t");
        			synchronized (currentReaders) {
						stringBuilder.append(currentReaders);
						currentReaders--;
					}
        			synchronized (serverReadLogs) {
        				serverReadLogs.add(stringBuilder.toString());
						Logger.logServer("read", stringBuilder.toString());
        			}
        		} else if (tokens[1].equalsIgnoreCase("write")) {
        			synchronized (serverWriteLogs) {
        				serverWriteLogs.add(stringBuilder.toString());
						Logger.logServer("write", stringBuilder.toString());
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
