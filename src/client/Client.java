package client;

import jdk.jshell.spi.ExecutionControl;

public abstract class Client {

    abstract public void read() throws ExecutionControl.NotImplementedException;

    abstract public void write() throws ExecutionControl.NotImplementedException;

    abstract public void log();

}
