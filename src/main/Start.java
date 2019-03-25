package main;

public class Start {

    private static void runClients(){
        /* reader client */
        Client c1 = new ReaderClient(StrategyType.SOCKET);
        String news = c1.read();
        System.log.print(news);

        /* writer client */
        String someNews = "hello world";
        Client c2 = new WriterClient(StrategyType.SOCKET);
        c2.write(someNews);
        System.log.print(someNews);
    }

    private static void runServer(){
        Server s = new Server(19216811, 7000, StrategyType.SOCKET)
        s.run();
    }

    public static void main(String args[]){
        String[] data = readConfigFile();
        runServer();
        runClients();
    }

}