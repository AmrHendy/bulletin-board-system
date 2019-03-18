package server;

public class Server {

	/* local attributes */
    private String ipAddress;
    private int portNumber;
    CommunicationStrategy strategy = null;

    /* constructor */
    public Server(String ipAddress, int portNumber, StrategyType communicationType){
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
        if(communicationType == StrategyType.SOCKET){
            this.strategy = new SocketStrategy();
        }else{
            this.strategy = new RMIStrategy();
        }
    }

    /* interface methods */
    public void start(){
        strategy.run();
    }
}