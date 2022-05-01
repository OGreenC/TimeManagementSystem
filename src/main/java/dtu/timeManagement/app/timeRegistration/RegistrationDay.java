package dtu.timeManagement.app.timeRegistration;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.Calendar;

public class RegistrationDay {

    private Calendar date;

    private ArrayList<RegistrationUnit> registrationUnits = new ArrayList<>();

    public RegistrationDay(Calendar date) {
        this.date = date;
    }

    public ArrayList<RegistrationUnit> getRegistrationUnits() {
        return registrationUnits;
    }

    public RegistrationUnit getRegistrationUnit(String projectID, String activitySerial) {
        for (RegistrationUnit u: registrationUnits) {
            if(u.getProjectID() == projectID && u.getActivitySerial() == activitySerial) {
                return u;
            }
        }
        return null;
    }

    public void addRegistrationUnit(RegistrationUnit u) throws OperationNotAllowedException{
        if(getTotalHoursOnDay() + u.getHours() > 24) {
            throw new OperationNotAllowedException("Can't register more than 24 hours a day");
        }

        RegistrationUnit existingRegistration = getRegistrationUnit(u.getProjectID(),u.getActivitySerial());

        if(existingRegistration != null) {
            existingRegistration.setHours(u.getHours());
        } else {
            registrationUnits.add(u);
        }
    }

    public void removeRegistrationUnit(String projectID, String activitySerial) {
        RegistrationUnit u = getRegistrationUnit(projectID, activitySerial);
        if(u != null) {
            registrationUnits.remove(u);
        }
    }

    public int getTotalHoursOnDay() {
        int hours = 0;
        for (RegistrationUnit u : registrationUnits) {
            hours += u.getHours();
        }
        return hours;
    }

}
