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

    public ApplicationException(String message) {
        super(message);
    }
}
