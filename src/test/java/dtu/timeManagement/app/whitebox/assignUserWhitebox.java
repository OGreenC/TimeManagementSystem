package dtu.timeManagement.app.whitebox;

import dtu.timeManagement.app.*;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import io.cucumber.java.bs.A;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Whitebox for assignUser method in Activity-class
 * @see Activity
 * @author Victor Hyltoft (s214964)
 */
public class assignUserWhitebox {
    private final ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();
    private final UserHelper userHelper = new UserHelper();
    private final ActivityHelper activityHelper = new ActivityHelper();

    public assignUserWhitebox() {
        // Create project with activity
        Project project = new Project(Calendar.getInstance());
        activityHelper.setActivity(project.createActivity());
    }

    @Test
    public void testInputDataSetA() {
        //Initializing data set:
        userHelper.setUser(null);

        //Running method:
        try {
            activityHelper.getActivity().assignUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        // Check exception happened
        assertEquals(errorMessageHolder.getErrorMessage(), "User does not exist");
        // Make sure the user was not assigned anyway
        assertEquals(activityHelper.getActivity().getUsers().size(), 0);
    }

    @Test
    public void testInputDataSetB() {
        //Initializing data set:
        userHelper.setUser(new User("VIHY"));
        try {
            activityHelper.getActivity().assignUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            // No exception should happen here
            fail();
        }

        //Running method:
        try {
            activityHelper.getActivity().assignUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        // Check exception happened
        assertEquals(errorMessageHolder.getErrorMessage(), "User is already assigned to this activity");
        // Make sure the user was not assigned anyway
        assertEquals(activityHelper.getActivity().getUsers().size(), 1);
    }
    @Test
    public void testInputDataSetC() {
        //Initializing data set:
        userHelper.setUser(new User("VIHY"));

        //Running method:
        try {
            activityHelper.getActivity().assignUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        // Check (no) exception happened
        assertEquals(errorMessageHolder.getErrorMessage(), "");
        // Make sure the user was assigned
        assertEquals(activityHelper.getActivity().getUsers().size(), 1);
    }
}
