package cl.bci.login.exception;

public class ValidationException extends Exception {
    public ValidationException(String error) {
        super(error);
    }
}