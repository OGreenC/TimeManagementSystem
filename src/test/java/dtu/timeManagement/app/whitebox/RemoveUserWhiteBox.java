package dtu.timeManagement.app.whitebox;

import dtu.timeManagement.app.*;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RemoveUserWhiteBox {
    private final TimeManagementApp timeManagementApp = new TimeManagementApp();
    private final UserHelper userHelper = new UserHelper();
    private final ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();
    private final ActivityHelper activityHelper = new ActivityHelper();
    private final ProjectHelper projectHelper = new ProjectHelper();

    public RemoveUserWhiteBox() {
        projectHelper.setProject(new Project(Calendar.getInstance()));
    }

    @Test
    public void testInputDataSetA() {
        // Initializing data set:
        userHelper.setUser(new User("ABC"));

        // (Try to) remove the user from the system:
        try {
            timeManagementApp.removeUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        // Check if given error message matches the expected error message (i.e. if it happened at all)
        assertEquals(errorMessageHolder.getErrorMessage(), "User is not in the system");
    }

    @Test
    public void testInputDataSetB() {
        // Initializing the data set:
        userHelper.setUser(new User("ABC"));
        try {
            timeManagementApp.addUser(userHelper.getUser()); // Adding the user to the system
        } catch (OperationNotAllowedException e) {
            fail();
        }

        // Remove the user from the system:
        try {
            timeManagementApp.removeUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        // Check that the user is in fact removed from the system:
        assertNull(timeManagementApp.getUser(userHelper.getUser().getInitial()));
    }

    @Test
    public void testInputDataSetC() {
        // Initializing the data set:
        userHelper.setUser(new User("ABC"));
        try {
            timeManagementApp.addUser(userHelper.getUser()); // Adding the user to the system
        } catch (OperationNotAllowedException e) {
            fail();
        }
        activityHelper.setActivity(new Activity(projectHelper.getProject()));
        userHelper.getUser().addActivity(activityHelper.getActivity()); // Assigning the activity to the user

        // Remove the user from the system:
        try {
            timeManagementApp.removeUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        // Check that the user is in fact removed from the activity and the system:
        assertFalse(activityHelper.getActivity().isAssigned(userHelper.getUser()));
        assertNull(timeManagementApp.getUser(userHelper.getUser().getInitial()));
    }

    @Test
    public void testInputDataSetD() {
        // Initializing the data set:
        userHelper.setUser(new User("ABC"));
        try {
            timeManagementApp.addUser(userHelper.getUser()); // Adding the user to the system
        } catch (OperationNotAllowedException e) {
            fail();
        }
        projectHelper.getProject().deleteAllActivities(); // Resetting activity serials (to match the white box tables)
        projectHelper.getProject().setProjectLeader(userHelper.getUser()); // Setting user as project leader
        activityHelper.setActivity(new Activity(projectHelper.getProject()));
        userHelper.getUser().addActivity(activityHelper.getActivity()); // Assigning the activity to the user

        // Remove the user from the system:
        try {
            timeManagementApp.removeUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        // Check that the user is in fact removed from the activity, as a project leader and from the system:
        assertFalse(activityHelper.getActivity().isAssigned(userHelper.getUser()));
        assertNotEquals(projectHelper.getProject().getProjectLeader(), userHelper.getUser());
        assertNull(timeManagementApp.getUser(userHelper.getUser().getInitial()));
    }

    @Test
    public void testInputDataSetE() {
        // Initializing the data set:
        userHelper.setUser(new User("ABC"));
        try {
            timeManagementApp.addUser(userHelper.getUser()); // Adding the user to the system
        } catch (OperationNotAllowedException e) {
            fail();
        }
        projectHelper.getProject().deleteAllActivities(); // Resetting activity serials (to match the white box tables)
        userHelper.getUser().addActivity(new Activity(projectHelper.getProject())); // Adding first activity to user
        userHelper.getUser().addActivity(new Activity(projectHelper.getProject())); // Adding second activity to user

        // Remove the user from the system:
        try {
            timeManagementApp.removeUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        // Check that the user is in fact removed from both activities and the system:
        for (Project project : timeManagementApp.getProjects()) {
            for (Activity activity : project.getActivities()) {
                assertFalse(activity.isAssigned(userHelper.getUser()));
            }
        }
        assertNull(timeManagementApp.getUser(userHelper.getUser().getInitial()));
    }

    @Test
    public void testInputDataSetF() {
        // Initializing the data set:
        userHelper.setUser(new User("ABC"));
        try {
            timeManagementApp.addUser(userHelper.getUser()); // Adding the user to the system
        } catch (OperationNotAllowedException e) {
            fail();
        }
        projectHelper.getProject().deleteAllActivities(); // Resetting activity serials (to match the white box tables)
        projectHelper.getProject().setProjectLeader(userHelper.getUser()); // Setting the user as project leader
        userHelper.getUser().addActivity(new Activity(projectHelper.getProject())); // Adding first activity to user
        userHelper.getUser().addActivity(new Activity(projectHelper.getProject())); // Adding second activity to user

        // Remove the user from the system:
        try {
            timeManagementApp.removeUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        // Check that the user is in fact removed from both activities, as a project leader and from the system:
        for (Project project : timeManagementApp.getProjects()) {
            for (Activity activity : project.getActivities()) {
                assertFalse(activity.isAssigned(userHelper.getUser()));
            }
        }
        assertNotEquals(projectHelper.getProject().getProjectLeader(), userHelper.getUser());
        assertNull(timeManagementApp.getUser(userHelper.getUser().getInitial()));
    }
}
