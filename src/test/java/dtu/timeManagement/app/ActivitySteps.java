package dtu.timeManagement.app;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ActivitySteps {
    private final TimeManagementApp timeManagementApp;
    private Project p;
    public ActivitySteps(TimeManagementApp timeManagementApp) {
        this.timeManagementApp = timeManagementApp;

    }

    @Given("there is a project")
    public void there_is_a_project() {
        this.p = timeManagementApp.createProject();
    }

    @When("the user adds the activity with the name {string} to the project")
    public void the_user_adds_the_activity_with_the_name_to_the_project(String string) {
        p.createActivity(string);
    }

    @Then("the activity with the name {string} is added to the project")
    public void the_activity_with_the_name_is_added_to_the_project(String string) {
        assertNotNull(p.getActivity(string));
    }
}
