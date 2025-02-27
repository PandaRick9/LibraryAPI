package by.baraznov.util;

public class BookAlreadyExists extends  RuntimeException{
    public BookAlreadyExists(String message) {
        super(message);
    }
}
