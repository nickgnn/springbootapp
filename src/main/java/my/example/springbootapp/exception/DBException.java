package my.example.springbootapp.exception;

public class DBException extends Exception {
    public DBException (Throwable throwable) {
        super(throwable);
    }
}

