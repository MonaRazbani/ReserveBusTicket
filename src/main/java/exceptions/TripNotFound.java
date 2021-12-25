package exceptions;

public class TripNotFound extends RuntimeException {
    public TripNotFound() {
        super("Any Trip not found ");
    }
}
