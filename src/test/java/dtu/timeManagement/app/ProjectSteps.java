package dtu.timeManagement.app;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProjectSteps {
    TimeManagementApp timeManagementApp;
    String year;
    public ProjectSteps(TimeManagementApp timeManagementApp) {
        this.timeManagementApp = timeManagementApp;
    }
    @Given("the year is {string}")
    public void the_date_is(String year) {
        this.year = year;
    }

    @When("a project is created")
    public void a_project_is_created() {
        timeManagementApp.createProject();
    }

    @Then("there is a project with ID {string}")
    public void there_is_a_project_with_id(String id) {
        //get the project id from timemanagementapp
        Project project = timeManagementApp.getProject(id);
        assertTrue(id.equals(project.getID()));
    }

    @Then("there are no activities in the project")
    public void there_are_no_activities_in_the_project() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
