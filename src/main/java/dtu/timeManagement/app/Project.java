package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Project {
    private static int nextSerial = 1;
    private final String projectID;
    private String name;
    private final Calendar calendar = Calendar.getInstance();
    private final Calendar dateOfCreation;
    private final List<Activity> activities = new ArrayList<>();
    private User projectLeader;

    // Used for the UI
    private final List<Node> activityTabs = new ArrayList<>();

    public Project(Calendar date) {
        this.dateOfCreation = date;
        this.projectID = generateID();
    }

    public String generateID() {
        int year = dateOfCreation.get(Calendar.YEAR) % 100;
        String serial = String.format("%04d", nextSerial);
        nextSerial++;
        return year + serial;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return projectID;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public Calendar getStartDate() {
        return calendar;
    }

    public void setStartDate(int y, int mo, int d) {
        this.calendar.set(y, mo, d);
    }

    public static int resetSerial() {
        nextSerial = 1;
        return nextSerial;
    }

    public void deleteAllActivities() {
        this.activities.clear();
        Activity.resetSerial();
    }

    public Activity createActivity() {
        Activity activity = new Activity(this);
        this.activities.add(activity);
        return activity;
    }

    public void deleteActivity(Activity activity) throws OperationNotAllowedException {
        if (activity == null) {
            throw new OperationNotAllowedException("Activity does not exist");
        }

        activities.remove(activity);

    }

    public Activity getActivity(String serialNumber) {
        return activities.stream().filter(a -> a.getSerialNumber().equals(serialNumber)).findAny().orElse(null);
    }

    public void setProjectLeader(User projectLeader) throws OperationNotAllowedException {
        if (this.projectLeader != null) {
            throw new OperationNotAllowedException("Only one project leader can be assigned per project");
        }
        this.projectLeader = projectLeader;
    }

    public User getProjectLeader() {
        return this.projectLeader;
    }

    public void removeProjectLeader() throws OperationNotAllowedException {
        if (this.projectLeader == null) {
            throw new OperationNotAllowedException("Project leader does not exist");
        } else {
            this.projectLeader = null;
        }
    }
}
