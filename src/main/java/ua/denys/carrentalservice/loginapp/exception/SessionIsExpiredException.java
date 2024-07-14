package ua.denys.carrentalservice.loginapp.exception;

public class SessionIsExpiredException extends Exception{
    public SessionIsExpiredException(String message) {
        super(message);
    }
}
