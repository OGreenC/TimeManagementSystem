package dtu.timeManagement.app;

import java.util.Calendar;
import java.util.Date;

public class Project {
    private String projectID;
    private static int nextSerial = 1;

    public Project() {
        this.projectID = generateID();
    }

    public String generateID() {
        int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
        String serial = String.format("%04d", nextSerial);
        nextSerial++;
        System.out.println(year + serial);
        return year + serial;
    }

    public String getID() {
        return projectID;
    }
}
