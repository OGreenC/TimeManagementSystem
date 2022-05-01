package dtu.timeManagement.app.timeRegistration;

import java.util.Calendar;

public class RegistrationUnit {

    private int hours;
    private String projectID;
    private String activitySerial;

    public RegistrationUnit(int hours, String projectID, String activitySerial) {
        this.hours = hours;
        this.projectID = projectID;
        this.activitySerial = activitySerial;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getProjectID() {
        return projectID;
    }

    public String getActivitySerial() {
        return activitySerial;
    }
}
