package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Project {
    private static int nextSerial = 1;

    private final String projectID;
    private String name;
    private Calendar calendar = Calendar.getInstance();

    private final List<Activity> activities = new ArrayList<>();
    private User projectLeader;

    public Project() {
        this.projectID = generateID();
        System.out.println(this.projectID);
    }

    public String generateID() {
        int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
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
        System.out.println(y + " " + mo + " " + " " + d);
        this.calendar.set(y, mo, d);
    }

    public static int resetSerial() {
        nextSerial = 1;
        return nextSerial;
    }

    public void createActivity() {
        this.activities.add(new Activity());
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
        }
        this.projectLeader = null;
    }
}
