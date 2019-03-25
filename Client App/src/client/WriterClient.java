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
            initConnection();
    	    executionStrategy.write(clientId);
        };
    }

    @Override
    public void read() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
