package dtu.timeManagement.app.Exceptions;

public class OperationNotAllowedException extends Exception {

    public OperationNotAllowedException(String errorMessage) {
        super(errorMessage);
    }
}
