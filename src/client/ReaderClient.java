package client;

import jdk.jshell.spi.ExecutionControl;

public class ReaderClient extends Client {

    @Override
    public void read() {

    }

    @Override
    public void log() {

    }

    @Override
    public void write() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("");
    }
}
