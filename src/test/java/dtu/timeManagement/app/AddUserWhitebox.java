package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.assertEquals;

public class AddUserWhitebox {
    private final TimeManagementApp timeManagementApp = new TimeManagementApp();
    private final ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();
    private final UserHelper userHelper = new UserHelper();

    public AddUserWhitebox() {

        userHelper.setUser(new User("ABC"));
    }

    @Test
    public void testInputSetA() {
        //Initializing Input set A
        //users contains user "nith"
        try {
            timeManagementApp.addUser(new User("nith"));
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        //user = "nith"
        userHelper.setUser(new User("NITH"));

        //Running method
        try {
            timeManagementApp.addUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        assertEquals(errorMessageHolder.getErrorMessage(), "The user with the given initials is already in the system");
    }

    @Test
    public void testInputSetB() {
        //Initializing Input set B
        //user = "PEANAN"
        userHelper.setUser(new User("PEANAN"));

        //Running method
        try {
            timeManagementApp.addUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        assertEquals(errorMessageHolder.getErrorMessage(), "The users initials are too long");
    }

    @Test
    public void testInputSetC() {
        //Initializing Input set C
        //user = "VIHY"
        userHelper.setUser(new User("VIHY"));

        //Running method
        try {
            timeManagementApp.addUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        Assertions.assertEquals(timeManagementApp.getUser(userHelper.getUser().getInitial()).getInitial(), "VIHY");
    }
}
