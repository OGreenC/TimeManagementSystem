package dtu.timeManagement.app;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ActivitySteps {
    private ProjectHelper projectHelper;
    public ActivitySteps(ProjectHelper projectHelper) {
        this.projectHelper = projectHelper;
    }

    @When("an activity is added to the project")
    public void an_activity_is_added_to_the_project() {
        projectHelper.getProject().createActivity();
    }

    @Then("the activity with the serial {string} is in the project")
    public void the_activity_with_the_serial_is_in_the_project(String serialNumber) {
        assertNotNull(projectHelper.getProject().getActivity(serialNumber));
    }
}
