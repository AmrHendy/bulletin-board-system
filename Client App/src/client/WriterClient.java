package client;

import utilities.Logger;

public class WriterClient extends Client{

	public WriterClient() {
		super();
		// TODO Auto-generated constructor stub
	}

    @Override
    public void write() {
        String logsFileHeader = "rSeq\tsSeq";
        Logger.logClient(this.clientId, logsFileHeader);
	    while(this.accessCount-- > 0) {
            sleep();
	        initConnection();
    	    executionStrategy.write(clientId);
            System.out.println("Finished request " + String.valueOf(this.accessCount + 1));
        };
    }

    @Override
    public void read() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
