package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.internal.matchers.Null;

import javax.swing.*;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class UserSteps {
    private final TimeManagementApp timeManagementApp;
    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    private ActivityHelper activityHelper;
    private final ErrorMessageHolder errorMessageHolder;

    public UserSteps(TimeManagementApp timeManagementApp, UserHelper userHelper, ProjectHelper projectHelper, ActivityHelper activityHelper, ErrorMessageHolder errorMessageHolder) {
        this.timeManagementApp = timeManagementApp;
        this.userHelper = userHelper;
        this.projectHelper = projectHelper;
        this.activityHelper = activityHelper;
        this.errorMessageHolder = errorMessageHolder;
    }

    @Given("there is a user with the initials {string}")
    public void thereIsAUserWithTheInitials(String initials) throws OperationNotAllowedException {
        userHelper.setUser(new User(initials));
        timeManagementApp.addUser(userHelper.getUser());
    }

    @Given("there is a user in the system")
    public void thereIsAUserInTheSystem() throws OperationNotAllowedException {
        userHelper.setUser(new User("ABC"));
        timeManagementApp.addUser(userHelper.getUser());
    }

    @When("the user is added to the system")
    public void theUserIsAddedToTheSystem() {
        try {
            timeManagementApp.addUser(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("there is a user in the system with the initials {string}")
    public void thereIsAUserInTheSystemWithTheInitials(String initials) {
        assertEquals(timeManagementApp.getUser(initials).getInitial(), initials);
    }

    @Then("the error message {string} is given")
    public void the_error_message_is_given(String errorMessage) {
        assertEquals(errorMessage, this.errorMessageHolder.getErrorMessage());
    }

    /**
     * Assign user to activity
     * - Includes assigning a non-existing user
     */
    @Given("an activity with serial {string} is added to the project with ID {string}")
    public void an_activity_with_serial_is_added_to_the_project_with_id(String serialNumber, String ID) {
        if (projectHelper.getProject().getID().equals(ID)) {
            projectHelper.getProject().createActivity();
            activityHelper.setActivity(projectHelper.getProject().getActivity(serialNumber));
            assertEquals(activityHelper.getActivity().getSerialNumber(), serialNumber);
        }
    }

    @When("the user with initials {string} is assigned to the activity with serial {string}")
    public void the_user_with_initials_is_assigned_to_the_activity_with_serial(String initials, String serial) {
        try {
            projectHelper.getProject().getActivity(serial).assignUser(timeManagementApp.getUser(initials));
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the activity with serial {string} in the project with ID {string} has the user with initials {string} assigned")
    public void the_activity_with_serial_in_the_project_with_id_has_the_user_with_initials_assigned(String serialNumber, String ID, String initials) {
        if (projectHelper.getProject().getID().equals(ID)) {
            if (activityHelper.getActivity().getSerialNumber().equals(serialNumber)) {
                assertTrue(activityHelper.getActivity().isAssigned(timeManagementApp.getUser(initials)));
            }
        }
    }

    /**
     * Remove user from an activity
     * - Includes removing a non-existing user
     */
    @When("the user with initials {string} is removed from the activity with serial {string}")
    public void the_user_with_initials_is_removed_from_the_activity_with_serial(String initials, String serial) {
        try {
            if (activityHelper.getActivity().getSerialNumber().equals(serial)) {
                activityHelper.getActivity().removeUser(timeManagementApp.getUser(initials));
            }
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the activity with serial {string} in the project with ID {string} does not have the user with initials {string} assigned")
    public void the_activity_with_serial_in_the_project_with_id_does_not_have_the_user_with_initials_assigned(String serial, String ID, String initials) {
        if (projectHelper.getProject().getID().equals(ID) && activityHelper.getActivity().getSerialNumber().equals(serial)) {
            activityHelper.getActivity().isAssigned(timeManagementApp.getUser(initials));
        }
    }

    /**
     * Delete user from the system
     */
//    @When("the user with initials {string} is removed from the system")
//    public void the_user_with_initials_is_removed_from_the_system(String initials) {
//        timeManagementApp.removeUser(initials);
//    }
//
//    @Then("there is no user with the initials {string} in the system")
//    public void there_is_no_user_with_the_initials_in_the_system(String initials) {
//        assertNull(timeManagementApp.getUser(initials));
//    }
//
//    @Then("no project has a project leader with the initials {string}")
//    public void no_project_has_a_project_leader_with_the_initials(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Then("no project has the user with the initials {string} assigned to it")
//    public void no_project_has_the_user_with_the_initials_assigned_to_it(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Then("no time should be assigned to the user with the initials {string}")
//    public void no_time_should_be_assigned_to_the_user_with_the_initials(String string) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new io.cucumber.java.PendingException();
//    }
}
