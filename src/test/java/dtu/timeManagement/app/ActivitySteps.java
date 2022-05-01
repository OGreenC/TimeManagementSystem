package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Calendar;

import static org.junit.Assert.*;

public class ActivitySteps {
    private ProjectHelper projectHelper;
    private ErrorMessageHolder errorMessageHolder;
    private ActivityHelper activityHelper;
    public ActivitySteps(ProjectHelper projectHelper, ErrorMessageHolder errorMessageHolder, ActivityHelper activityHelper) {
        this.projectHelper = projectHelper;
        this.errorMessageHolder = errorMessageHolder;
        this.activityHelper = activityHelper;
    }

    @When("an activity is added to the project")
    public void an_activity_is_added_to_the_project() {
        Activity a = projectHelper.getProject().createActivity();
        activityHelper.setActivity(a);
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

    @When("the expected hours of the activity with serial {string} is changed to {string}")
    public void the_expected_hours_of_the_activity_with_serial_is_changed_to(String serialNumber, String hours) {
        projectHelper.getProject().getActivity(serialNumber).setExpectedHours(Integer.parseInt(hours));
    }

    @Then("the activity with serial {string} is expected to take {string} hours")
    public void the_activity_with_serial_is_expected_to_take_hours(String serialNumber, String hours) {
        assertEquals(projectHelper.getProject().getActivity(serialNumber).getExpectedHours(),Integer.parseInt(hours));
    }

    @When("the activity with the serial {string} startDate is set to year {int} month {int} date {int}")
    public void the_activity_with_the_serial_start_date_is_set_to_year_month_date(String serialNumber, int y, int mo, int d) {
        if (projectHelper.getProject().getActivity(serialNumber).getHasEndDate() == false) {
            projectHelper.getProject().getActivity(serialNumber).setStartTime(y, mo, d);
            projectHelper.getProject().getActivity(serialNumber).setHasStartDate(true);
        } else {
            Calendar tempStartDate = Calendar.getInstance();
            tempStartDate.set(y, mo, d);
            if (tempStartDate.before(projectHelper.getProject().getActivity(serialNumber).getEndTime())) {
                projectHelper.getProject().getActivity(serialNumber).setStartTime(y, mo, d);
            } else {
                errorMessageHolder.setErrorMessage("The finish date is before the start date");
            }
        }
    }

    @Then("the activity with the serial {string} has the startdate year {int} month {int} date {int}")
    public void the_activity_with_the_serial_has_the_startdate_year_month_date(String serialNumber, int y, int mo, int d) {
        assertEquals(projectHelper.getProject().getActivity(serialNumber).getStartTime().get(Calendar.YEAR), y);
        assertEquals(projectHelper.getProject().getActivity(serialNumber).getStartTime().get(Calendar.MONTH), mo);
        assertEquals(projectHelper.getProject().getActivity(serialNumber).getStartTime().get(Calendar.DATE), d);
    }

    @When("the activity with the serial {string} finishDate is set to year {int} month {int} date {int}")
    public void the_activity_with_the_serial_finish_date_is_set_to_year_month_date(String serialNumber, int y, int mo, int d) {
        if(projectHelper.getProject().getActivity(serialNumber).getHasStartDate() == false) {
            projectHelper.getProject().getActivity(serialNumber).setEndTime(y, mo, d);
            projectHelper.getProject().getActivity(serialNumber).setHasEndDate(true);
        } else {
            Calendar tempFinishDate = Calendar.getInstance();
            tempFinishDate.set(y, mo, d);
            if(tempFinishDate.after(projectHelper.getProject().getActivity(serialNumber).getStartTime())) {
                projectHelper.getProject().getActivity(serialNumber).setEndTime(y, mo, d);
            } else {
                errorMessageHolder.setErrorMessage("The finish date is before the start date");
            }
        }

    }

    @Then("the activity with the serial {string} has the finishdate year {int} month {int} date {int}")
    public void the_activity_with_the_serial_has_the_finishdate_year_month_date(String serialNumber, int y, int mo, int d) {
        assertEquals(projectHelper.getProject().getActivity(serialNumber).getEndTime().get(Calendar.YEAR), y);
        assertEquals(projectHelper.getProject().getActivity(serialNumber).getEndTime().get(Calendar.MONTH), mo);
        assertEquals(projectHelper.getProject().getActivity(serialNumber).getEndTime().get(Calendar.DATE), d);
    }

    @When("the activity with the serial {string} name is set to {string}")
    public void the_activity_with_the_serial_name_is_set_to(String serialNumber, String activityName) {
        projectHelper.getProject().getActivity(serialNumber).setActivityName(activityName);
    }

    @Then("the activity with the serial {string} has the name {string}")
    public void the_activity_with_the_serial_has_the_name(String serialNumber, String activityName) {
        assertEquals(projectHelper.getProject().getActivity(serialNumber).getActivityName(),activityName);
    }
}
