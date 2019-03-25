package client;


import configuration.Configuration;
import execution.ExecutionStrategy;
import execution.SocketStrategy;
import execution.rmi.RMIStrategy;

public abstract class Client {
	protected ExecutionStrategy executionStrategy;
	protected int clientId;
	protected int accessCount;
	
	public Client() {
    	String type = Configuration.getConfiguration().getConf("type");
    	int id =  Integer.valueOf(Configuration.getConfiguration().getConf("client-id"));
    	int accessCount = Integer.valueOf(Configuration.getConfiguration().getConf("access-count"));
    	if(type.equalsIgnoreCase("socket"))
        {
    		this.executionStrategy = new SocketStrategy();
        }else{
            this.executionStrategy = new RMIStrategy();
        }
    	
    	this.accessCount = accessCount ;
    	this.clientId = id ;
    }
	
    abstract public void read() throws UnsupportedOperationException;

    abstract public void write() throws UnsupportedOperationException;

    abstract public void log();

}
