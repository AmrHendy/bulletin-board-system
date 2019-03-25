package client;

public class WriterClient extends Client{

	public WriterClient() {
		super();
		// TODO Auto-generated constructor stub
	}

    @Override
    public void write() {
    	while(this.accessCount-- > 0) {
        	executionStrategy.write(clientId);
        };
    }

    @Override
    public void log() {

    }

    @Override
    public void read() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
