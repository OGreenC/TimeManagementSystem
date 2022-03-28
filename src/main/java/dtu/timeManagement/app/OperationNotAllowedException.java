package dtu.timeManagement.app;

public class OperationNotAllowedException extends Exception {

    public OperationNotAllowedException(String errorMessage) {
        super(errorMessage);
    }
}
