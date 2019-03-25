package server;

import client.communication.CommunicationStrategy;
import client.communication.rmi.RMIStrategy;
import client.communication.socket.SocketStrategy;
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