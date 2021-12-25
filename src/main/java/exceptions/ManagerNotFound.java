package exceptions;

public class ManagerNotFound extends RuntimeException{
    public ManagerNotFound() {
        super("manager not found ");
    }
}
