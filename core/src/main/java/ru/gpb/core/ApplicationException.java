package ru.gpb.core;

/**
 * Base application exception
 *
 * @author Danil Popov
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException() {
        super("Please look at the help page(--help)");
    }

    public ApplicationException(Exception e) {
        super("Please look at the help page(--help)", e);
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Exception e) {
        super(message, e);
    }
}
