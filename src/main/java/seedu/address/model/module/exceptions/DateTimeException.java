package seedu.address.model.module.exceptions;

//@@author wanxuanong
/**
 * Exception thrown when there is an input error of date and time.
 */
public class DateTimeException extends Exception {
    public DateTimeException (String message) {
        super(message);
    }
}
