package communication;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import configuration.Configuration;
import utilities.Logger;

public class SocketStrategy implements ExecutionStrategy {

    private Socket clientSocket;
    private int clientId;
    private DataInputStream socketIn;
    private DataOutputStream socketOut;

    public SocketStrategy() {
        String serverAddress = Configuration.getConfiguration().getConf("server-address");
        int serverPort = Integer.valueOf(Configuration.getConfiguration().getConf("server-port")) ;
        this.clientId = Integer.valueOf(Configuration.getConfiguration().getConf("client-id")) ;
        try {
        	System.out.println(serverAddress + "\t" + serverPort);
            InetAddress serverAddressNet = InetAddress.getByName(serverAddress);
            clientSocket = new Socket(serverAddressNet, serverPort);
	        socketIn = new DataInputStream(clientSocket.getInputStream());
	        socketOut = new DataOutputStream(clientSocket.getOutputStream());
	        System.out.println("successfully connected to server");
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void read() {
    	// send read request
        // in case of read request it will be: clientId [tab] read [tab] empty
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(clientId);
        stringBuilder.append("\t");
        stringBuilder.append("read");
        stringBuilder.append("\t");
        stringBuilder.append("empty");
		try {
		    socketOut.writeUTF(stringBuilder.toString());
            System.out.println("client sent the read request to the server socket");
	        // recieve the responce from the server
	        // the response as following: rSeq [tab] sSeq [tab] value ["\n"]
	        String response = socketIn.readUTF();
            System.out.println("client received the read response from the server socket");
	    	System.out.println("response = " + response);
	    	// parse the response
	        String[] responseTokens = response.split("\t");
	        int rSeq = Integer.parseInt(responseTokens[0]);
	        int sSeq = Integer.parseInt(responseTokens[1]);
	        int value = Integer.parseInt(responseTokens[2]);
            // client logging
	        Logger.logClient(this.clientId, response);
            socketIn.close();
            socketOut.close();
            clientSocket.close();
		} catch (IOException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // value = clientId sent from the client
    @Override
    public void write(int value) {
        // send read request
        // in case of read request it will be: clientId [tab] read [tab] empty
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(clientId);
        stringBuilder.append("\t");
        stringBuilder.append("write");
        stringBuilder.append("\t");
        stringBuilder.append(value);
        try {
	        socketOut.writeUTF(stringBuilder.toString());
            System.out.println("client sent the write request to the server socket");
            // recieve the responce from the server
	        // the response as following: rSeq [tab] sSeq [tab] value ["\n"]
	        String response = socketIn.readUTF();
            System.out.println("client received the write response from the server socket");
            // parse the response
            String[] responseTokens = response.split("\t");
	        int rSeq = Integer.parseInt(responseTokens[0]);
	        int sSeq = Integer.parseInt(responseTokens[1]);
            // client logging
            Logger.logClient(this.clientId, response);
            socketIn.close();
            socketOut.close();
            clientSocket.close();
        } catch (IOException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
