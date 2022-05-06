package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity {

    private static int nextSerial = 1;
    private String serialNumber;

    private String activityName;
    private int expectedHours;

    private List<User> users = new ArrayList<>();
    private Calendar startTime;
    private Calendar endTime;
    private Project project;


    /**
     * Activity is constructed by giving it a name, the name therefore works as an 'ID' for the activity
     */
    public Activity(Project project) {
        this.serialNumber = String.format("%04d", nextSerial);
        nextSerial++;
        this.project = project;
    }

    public void assignUser(User user) throws OperationNotAllowedException {
        if (user == null) {
            throw new OperationNotAllowedException("User does not exist");
        } else if (isAssigned(user)) {
            throw new OperationNotAllowedException("User is already assigned to this activity");
        }
        users.add(user);
    }

    public void removeUser(User user) throws OperationNotAllowedException {
        if (user == null) {
            throw new OperationNotAllowedException("User does not exist");
        }
        users.remove(user);
    }

    public Boolean isAssigned(User user) {
        return users.contains(user);
    }

    public static void resetSerial() {
        nextSerial = 1;
    }

    public void setExpectedHours(int expectedHours) {
        this.expectedHours = expectedHours;
    }

    public void setStartTime(int y, int mo, int d) throws OperationNotAllowedException {
        this.startTime = Calendar.getInstance();
        Calendar tempStartTime = Calendar.getInstance();
        tempStartTime.set(y, mo, d);
        if (tempStartTime.before(endTime) || endTime == null) {
            this.startTime.set(y, mo, d);
        } else {
            throw new OperationNotAllowedException("The finish date is before the start date");
        }
    }

    public void setEndTime(int y, int mo, int d) throws OperationNotAllowedException {
        this.endTime = Calendar.getInstance();
        Calendar tempFinishDate = Calendar.getInstance();
        tempFinishDate.set(y, mo, d);
        if (tempFinishDate.after(startTime) || startTime == null) {
            this.endTime.set(y, mo, d);
        } else {
            throw new OperationNotAllowedException("The finish date is before the start date");
        }
    }

    public void setActivityName(String name) {
        this.activityName = name;
    }

    public int getExpectedHours() {
        return expectedHours;
    }

    public Boolean hasEnded() {
        if(!Calendar.getInstance().before(endTime)){ return true; }
        else { return false; }
    }

    public Boolean hasStarted() {
        if(!Calendar.getInstance().before(startTime)) { return true; }
        else { return false; }
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getActivityName() { return activityName;}

    public Project getProject() { return project;}

    public List<User> getUsers() {
        return users;
    }
}
