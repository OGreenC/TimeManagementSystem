package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.domains.Project;
import dtu.timeManagement.app.domains.TimeManagementApp;
import dtu.timeManagement.app.domains.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * @author Hovedansvarlig Victor Hyltoft (s214964)
 */
public class ProjectSteps {
    private final TimeManagementApp timeManagementApp;
    private final ErrorMessageHolder errorMessageHolder;
    private final ProjectHelper projectHelper;

    public ProjectSteps(TimeManagementApp timeManagementApp, ErrorMessageHolder errorMessageHolder, ProjectHelper projectHelper) {
        this.timeManagementApp = timeManagementApp;
        this.errorMessageHolder = errorMessageHolder;
        this.projectHelper = projectHelper;
    }

    @And("no projects have been created")
    public void no_projects_have_been_created() {
        timeManagementApp.deleteAllProjects();
        assertEquals(timeManagementApp.getProjects().size(), 0);
        assertEquals(Project.resetSerial(), 1);
    }

    @Given("a project is registered in the system")
    public void a_project_is_registered_in_the_system() {
        this.projectHelper.setProject(timeManagementApp.createProject());
    }

    /**
     * Oliver Tobias Siggaard (s204450)
     */
    @Then("there is a project with ID {string}")
    public void there_is_a_project_with_id(String id) {
        this.projectHelper.setProject(timeManagementApp.getProject(id));
        assertEquals(id, projectHelper.getProject().getID());
    }

    /**
     * Oliver Tobias Siggaard (s204450)
     */
    @Then("there are no activities in the project")
    public void there_are_no_activities_in_the_project() {
        projectHelper.getProject().deleteAllActivities();
        assertEquals(projectHelper.getProject().getActivities().size(), 0);

    }

    /**
     * Oliver Gr??nborg Christensen (s204479)
     */
    @When("the project is renamed to {string}")
    public void the_project_is_renamed_to(String name) {
        projectHelper.getProject().setName(name);
    }

    /**
     * Oliver Gr??nborg Christensen (s204479)
     */
    @Then("the project has the name {string}")
    public void the_project_has_the_name(String name) {
        assertEquals(name, projectHelper.getProject().getName());
    }

    /**
     * Niels Thormann (s216160)
     */
    @When("the startDate is set to year {int} month {int} date {int}")
    public void the_start_date_is_set_to_year_month_date(int y, int mo, int d) {
        try {
            projectHelper.getProject().setStartDate(y, mo, d);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    /**
     * Niels Thormann (s216160)
     */
    @Then("the startDate is year {int} month {int} date {int}")
    public void the_start_date_is_year_month_date(int y, int mo, int d) {
        assertEquals(projectHelper.getProject().getStartDate().get(Calendar.YEAR), y);
        assertEquals(projectHelper.getProject().getStartDate().get(Calendar.MONTH), mo);
        assertEquals(projectHelper.getProject().getStartDate().get(Calendar.DATE), d);
    }

    /**
     * Niels Thormann (s216160)
     */
    @When("the finishDate is set to year {int} month {int} date {int}")
    public void the_finish_date_is_set_to_year_month_date(int y, int mo, int d) {
        try {
            projectHelper.getProject().setEndDate(y, mo, d);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    /**
     * Niels Thormann (s216160)
     */
    @Then("the finishDate is year {int} month {int} date {int}")
    public void the_finish_date_is_year_month_date(int y, int mo, int d) {
        assertEquals(projectHelper.getProject().getEndDate().get(Calendar.YEAR), y);
        assertEquals(projectHelper.getProject().getEndDate().get(Calendar.MONTH), mo);
        assertEquals(projectHelper.getProject().getEndDate().get(Calendar.DATE), d);
    }

    @When("the project is deleted")
    public void the_project_is_deleted() {
        try {
            assertTrue(timeManagementApp.deleteProject(this.projectHelper.getProject()));
            this.projectHelper.setProject(null);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project does not exist")
    public void the_project_does_not_exist() {
        assertNull(timeManagementApp.getProject(this.projectHelper.getProject()));
    }


    @And("the project has activities")
    public void theProjectHasActivities() {
        assertTrue(projectHelper.getProject().getActivities().size() > 0);
    }

    @And("the project does not have a project leader assigned")
    public void theProjectDoesNotHaveAProjectLeaderAssigned() {
        assertNull(this.projectHelper.getProject().getProjectLeader());
    }


    @When("the user {string} is assigned as project leader to the project")
    public void the_user_is_assigned_as_project_leader_to_the_project(String projectLeaderInitials) {
        User user = timeManagementApp.getUser(projectLeaderInitials.toUpperCase());
        try {
            timeManagementApp.setProjectLeader(this.projectHelper.getProject(), user);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project has the project leader {string} assigned")
    public void the_project_has_the_project_leader_assigned(String projectLeaderInitials) {
        assertEquals(this.projectHelper.getProject().getProjectLeader().getInitial(), projectLeaderInitials.toUpperCase());
    }

    @When("the project leader is removed from the project")
    public void the_project_leader_is_removed_from_the_project() {
        try {
            timeManagementApp.removeProjectLeader(this.projectHelper.getProject());
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the project has no project leader")
    public void the_project_has_no_project_leader() {
        assertNull(this.projectHelper.getProject().getProjectLeader());
    }
}