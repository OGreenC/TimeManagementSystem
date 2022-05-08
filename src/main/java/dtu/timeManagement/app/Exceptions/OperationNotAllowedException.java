package dtu.timeManagement.app.Exceptions;

/**
 * @author Niels Thormann (s216160)
 */
public class OperationNotAllowedException extends Exception {
    public OperationNotAllowedException(String errorMessage) {
        super(errorMessage);
    }
}
