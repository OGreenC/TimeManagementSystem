package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity {

    private static int nextSerial = 1;
    private final String serialNumber;

    private String activityName;
    private int expectedHours;

    private final List<User> users = new ArrayList<>();
    private Calendar startTime;
    private Calendar endTime;
    private final Project project;

    public Activity(Project project) {
        this.serialNumber = String.format("%04d", nextSerial);
        nextSerial++;
        this.project = project;
    }

    public void assignUser(User user) {

        assert user != null && !isAssigned(user);

        users.add(user);

        // Post-condition
        assert users.contains(user);
    }

    public void removeUser(User user) {
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
        if (tempStartTime.before(endTime) || endTime == null &&
                (tempStartTime.after(getProject().getStartDate())|| getProject().getStartDate()==null) &&
                (tempStartTime.before(getProject().getEndDate())|| getProject().getEndDate()==null)) {
            this.startTime.set(y, mo, d);
        } else if(tempStartTime.before(getProject().getStartDate()) ||
                tempStartTime.after(getProject().getEndDate())) {
            throw new OperationNotAllowedException("The start date is not in the interval of the projects dates");
        } else {
            throw new OperationNotAllowedException("The finish date is before the start date");
        }
    }

    public void setEndTime(int y, int mo, int d) throws OperationNotAllowedException {
        this.endTime = Calendar.getInstance();
        Calendar tempFinishDate = Calendar.getInstance();
        tempFinishDate.set(y, mo, d);
        if (tempFinishDate.after(startTime) || startTime == null &&
                (tempFinishDate.after(getProject().getStartDate())|| getProject().getStartDate()==null) &&
                (tempFinishDate.before(getProject().getEndDate())|| getProject().getEndDate()==null)) {
            this.endTime.set(y, mo, d);
        } else if(tempFinishDate.before(getProject().getStartDate()) ||
                tempFinishDate.after(getProject().getEndDate())) {
            throw new OperationNotAllowedException("The finish date is not in the interval of the projects dates");
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
        return !Calendar.getInstance().before(endTime);
    }

    public Boolean hasStarted() {
        return !Calendar.getInstance().before(startTime);
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

    public String getActivityName() {
        return activityName;
    }

    public Project getProject() {
        return project;
    }

    public List<User> getUsers() {
        return users;
    }
}
