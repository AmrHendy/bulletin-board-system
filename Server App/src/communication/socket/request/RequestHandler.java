package communication.socket.request;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import message.Message;

public class RequestHandler {
    private static RequestHandler requestHandlerInstance = null;
    private static ReentrantReadWriteLock lock;

    private RequestHandler() {
        lock = new ReentrantReadWriteLock();
    }

    public static RequestHandler getRequestHandlerInstance() {
        if (requestHandlerInstance == null) {
            requestHandlerInstance = new RequestHandler();
        }
        return requestHandlerInstance;
    }

    public static void handle(Message requestMessage, Integer newsValue) {
        switch (requestMessage.getRequestType()) {
        case READ:
            handleRead(requestMessage, newsValue);
            break;
        case WRITE:
            handleWrite(requestMessage, newsValue);
            break;
        default:
            System.err.println("Error, not supported request type");
        }
    }

    private static void handleRead(Message requestMessage, Integer newsValue) {
        System.out.println("starting handle read");
        lock.readLock().lock();
        int currentValue = newsValue;
        lock.readLock().unlock();
        System.out.println("finish handle read");
    }

    private static synchronized void handleWrite(Message requestMessage, Integer newsValue) {
        System.out.println("starting handle write");
        lock.writeLock().lock();
        newsValue = requestMessage.getValue();
        lock.writeLock().unlock();
        System.out.println("finish handle write");
    }
}
