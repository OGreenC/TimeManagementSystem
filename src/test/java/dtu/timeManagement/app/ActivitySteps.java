package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class ActivitySteps {
    private ProjectHelper projectHelper;
    private ErrorMessageHolder errorMessageHolder;
    public ActivitySteps(ProjectHelper projectHelper, ErrorMessageHolder errorMessageHolder) {
        this.projectHelper = projectHelper;
        this.errorMessageHolder = errorMessageHolder;
    }

    @When("an activity is added to the project")
    public void an_activity_is_added_to_the_project() {
        projectHelper.getProject().createActivity();
    }

    @Then("the activity with the serial {string} is in the project")
    public void the_activity_with_the_serial_is_in_the_project(String serialNumber) {
        assertNotNull(projectHelper.getProject().getActivity(serialNumber));
    }

    @Given("there is an activity in the project with serial {string}")
    public void there_is_an_activity_in_the_project_with_serial(String serialNumber) {
        projectHelper.getProject().createActivity();
        assertNotNull(projectHelper.getProject().getActivity(serialNumber));
    }

    @When("the user deletes the activity with serial {string}")
    public void the_user_deletes_the_activity_with_serial(String serialNumber) {
        try {
            projectHelper.getProject().deleteActivity(serialNumber);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the activity with serial {string} is deleted")
    public void the_activity_with_serial_is_deleted(String serialNumber) {
        assertNull(projectHelper.getProject().getActivity(serialNumber));
    }

}
