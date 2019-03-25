package client;

public class ReaderClient extends Client {
	
    
    public ReaderClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
    public void read() {
		while(this.accessCount-- > 0) {
			System.out.println(this.accessCount);
			initConnection();
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
