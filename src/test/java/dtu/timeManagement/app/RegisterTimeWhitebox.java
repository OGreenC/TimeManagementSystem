package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.timeRegistration.RegistrationInstance;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RegisterTimeWhitebox {
    private final ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();
    private final UserHelper userHelper = new UserHelper();

    public RegisterTimeWhitebox() {
        //Arbitrary user object. Initial doesn't matter in this case.
        userHelper.setUser(new User("ABC"));
    }

    @Test
    public void testInputDataSetA() {
        //Initializing data set:
        int hours = 0;
        Calendar date = Calendar.getInstance();
        date.set(2022, 11, 24);
        String projectID = "220001";
        String activitySerial = "0001";

        //Running method:
        try {
            userHelper.getUser().registerTime(date,hours,projectID,activitySerial);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        assertEquals(errorMessageHolder.getErrorMessage(), "Can't register 0 or negative hours on activity");
    }

    @Test
    public void testInputDataSetB() {
        //Initializing data set:
        int hours = 3;
        Calendar date = Calendar.getInstance();
        date.set(2022, 11, 24);
        String projectID = "220001";
        String activitySerial = "0001";

        //Running method:
        try {
            userHelper.getUser().registerTime(date,hours,projectID,activitySerial);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        RegistrationInstance registrationInstance = userHelper.getUser().getTimeRegistrationDay(date).getRegistrationUnit(projectID,activitySerial);
        assertNotNull(registrationInstance);
        assertEquals(registrationInstance.getHours(), hours);
        assertEquals(registrationInstance.getProjectID(), projectID);
        assertEquals(registrationInstance.getActivitySerial(), activitySerial);
    }
    @Test
    public void testInputDataSetC() {
        //Initializing data set:
        int hours = 25;
        Calendar date = Calendar.getInstance();
        date.set(2022, 11, 24);
        String projectID = "220001";
        String activitySerial = "0001";

        //Running method:
        try {
            userHelper.getUser().registerTime(date,hours,projectID,activitySerial);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        assertEquals(errorMessageHolder.getErrorMessage(), "Can't register more than 24 hours a day");
    }
    @Test
    public void testInputDataSetD() {
        //Initializing data set:
        int hours = 3;
        Calendar date = Calendar.getInstance();
        date.set(2022, 11, 24);
        String projectID = "220001";
        String activitySerial = "0001";
        //Creating initial time registration on another activity, on the same date
        try {
            userHelper.getUser().registerTime(date,3,"220002","0002");
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertEquals(userHelper.getUser().getTimeRegistrationDay(date).getTotalHoursOnDay(), 3);

        //Running method:
        try {
            userHelper.getUser().registerTime(date,hours,projectID,activitySerial);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        RegistrationInstance registrationInstance = userHelper.getUser().getTimeRegistrationDay(date).getRegistrationUnit(projectID,activitySerial);
        assertNotNull(registrationInstance);
        assertEquals(registrationInstance.getHours(), hours);
        assertEquals(registrationInstance.getProjectID(), projectID);
        assertEquals(registrationInstance.getActivitySerial(), activitySerial);
    }
    @Test
    public void testInputDataSetE() {
        //Initializing data set:
        int hours = 3;
        Calendar date = Calendar.getInstance();
        date.set(2022, 11, 24);
        String projectID = "220001";
        String activitySerial = "0001";
        //Creating initial time registration on another activity, on the same date
        try {
            userHelper.getUser().registerTime(date,22,"220002","0002");
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertEquals(userHelper.getUser().getTimeRegistrationDay(date).getTotalHoursOnDay(), 22);

        //Running method:
        try {
            userHelper.getUser().registerTime(date,hours,projectID,activitySerial);
        } catch (OperationNotAllowedException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //Checking for expected output:
        assertEquals(errorMessageHolder.getErrorMessage(), "Can't register more than 24 hours a day");
    }
}
