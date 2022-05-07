package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.timeRegistration.RegistrationInstance;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class TimeRegistrationSteps {
    private final TimeManagementApp timeManagementApp;
    private final ErrorMessageHolder errorMessageHolder;
    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    private ActivityHelper activityHelper;

    public TimeRegistrationSteps(TimeManagementApp timeManagementApp, UserHelper userHelper, ProjectHelper projectHelper, ActivityHelper activityHelper, ErrorMessageHolder errorMessageHolder) {
        this.timeManagementApp = timeManagementApp;
        this.userHelper = userHelper;
        this.projectHelper = projectHelper;
        this.errorMessageHolder = errorMessageHolder;
        this.activityHelper = activityHelper;
    }

    @When("the user registers {int} hours on the activity on year {int} month {int} date {int}")
    public void theUserRegistersHoursOnTheActivity(int hours, int y, int mo, int d) {
        try {
            Calendar date = Calendar.getInstance();
            date.set(y, mo, d);
            String projectID = projectHelper.getProject().getID();
            String activitySerial = activityHelper.getActivity().getSerialNumber();

            userHelper.getUser().registerTime(date,hours,projectID,activitySerial);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    @When("the user registers {int} hours on the activity")
    public void theUserRegistersHoursOnTheActivity(int hours) {
        Calendar date = Calendar.getInstance();
        String projectID = projectHelper.getProject().getID();
        String activitySerial = activityHelper.getActivity().getSerialNumber();
        try {
            userHelper.getUser().registerTime(date,hours,projectID,activitySerial);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("the user has registered {int} hours on the activity on year {int} month {int} date {int}")
    public void theUserHasRegisteredHoursOnTheActivity(int hours, int y, int mo, int d) {
        Calendar date = Calendar.getInstance();
        date.set(y, mo, d);
        String projectID = projectHelper.getProject().getID();
        String activitySerial = activityHelper.getActivity().getSerialNumber();
        RegistrationInstance u = userHelper.getUser().getTimeRegistrationDay(date).getRegistrationUnit(projectID,activitySerial);
        assertNotNull(u);
        assertEquals(u.getHours(), hours);
    }

    @When("the user removes time registration of activity on year {int} month {int} date {int}")
    public void theUserRemovesTimeRegistrationOfActivityOnYearMonthDate(int y, int mo, int d) {
        Calendar date = Calendar.getInstance();
        date.set(y, mo, d);
        String projectID = projectHelper.getProject().getID();
        String activitySerial = activityHelper.getActivity().getSerialNumber();
        userHelper.getUser().getTimeRegistrationDay(date).removeRegistrationUnit(projectID,activitySerial);
    }

    @Then("there is no time registration for activity on year {int} month {int} date {int}")
    public void thereIsNoTimeRegistrationForActivityOnYearMonthDate(int y, int mo, int d) {
        Calendar date = Calendar.getInstance();
        date.set(y, mo, d);
        String projectID = projectHelper.getProject().getID();
        String activitySerial = activityHelper.getActivity().getSerialNumber();
        RegistrationInstance u = userHelper.getUser().getTimeRegistrationDay(date).getRegistrationUnit(projectID,activitySerial);
        assertNull(u);
    }

    @Then("there is {int} time registrations on year {int} month {int} date {int}")
    public void thereIsTimeRegistrationsOnYearMonthDate(int registrations, int y, int mo, int d) {
        Calendar date = Calendar.getInstance();
        date.set(y, mo, d);
        ArrayList<RegistrationInstance> units = userHelper.getUser().getTimeRegistrationDay(date).getRegistrationUnits();
        assertEquals(units.size(),registrations);
    }


}
