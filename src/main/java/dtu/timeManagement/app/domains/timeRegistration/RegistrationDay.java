package dtu.timeManagement.app.domains.timeRegistration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * Class written by:
 * @author s204470 - Oliver Grønborg Christensen
 *
 * RegistrationDay objects hold all registration instances on a given date, for each user.
 * Each user has its own RegistrationDay object, for each day.
 */

public class RegistrationDay {

    private final ArrayList<RegistrationInstance> registrationInstances = new ArrayList<>();

    public RegistrationDay(Calendar date) {
    }

    public ArrayList<RegistrationInstance> getRegistrationUnits() {
        return registrationInstances;
    }

    public RegistrationInstance getRegistrationUnit(String projectID, String activitySerial) {
        for (RegistrationInstance u: registrationInstances) {
            if(Objects.equals(u.getProjectID(), projectID) && Objects.equals(u.getActivitySerial(), activitySerial)) {
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
