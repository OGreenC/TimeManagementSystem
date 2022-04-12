package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserSteps {
    private final TimeManagementApp timeManagementApp;
    private UserHelper userHelper;
    private final ErrorMessageHolder errorMessageHolder;

    public UserSteps(TimeManagementApp timeManagementApp, UserHelper userHelper, ErrorMessageHolder errorMessageHolder) {
        this.timeManagementApp = timeManagementApp;
        this.userHelper = userHelper;
        this.errorMessageHolder = errorMessageHolder;
    }


    @Given("there is a user with the initials {string}")
    public void thereIsAUserWithTheInitials(String initials) {
        userHelper.setUser(new User(initials));
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
}
