package client;

import jdk.jshell.spi.ExecutionControl;

public class WriterClient extends Client{


    public WriterClient(StrategyType type)
    {
        if(type == StrategyType.SOCKET)
        {
            this.strategy = new SocketStrategy();
        }else{
            this.strategy = new RMIStrategy();
        }
    }

    @Override
    public void write(String news) {
        strategy.write(news);
    }

    @Override
    public void log() {

    }

    @Override
    public String read() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("");
    }
}
