package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class TimeRegistrationSteps {
    private final TimeManagementApp timeManagementApp;
    private final ErrorMessageHolder errorMessageHolder;
    private UserHelper userHelper;
    private ProjectHelper projectHelper;
    private ActivityHelper activityHelper;

    public TimeRegistrationSteps(TimeManagementApp timeManagementApp, UserHelper userHelper, ProjectHelper projectHelper, ErrorMessageHolder errorMessageHolder) {
        this.timeManagementApp = timeManagementApp;
        this.userHelper = userHelper;
        this.projectHelper = projectHelper;
        this.errorMessageHolder = errorMessageHolder;
    }

}
