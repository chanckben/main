package seedu.address.model.module.exceptions;

//@@author chanckben
/**
 * Exception to be thrown when a module does not exist.
 */
public class ModuleNotFoundException extends Exception {
    public ModuleNotFoundException(String msg) {
        super(msg);
    }
}
