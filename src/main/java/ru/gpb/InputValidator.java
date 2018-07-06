package ru.gpb;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Validator for input arguments
 *
 * @author Danil Popov
 */
public class InputValidator {
    public void validate(String[] args) {
        if (args == null) {
            throw new ApplicationException();
        }
        if (!(isValidArgs(args) || isHelp(args))) {
            throw new ApplicationException();
        }
    }

    private boolean isValidArgs(String[] args) {
        return args.length == 3 && NumberUtils.isDigits(args[1]);
    }

    private boolean isHelp(String[] args) {
        return (args.length == 1 && "--help".equals(args[0]));
    }
}
