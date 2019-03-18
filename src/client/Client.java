package client;

import jdk.jshell.spi.ExecutionControl;

public abstract class Client
{

	/* 01. attributes */
	protected ExecutionStrategy strategy;

	/* 02. methods */
    abstract public String read() throws ExecutionControl.NotImplementedException;

    abstract public void write(String news) throws ExecutionControl.NotImplementedException;

    abstract public void log();

}
