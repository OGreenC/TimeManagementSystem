package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserSteps {
    private final TimeManagementApp timeManagementApp;
    private User user;
    private ErrorMessageHolder errorMessageHolder;

    public UserSteps(TimeManagementApp timeManagementApp, ErrorMessageHolder errorMessageHolder) {
        this.timeManagementApp = timeManagementApp;
        this.errorMessageHolder = errorMessageHolder;
    }


    @Given("there is a user with the initials {string}")
    public void thereIsAUserWithTheInitials(String initials) {
        this.user = new User(initials);
    }

    @When("the user is added to the system")
    public void theUserIsAddedToTheSystem() {
        try {
            timeManagementApp.addUser(user);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("there is a user in the system with the initials {string}")
    public void thereIsAUserInTheSystemWithTheInitials(String initials) {
        User returnUser = timeManagementApp.getUser(initials);
        assertEquals(returnUser.getInitial(), initials);
    }

    @Then("the error message {string} is given")
    public void the_error_message_is_given(String errorMessage) throws OperationNotAllowedException {
        assertEquals(errorMessage, this.errorMessageHolder.getErrorMessage());
    }
}
