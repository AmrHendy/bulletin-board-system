package client;

import jdk.jshell.spi.ExecutionControl;

public class ReaderClient extends Client {

    public ReaderClient(StrategyType type)
    {
        if(type == StrategyType.SOCKET)
        {
            this.strategy = new SocketStrategy();
        }else{
            this.strategy = new RMIStrategy();
        }
    }

    @Override
    public String read() {
        return strategy.read();
    }

    @Override
    public void log() {

    }

    @Override
    public void write(String news) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("");
    }
}
