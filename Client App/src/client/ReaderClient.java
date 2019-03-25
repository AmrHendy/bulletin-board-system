package client;

import utilities.Logger;

public class ReaderClient extends Client {
	
    
    public ReaderClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
    public void read() {
        String logsFileHeader = "rSeq\tsSeq\toVal";
        Logger.logClient(this.clientId, logsFileHeader);
		while(this.accessCount-- > 0) {
			System.out.println(this.accessCount);
			initConnection();
			executionStrategy.read();
        }
    }

    @Override
    public void write() throws UnsupportedOperationException{
    	throw new UnsupportedOperationException() ;
    }
}
