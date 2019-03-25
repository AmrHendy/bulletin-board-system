package communication;

public interface ExecutionStrategy {

    public void read();

    public void write(int value);
}
