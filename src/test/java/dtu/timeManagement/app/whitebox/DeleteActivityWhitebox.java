package dtu.timeManagement.app.whitebox;

import dtu.timeManagement.app.*;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import jdk.dynalink.Operation;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteActivityWhitebox {
    private final ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();

    private final ProjectHelper projectHelper = new ProjectHelper();

    private final ActivityHelper activityHelper = new ActivityHelper();

    private final TimeManagementApp timeManagementApp = new TimeManagementApp();

    private final Calendar date = Calendar.getInstance();


    @Test
    public void testInputDataSetA() {
        try {
            timeManagementApp.deleteActivity(projectHelper.getProject(),activityHelper.getActivity());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertEquals(errorMessageHolder.getErrorMessage(),"Project does not exist");
    }

    @Test
    public void testInputDataSetB() {
        projectHelper.setProject(new Project(date));
        try {
            timeManagementApp.deleteActivity(projectHelper.getProject(),activityHelper.getActivity());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertEquals(errorMessageHolder.getErrorMessage(),"Activity does not exist");
    }

    @Test
    public void testInputDataSetC() {
        projectHelper.setProject(new Project(date));
        projectHelper.getProject().createActivity();
        try {
            timeManagementApp.deleteActivity(projectHelper.getProject(),projectHelper.getProject().getActivity("0001"));
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertNull(projectHelper.getProject().getActivity("0001"));
    }
}
