package dtu.timeManagement.app;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserSteps {
    private final TimeManagementApp timeManagementApp;
    private User user;

    public UserSteps(TimeManagementApp timeManagementApp) {
        this.timeManagementApp = timeManagementApp;
    }

    /*@Given("there is a user with the name {string} and the initials {string}")
    public void there_is_a_user_with_the_name_and_the_initials(String name, String initials) {


    }

    @When("the user is added to the system")
    public void the_user_is_added_to_the_system() {

    }

    @Then("there is a user in the system with the initials {string} with the name {string}")
    public void there_is_a_user_in_the_system_with_the_initials_with_the_name(String initials, String name) {


    }*/

    @Given("there is a user with the initials {string}")
    public void thereIsAUserWithTheInitials(String initials) {
        this.user = new User(initials);
    }

    @When("the user is added to the system")
    public void theUserIsAddedToTheSystem() {
        timeManagementApp.addUser(user);
    }

    @Then("there is a user in the system with the initials {string}")
    public void thereIsAUserInTheSystemWithTheInitials(String initials) {
        User returnUser = timeManagementApp.getUser(initials);
        assertEquals(returnUser.getInitial(), initials);
    }
}
