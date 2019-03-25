package server;

import communication.CommunicationStrategy;
import communication.rmi.RMIStrategy;
import communication.socket.SocketStrategy;
import utilities.StrategyType;

public class Server {

    /* local attributes */
    CommunicationStrategy strategy = null;

    /* constructor */
    public Server(StrategyType communicationType){
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