package client;

public class ReaderClient extends Client {
	
    
    public ReaderClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
    public void read() {
        while(this.accessCount-- > 0) {
        	executionStrategy.read();
        }
    }

    @Override
    public void log() {
    	
    }

    @Override
    public void write() throws UnsupportedOperationException{
    	throw new UnsupportedOperationException() ;
    }
}
