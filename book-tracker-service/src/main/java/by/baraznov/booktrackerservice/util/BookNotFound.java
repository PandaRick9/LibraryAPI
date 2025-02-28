package by.baraznov.booktrackerservice.util;

public class BookNotFound extends RuntimeException{
    public BookNotFound(String message) {
        super(message);
    }
}
