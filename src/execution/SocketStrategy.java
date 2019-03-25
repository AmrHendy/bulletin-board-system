package execution;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import configuration.Configuration;
import java.io.InputStreamReader;

public class SocketStrategy implements ExecutionStrategy {

    private Socket clientSocket;
    private int clientId;
    private BufferedReader socketIn;
    private PrintWriter socketOut;

    public SocketStrategy() {
        String serverAddress = Configuration.getConfiguration().getConf("server-address");
        int clientPort = Configuration.getConfiguration().getConf("current-port");
        this.clientId = Configuration.getConfiguration().getConf("client");
        clientSocket = new Socket(serverAddress, clientPort);
        socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        socketOut = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    @Override
    public String read() {
        // send read request
        // in case of read request it will be: clientId [tab] read [tab] empty
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(clientId);
        stringBuilder.append("\t");
        stringBuilder.append("read");
        stringBuilder.append("\t");
        stringBuilder.append("empty");
        socketOut.println(stringBuilder.toString());

        // recieve the responce from the server
        // the response as following: rSeq [tab] sSeq [tab] value ["\n"]
        String response = socketIn.readLine();
        String[] responseTokens = response.split("\t");
        int rSeq = Integer.parseInt(responseTokens[0]);
        int sSeq = Integer.parseInt(responseTokens[1]);
        int value = Integer.parseInt(responseTokens[2]);

        System.out.println(rSeq + " " + sSeq + " " + value);
    }

    // value = clientId sent from the client
    @Override
    public void write(String value) {
        // send read request
        // in case of read request it will be: clientId [tab] read [tab] empty
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(clientId);
        stringBuilder.append("\t");
        stringBuilder.append("write");
        stringBuilder.append("\t");
        stringBuilder.append(value);
        socketOut.println(stringBuilder.toString());

        // recieve the responce from the server
        // the response as following: rSeq [tab] sSeq [tab] value ["\n"]
        String response = socketIn.readLine();
        String[] responseTokens = response.split("\t");
        int rSeq = Integer.parseInt(responseTokens[0]);
        int sSeq = Integer.parseInt(responseTokens[1]);

        System.out.println(rSeq + " " + sSeq);
    }
}
