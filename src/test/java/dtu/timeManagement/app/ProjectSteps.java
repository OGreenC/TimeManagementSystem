package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Calendar;

import static org.junit.Assert.*;

public class ProjectSteps {
    private final TimeManagementApp timeManagementApp;
    private Project project;
    private final ErrorMessageHolder errorMessageHolder;
    private final UserHelper userHelper;

    public ProjectSteps(TimeManagementApp timeManagementApp, ErrorMessageHolder errorMessageHolder, UserHelper userHelper) {
        this.timeManagementApp = timeManagementApp;
        this.errorMessageHolder = errorMessageHolder;
        this.userHelper = userHelper;
    }

    @And("no projects have been created")
    public void no_projects_have_been_created() {
        timeManagementApp.deleteAllProjects();
        assertEquals(timeManagementApp.getProjects().size(), 0);
        assertEquals(Project.resetSerial(), 1);
    }

    @Given("a project is registered in the system")
    public void a_project_is_registered_in_the_system() {
        this.project = timeManagementApp.createProject();
    }

    @Then("there is a project with ID {string}")
    public void there_is_a_project_with_id(String id) {
        // Get the project by id from TimeManagementApp
        this.project = timeManagementApp.getProject(id);
        assertEquals(id, project.getID());
    }

    @Then("there are no activities in the project")
    public void there_are_no_activities_in_the_project() {
        assertEquals(project.getActivities().size(), 0);
    }

    @When("the project is renamed to {string}")
    public void the_project_is_renamed_to(String name) {
        project.setName(name);
    }

    @Then("the project has the name {string}")
    public void the_project_has_the_name(String name) {
        assertEquals(name, project.getName());
    }

    @When("the startDate is set to year {int} month {int} date {int}")
    public void the_start_date_is_set_to_year_month_date(int y, int mo, int d) {
        project.setStartDate(y, mo, d);
    }

    @Then("the startDate is year {int} month {int} date {int}")
    public void the_start_date_is_year_month_date(int y, int mo, int d) {
        assertEquals(project.getStartDate().get(Calendar.YEAR), y);
        assertEquals(project.getStartDate().get(Calendar.MONTH), mo);
        assertEquals(project.getStartDate().get(Calendar.DATE), d);
    }


    @When("the project is deleted")
    public void the_project_is_deleted() {
        try {
            assertTrue(timeManagementApp.deleteProject(this.project));
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project does not exist")
    public void the_project_does_not_exist() {
        assertNull(timeManagementApp.getProject(this.project.getID()));
    }


    @And("the project has activities")
    public void theProjectHasActivities() {
        assertTrue(project.getActivities().size() > 0);
    }


    @And("an activity is registered to the project")
    public void anActivityIsRegisteredToTheProject() {
        timeManagementApp.createActivity(this.project);
    }

    @And("the project does not have a project leader assigned")
    public void theProjectDoesNotHaveAProjectLeaderAssigned() {
        assertNull(this.project.getProjectLeader());
    }


    @When("the project leader {string} is assigned to the project")
    public void the_project_leader_is_assigned_to_the_project(String projectLeaderInitials) {
        assertEquals(this.userHelper.getUser().getInitial(), projectLeaderInitials);
        try {
            this.project.setProjectLeader(userHelper.getUser());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project has the project leader {string} assigned")
    public void the_project_has_the_project_leader_assigned(String projectLeaderInitials) {
        assertEquals(this.project.getProjectLeader().getInitial(), projectLeaderInitials);
    }

    @When("the project leader is removed from the project")
    public void the_project_leader_is_removed_from_the_project() {
        try {
            this.project.removeProjectLeader();
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project has no project leader")
    public void the_project_has_no_project_leader() {
        assertNull(this.project.getProjectLeader());
    }

    @When("{int} year has passed")
    public void yearHasPassed(int arg0) {
    }
}
