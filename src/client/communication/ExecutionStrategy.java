package execution;

public interface ExecutionStrategy {

    public String read();

    public void write(String value);
}
