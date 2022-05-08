package dtu.timeManagement.app;

import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.timeRegistration.RegistrationDay;
import dtu.timeManagement.app.timeRegistration.RegistrationInstance;

import java.util.*;


public class User {
    private final String initial;
    private final ArrayList<Activity> activities = new ArrayList<Activity>();
    HashMap<String, RegistrationDay> timeRegistration = new HashMap<String, RegistrationDay>();


    public User(String initial) {
        this.initial = initial.toUpperCase();
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

        //Pre-conditions:
        assert date != null && projectID != null && activitySerial != null;

        if(hours <= 0) {    //implicit pre-condition                                                                    //1
            throw new OperationNotAllowedException("Can't register 0 or negative hours on activity");                   //2
        } else {
            //Get (or create if not existing) time registration day object from map.
            String dateString = DateServer.getDateAsString(date);                                                       //3
            RegistrationDay registrationDay = timeRegistration.get(dateString);                                         //4
            if(registrationDay == null) {                                                                               //5
                registrationDay = new RegistrationDay(date);                                                            //6
                timeRegistration.put(dateString, registrationDay);                                                      //7
            }

            if(registrationDay.getTotalHoursOnDay() + hours <= 24) {    //implicit pre-condition                        //8
                //create and add registration instance
                RegistrationInstance registrationInstance = new RegistrationInstance(hours,projectID,activitySerial);   //9
                registrationDay.addRegistrationUnit(registrationInstance);                                              //10
            } else {
                throw new OperationNotAllowedException("Can't register more than 24 hours a day");                      //11
            }
        }

        //Post-conditions (Only ran, if all pre-conditions is satisfied (including implicit pre-conditions))
        RegistrationInstance registerInstance = timeRegistration.get(DateServer.getDateAsString(date)).getRegistrationUnit(projectID, activitySerial);
        assert Objects.equals(registerInstance.getProjectID(), projectID);
        assert Objects.equals(registerInstance.getActivitySerial(), activitySerial);
        assert registerInstance.getHours() == hours;
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