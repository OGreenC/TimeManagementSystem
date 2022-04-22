package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class UserSteps {
    private final TimeManagementApp timeManagementApp;
    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    private final ErrorMessageHolder errorMessageHolder;

    public UserSteps(TimeManagementApp timeManagementApp, UserHelper userHelper, ProjectHelper projectHelper, ErrorMessageHolder errorMessageHolder) {
        this.timeManagementApp = timeManagementApp;
        this.userHelper = userHelper;
        this.projectHelper = projectHelper;
        this.errorMessageHolder = errorMessageHolder;
    }

    @Given("there is a user with the initials {string}")
    public void thereIsAUserWithTheInitials(String initials) throws OperationNotAllowedException {
        userHelper.setUser(new User(initials));
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
     * Assign user to project
     */
    @Given("a project is registered in the system with ID {string}")
    public void a_project_is_registered_in_the_system_with_id(String ID) {
        assertEquals(projectHelper.getProject().getID(), ID);
    }

    @When("the user with initials {string} is assigned to the project with ID {string}")
    public void the_user_with_initials_is_assigned_to_the_project_with_id(String initials, String ID) {
        try {
            if (timeManagementApp.getProject(ID) == null) {
                throw new OperationNotAllowedException("Project does not exist");
            }
            timeManagementApp.assignEmployeeToProject(initials, projectHelper.getProject());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project with ID {string} has the user with the initials {string} assigned")
    public void the_project_with_id_has_the_user_with_initials_assigned(String ID, String initials) {
        if (projectHelper.getProject().getID().equals(ID)) {
            assertNotNull(timeManagementApp.searchProjectForEmployee(initials, projectHelper.getProject()));
        }
    }

    /**
     * Assign user to non-existing project
     */
    @Given("a project with ID {string} is not in the system")
    public void a_project_with_id_is_not_in_the_system(String ID) {
        assertNull(timeManagementApp.getProject(ID));
    }

    /**
     * Remove user from project
     */
    @When("the user with initials {string} is removed from the project with ID {string}")
    public void the_user_with_initials_is_removed_from_the_project_with_id(String initials, String ID) {
        if (projectHelper.getProject().getID().equals(ID)) {
            timeManagementApp.removeEmployeeFromProject(initials, projectHelper.getProject());
        }
    }

    @Then("the project with ID {string} does not have the user with initials {string} assigned")
    public void the_project_with_id_does_not_have_the_user_with_initials_assigned(String initials, String ID) {
        if (projectHelper.getProject().getID().equals(ID)) {
            assertNull(timeManagementApp.searchProjectForEmployee(initials, projectHelper.getProject()));
        }
    }

    /**
     * Delete user from the system
     */
    @When("the user with initials {string} is removed from the system")
    public void the_user_with_initials_is_removed_from_the_system(String initials) {
        timeManagementApp.removeUser(initials);
    }

    @Then("there is no user with the initials {string} in the system")
    public void there_is_no_user_with_the_initials_in_the_system(String initials) {
        assertNull(timeManagementApp.getUser(initials));
    }

    @Then("no project has a project leader with the initials {string}")
    public void no_project_has_a_project_leader_with_the_initials(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("no project has the user with the initials {string} assigned to it")
    public void no_project_has_the_user_with_the_initials_assigned_to_it(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("no time should be assigned to the user with the initials {string}")
    public void no_time_should_be_assigned_to_the_user_with_the_initials(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
