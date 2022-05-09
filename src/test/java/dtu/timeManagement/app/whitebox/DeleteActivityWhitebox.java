package dtu.timeManagement.app.whitebox;

import dtu.timeManagement.app.*;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.domains.TimeManagementApp;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Mikkel Allermand (s214953)
 */
public class DeleteActivityWhitebox {
    private final ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();

    private final ProjectHelper projectHelper = new ProjectHelper();

    private final ActivityHelper activityHelper = new ActivityHelper();

    private final TimeManagementApp timeManagementApp = new TimeManagementApp();



    @Test
    public void testInputDataSetB() {
        projectHelper.setProject(timeManagementApp.createProject());
        try {
            timeManagementApp.deleteActivity(projectHelper.getProject(),activityHelper.getActivity());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertEquals(errorMessageHolder.getErrorMessage(),"Activity does not exist");
    }

    @Test
    public void testInputDataSetC() {
        projectHelper.setProject(timeManagementApp.createProject());
        projectHelper.getProject().createActivity();
        try {
            timeManagementApp.deleteActivity(projectHelper.getProject(),projectHelper.getProject().getActivity("0001"));
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertNull(projectHelper.getProject().getActivity("0001"));
    }
}
