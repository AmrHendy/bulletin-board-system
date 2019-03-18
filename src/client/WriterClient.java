package client;

import jdk.jshell.spi.ExecutionControl;

public class WriterClient extends Client{

    @Override
    public void write() {

    }

    @Override
    public void log() {

    }

    @Override
    public void read() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("");
    }
}
