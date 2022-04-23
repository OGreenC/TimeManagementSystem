package dtu.timeManagement.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Activity {
    private String activityName;
    private int expectedHours;
    private List<User> users = new ArrayList<>();
    private Calendar startTime;
    private Calendar endTime;

    /**
     * Activity is constructed by giving it a name, the name therefore works as an 'ID' for the activity
     */
    public Activity(String activityName) {
        this.activityName = activityName;
    }

    public void assignUser(User user) {
        users.add(user);
    }

    public Boolean isAssigned(User user) {
        return users.contains(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void setExpectedHours(int expectedHours) {
        this.expectedHours = expectedHours;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getExpectedHours() {
        return expectedHours;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }
}
