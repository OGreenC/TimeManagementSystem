package dtu.timeManagement.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Project {
    private static int nextSerial = 1;

    private String projectID;
    private String name;
    private Calendar calendar = Calendar.getInstance();

    private final ArrayList<Activity> activities = new ArrayList<>();

    public Project() {
        this.projectID = generateID();
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

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public Calendar getStartDate() {
        return calendar;
    }

    public void setStartDate(int y, int mo, int d) {
        System.out.println(y + " " + mo + " " + " " + d);
        this.calendar.set(y, mo, d);
    }
}
