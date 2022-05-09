package dtu.timeManagement.app.whitebox;

import dtu.timeManagement.app.*;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.domains.Activity;
import dtu.timeManagement.app.domains.Project;
import dtu.timeManagement.app.domains.TimeManagementApp;
import dtu.timeManagement.app.domains.User;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Whitebox test for assignUserToActivity method in TimeManagementApp-class
 *
 * @author Victor Hyltoft (s214964)
 * @see TimeManagementApp
 */
public class AssignUserToActivityWhitebox {
    private final TimeManagementApp timeManagementApp = new TimeManagementApp();
    private final ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();
    private Activity activity;

    public AssignUserToActivityWhitebox() {
        // Create project with activity
        Project project = new Project(Calendar.getInstance());
        activity = project.createActivity();
    }

    /**
     * Check for user = null
     */
    @Test
    public void testInputDataSetA() {
        //Initializing data set:
        User user = null;

        // Note: Technically we could check and make sure that activity.users is empty,
        // but because JUnit creates a new instance of the testing class for each @Test
        // it is redundant to check. If we wanted, we could just add the following line;
        // assertEquals(activity.getUsers().size(), 0);

        //Running method:
        try {
            timeManagementApp.assignUserToActivity(user, activity);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        // Check exception happened
        assertEquals(errorMessageHolder.getErrorMessage(), "User does not exist");
        // Make sure the user was not assigned anyway
        assertEquals(activity.getUsers().size(), 0);
    }

    /**
     * Check for activity = null
     */
    @Test
    public void testInputDataSetB() {
        //Initializing data set:
        User user = new User("VIHY");
        activity = null;

        //Running method:
        try {
            timeManagementApp.assignUserToActivity(user, activity);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        // Check exception happened
        assertEquals(errorMessageHolder.getErrorMessage(), "Activity does not exist");

    }

    /**
     * Check for user already assigned to activity
     */
    @Test
    public void testInputDataSetC() {
        //Initializing data set:
        User user = new User("VIHY");

        try {
            timeManagementApp.assignUserToActivity(user, activity);
        } catch (OperationNotAllowedException e) {
            // No exception should happen here
            fail();
        }
        assertEquals(activity.getUsers().size(), 1);

        //Running method:
        try {
            timeManagementApp.assignUserToActivity(user, activity);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        // Check exception happened
        assertEquals(errorMessageHolder.getErrorMessage(), "User is already assigned to this activity");
        // Make sure the user was not assigned to the activity anyway
        assertEquals(activity.getUsers().size(), 1);
    }

    /**
     * Check for successful assignment
     */
    @Test
    public void testInputDataSetD() {
        //Initializing data set:
        User user = new User("VIHY");

        //Running method:
        try {
            timeManagementApp.assignUserToActivity(user, activity);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        // Check (no) exception happened
        assertEquals(errorMessageHolder.getErrorMessage(), "");
        // Make sure the user was assigned to activity
        assertEquals(activity.getUsers().size(), 1);
        // Make sure the activity was added to the user's activities
        assertTrue(user.getActivities().contains(activity));
    }
}
