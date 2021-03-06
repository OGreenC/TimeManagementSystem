package dtu.timeManagement.app.domains.timeRegistration;

/**
 * Class written by:
 * @author s204470 - Oliver Grønborg Christensen
 *
 * RegistrationInstance objects hold all data relating to a single instance of a
 * time registration - eg. dedicated hours and project/activity identification ID's
 */

public class RegistrationInstance {

    private int hours;
    private final String projectID;
    private final String activitySerial;

    public RegistrationInstance(int hours, String projectID, String activitySerial) {
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
