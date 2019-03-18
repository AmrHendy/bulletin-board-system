package execution;

public interface ExecutionStrategy {

    public String read(Client client);

    public void write(String value, Client client);
}
