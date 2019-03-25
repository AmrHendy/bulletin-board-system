package client;


import configuration.Configuration;
import communication.ExecutionStrategy;
import communication.SocketStrategy;
import communication.rmi.RMIStrategy;

public abstract class Client {
	protected ExecutionStrategy executionStrategy;
	protected int clientId;
	protected int accessCount;
	private String type;

	public Client() {
    	this.type = Configuration.getConfiguration().getConf("type");
    	int id =  Integer.valueOf(Configuration.getConfiguration().getConf("client-id"));
    	int accessCount = Integer.valueOf(Configuration.getConfiguration().getConf("access-count"));
    	this.accessCount = accessCount ;
    	this.clientId = id ;
    }
	
    abstract public void read() throws UnsupportedOperationException;

    abstract public void write() throws UnsupportedOperationException;

    void initConnection(){
		if(type.equalsIgnoreCase("socket"))
		{
			this.executionStrategy = new SocketStrategy();
		}else {
			this.executionStrategy = new RMIStrategy();
		}
	}
}