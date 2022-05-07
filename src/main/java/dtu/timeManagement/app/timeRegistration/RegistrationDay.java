package dtu.timeManagement.app.timeRegistration;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class written by:
 * s204470 - Oliver Grønborg Christensen
 *
 * RegistrationDay objects hold all registration instances on a given date, for each user.
 * Each user has its own RegistrationDay object, for each day.
 */

public class RegistrationDay {

    private Calendar date;

    private ArrayList<RegistrationInstance> registrationInstances = new ArrayList<>();

    public RegistrationDay(Calendar date) {
        this.date = date;
    }

    public ArrayList<RegistrationInstance> getRegistrationUnits() {
        return registrationInstances;
    }

    public RegistrationInstance getRegistrationUnit(String projectID, String activitySerial) {
        for (RegistrationInstance u: registrationInstances) {
            if(u.getProjectID() == projectID && u.getActivitySerial() == activitySerial) {
                return u;
            }
        }
        return null;
    }

    public void addRegistrationUnit(RegistrationInstance u) {
        RegistrationInstance existingRegistration = getRegistrationUnit(u.getProjectID(),u.getActivitySerial());

        if(existingRegistration != null) {
            existingRegistration.setHours(u.getHours());
        } else {
            registrationInstances.add(u);
        }
    }

    public void removeRegistrationUnit(String projectID, String activitySerial) {
        RegistrationInstance u = getRegistrationUnit(projectID, activitySerial);
        if(u != null) {
            registrationInstances.remove(u);
        }
    }

    public int getTotalHoursOnDay() {
        int hours = 0;
        for (RegistrationInstance u : registrationInstances) {
            hours += u.getHours();
        }
        return hours;
    }

}
