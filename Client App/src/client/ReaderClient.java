package client;

import utilities.Logger;

import java.util.Random;

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
            sleep();
			initConnection();
			executionStrategy.read();
            System.out.println("Finished request " + String.valueOf(this.accessCount + 1));
            System.out.println("=================================================================");
        }
    }

    @Override
    public void write() throws UnsupportedOperationException{
    	throw new UnsupportedOperationException() ;
    }
}
