package communication.socket;

import communication.CommunicationStrategy;
import communication.socket.request.RequestHandler;

public class SocketStrategy implements CommunicationStrategy {

    /* local attributes */
    private RequestHandler requestHandler;
    private final int PORT = 8000;

    /* constructor */
    public SocketStrategy(){
        requestHandler = RequestHandler.getRequestHandlerInstance();
    }

    /* interface methods */
    @Override
    public void run() {
        // server is listening on port 5056 
        ServerSocket ss = new ServerSocket(PORT); 
        
        // running infinite loop for getting 
        // client request 
        while (true) 
        { 
            Socket s = null; 
            try
            { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                
                System.out.println("A new client is connected : " + s); 
                
                // obtaining input and out streams 
                //DataInputStream dis = new DataInputStream(s.getInputStream()); 
                //DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                
                System.out.println("Assigning new thread for this client"); 

                // create a new thread object 
                Thread t = new HandlerSpawner(s); 

                // Invoking the start() method 
                t.start(); 
                
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        }
    }
}
