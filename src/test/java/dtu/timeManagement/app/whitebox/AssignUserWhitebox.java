package dtu.timeManagement.app.whitebox;

import dtu.timeManagement.app.*;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Whitebox for assignUser method in Activity-class
 * @see Activity
 * @author Victor Hyltoft (s214964)
 */
public class AssignUserWhitebox {
    private final ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();
    private final Activity activity;

    public AssignUserWhitebox() {
        // Create project with activity
        Project project = new Project(Calendar.getInstance());
        activity = project.createActivity();
    }

    @Test
    public void testInputDataSetA() {
        //Initializing data set:
        User user = null;

        //Running method:
        try {
            activity.assignUser(user);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        // Check exception happened
        assertEquals(errorMessageHolder.getErrorMessage(), "User does not exist");
        // Make sure the user was not assigned anyway
        assertEquals(activity.getUsers().size(), 0);
    }

    @Test
    public void testInputDataSetB() {
        //Initializing data set:
        User user = new User("VIHY");

        try {
            activity.assignUser(user);
        } catch (OperationNotAllowedException e) {
            // No exception should happen here
            fail();
        }
        assertEquals(activity.getUsers().size(), 1);

        //Running method:
        try {
            activity.assignUser(user);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        // Check exception happened
        assertEquals(errorMessageHolder.getErrorMessage(), "User is already assigned to this activity");
        // Make sure the user was not assigned anyway
        assertEquals(activity.getUsers().size(), 1);
    }
    @Test
    public void testInputDataSetC() {
        //Initializing data set:
        User user = new User("VIHY");

        //Running method:
        try {
            activity.assignUser(user);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        // Check (no) exception happened
        assertEquals(errorMessageHolder.getErrorMessage(), "");
        // Make sure the user was assigned
        assertEquals(activity.getUsers().size(), 1);
    }
}
