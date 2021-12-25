package exceptions;

public class BusNotFound extends RuntimeException{
    public BusNotFound() {
        super("bus not found ");
    }
}
