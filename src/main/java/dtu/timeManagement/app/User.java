package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.timeRegistration.RegistrationDay;
import dtu.timeManagement.app.timeRegistration.RegistrationInstance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;



public class User {
    private final String initial;
    private final ArrayList<Activity> activities = new ArrayList<Activity>();
    HashMap<String, RegistrationDay> timeRegistration = new HashMap<String, RegistrationDay>();


    public User(String initial) {
        this.initial = initial;
    }

    public String getInitial() {
        return initial;
    }

    /**
     *
     * Method written and used in report by:
     * s204479 - Oliver Grønborg Christensen
     *
     * @param date
     * @param hours
     * @param projectID
     * @param activitySerial
     * @throws OperationNotAllowedException
     */
    public void registerTime(Calendar date, int hours, String projectID, String activitySerial) throws OperationNotAllowedException {
        //Get (or create if not existing) time registration day object from map.
        String dateString = DateServer.getDateAsString(date);
        RegistrationDay day = timeRegistration.get(dateString);
        if(day == null) {
            day = new RegistrationDay(date);
            timeRegistration.put(dateString, day);
        }

        //create and add registration instance
        RegistrationInstance registrationInstance = new RegistrationInstance(hours,projectID,activitySerial);
        day.addRegistrationUnit(registrationInstance); //Throws OperationNotAllowedException, if combined hours on day surpasses above 24 hours.
    }

    public RegistrationDay getTimeRegistrationDay(Calendar date) {
        return timeRegistration.get(DateServer.getDateAsString(date));
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }
    public boolean match(String searchText) {
        return initial.contains(searchText);
    }
}
