package server;



public class Server {
    private String ipAddress;
    private int portNumber;
    CommunicationStrategy communication = null;

    public Server(String ipAddress, int portNumber, String communicationType){
        // TODO check type and set the obj
    }


    public void start(){
        communication.run();
    }
}