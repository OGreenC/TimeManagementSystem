package dtu.timeManagement.app.domains;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Mikkel Allermand (s214953)
 */
public class Project {
    private static int nextSerial = 1;
    private final String projectID;
    private String name;
    private final Calendar dateOfCreation;
    private Calendar startTime;
    private Calendar endTime;
    private final List<Activity> activities = new ArrayList<>();
    private User projectLeader;

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
        return startTime;
    }
    public Calendar getEndDate() { return endTime; }

    /**
     * Niels Thormann (s216160)
     */
    public void setStartDate(int y, int mo, int d) throws OperationNotAllowedException {
        this.startTime = Calendar.getInstance();
        Calendar tempStartTime = Calendar.getInstance();
        tempStartTime.set(y, mo, d);
        if (tempStartTime.before(endTime) || endTime == null) {
            if(!activities.isEmpty()) {
                for (int x = 0; x <= activities.size()-1; x++) {
                    if (tempStartTime.after(activities.get(x).getStartTime()) || tempStartTime.after(activities.get(x).getEndTime())) {
                        throw new OperationNotAllowedException("There is an activity with start" +
                                " or finish date outside the interval of the projects dates");
                    }
                }
            }
            this.startTime.set(y, mo, d);
        } else {
            throw new OperationNotAllowedException("The finish date is before the start date");
        }
    }

    /**
     * Niels Thormann (s216160)
     */
    public void setEndDate(int y, int mo, int d) throws OperationNotAllowedException {
        this.endTime = Calendar.getInstance();
        Calendar tempFinishDate = Calendar.getInstance();
        tempFinishDate.set(y, mo, d);
        if (tempFinishDate.after(startTime) || startTime == null) {
            if(!activities.isEmpty()) {
                for (int x = 0; x <= activities.size()-1; x++) {
                    if (tempFinishDate.before(activities.get(x).getStartTime()) || tempFinishDate.before(activities.get(x).getEndTime())) {
                        throw new OperationNotAllowedException("There is an activity with start" +
                                " or finish date outside the interval of the projects dates");
                    }
                }
            }
            this.endTime.set(y, mo, d);
        } else {
            throw new OperationNotAllowedException("The finish date is before the start date");
        }
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

    public void deleteActivity(Activity activity) {
        activities.remove(activity);

    }

    public Activity getActivity(String serialNumber) {
        return activities.stream().filter(a -> a.getSerialNumber().equals(serialNumber)).findAny().orElse(null);
    }

    public void setProjectLeader(User projectLeader) {
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
