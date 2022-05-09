package dtu.timeManagement.app.presentationLayer;

import dtu.timeManagement.app.domains.Activity;
import dtu.timeManagement.app.Exceptions.OperationNotAllowedException;
import dtu.timeManagement.app.domains.Project;
import dtu.timeManagement.app.domains.TimeManagementApp;
import dtu.timeManagement.app.domains.User;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * This class is only purpose, is to enable us to initialize data on
 * start of application, so the application has some initial data on startup.
 *
 * There is no tests for this class, as it only temporary data initialization, replacing an eventual database.
 * @author Oliver Grønborg Christensen (s204479)
 */

public class InitialData {

    public static void initializeData(TimeManagementApp app) throws OperationNotAllowedException {
        //Get Calendar object for today
        Calendar calendarNow = new GregorianCalendar.Builder()
                .setDate(java.time.LocalDate.now().getYear(), java.time.LocalDate.now().getMonthValue(),
                        java.time.LocalDate.now().getDayOfMonth()).build();
        Calendar calendarYesterday = new GregorianCalendar.Builder()
                .setDate(java.time.LocalDate.now().getYear(), java.time.LocalDate.now().getMonthValue(),
                        java.time.LocalDate.now().getDayOfMonth() - 1).build();

        app.addUser(new User("ogc"));
        app.addUser(new User("ots"));
        app.addUser(new User("abc"));
        app.addUser(new User("lhb"));
        app.addUser(new User("huba"));
        app.addUser(new User("vh"));
        app.addUser(new User("nh"));
        app.addUser(new User("ma"));

        Project p = app.createProject();
        p.setName("Software Project");
        p.setProjectLeader(app.getUser("huba"));
        Activity a = p.createActivity();
        a.setActivityName("Make JUnit Tests");
        a.setExpectedHours(18);
        app.assignUserToActivity(app.getUser("huba"), a);
        app.assignUserToActivity(app.getUser("ogc"), a);
        app.assignUserToActivity(app.getUser("nh"), a);
        app.assignUserToActivity(app.getUser("vh"), a);
        app.getUser("huba").registerTime(calendarNow,5, p.getID(), a.getSerialNumber());
        app.getUser("ogc").registerTime(calendarNow,2, p.getID(), a.getSerialNumber());
        app.getUser("nh").registerTime(calendarNow,3, p.getID(), a.getSerialNumber());
        a = p.createActivity();
        a.setActivityName("Create business logic");
        a.setExpectedHours(4);
        app.assignUserToActivity(app.getUser("abc"), a);
        app.assignUserToActivity(app.getUser("ogc"), a);
        app.getUser("abc").registerTime(calendarNow,7, p.getID(), a.getSerialNumber());
        app.getUser("ogc").registerTime(calendarNow,3, p.getID(), a.getSerialNumber());
        app.getUser("nh").registerTime(calendarNow,1, p.getID(), a.getSerialNumber());
        a = p.createActivity();
        a.setActivityName("Fix bugs");
        a.setExpectedHours(100);
        app.assignUserToActivity(app.getUser("ma"), a);
        app.assignUserToActivity(app.getUser("nh"), a);
        app.assignUserToActivity(app.getUser("vh"), a);
        app.getUser("ma").registerTime(calendarNow,7, p.getID(), a.getSerialNumber());
        app.getUser("nh").registerTime(calendarNow,1, p.getID(), a.getSerialNumber());
        app.getUser("vh").registerTime(calendarNow,4, p.getID(), a.getSerialNumber());
        a = p.createActivity();
        a.setActivityName("Presentation of product");
        a.setExpectedHours(1);
        app.assignUserToActivity(app.getUser("ogc"), a);
        app.assignUserToActivity(app.getUser("ots"), a);
        app.assignUserToActivity(app.getUser("ma"), a);
        app.assignUserToActivity(app.getUser("nh"), a);
        app.assignUserToActivity(app.getUser("vh"), a);
        app.getUser("ots").registerTime(calendarNow,1, p.getID(), a.getSerialNumber());
        app.getUser("nh").registerTime(calendarNow,1, p.getID(), a.getSerialNumber());
        app.getUser("vh").registerTime(calendarNow,1, p.getID(), a.getSerialNumber());
        app.getUser("ogc").registerTime(calendarNow,1, p.getID(), a.getSerialNumber());
        app.getUser("ma").registerTime(calendarNow,1, p.getID(), a.getSerialNumber());

        p = app.createProject();
        p.setName("Math exam");
        p.setProjectLeader(app.getUser("ots"));
        a = p.createActivity();
        a.setActivityName("Make math report");
        a.setExpectedHours(21);
        app.assignUserToActivity(app.getUser("ogc"), a);
        app.assignUserToActivity(app.getUser("nh"), a);
        app.assignUserToActivity(app.getUser("vh"), a);
        app.assignUserToActivity(app.getUser("abc"), a);
        app.getUser("nh").registerTime(calendarYesterday,3, p.getID(), a.getSerialNumber());
        app.getUser("ots").registerTime(calendarYesterday,4, p.getID(), a.getSerialNumber());
        app.getUser("vh").registerTime(calendarYesterday,2, p.getID(), a.getSerialNumber());
        app.getUser("ogc").registerTime(calendarYesterday,7, p.getID(), a.getSerialNumber());
        a = p.createActivity();
        a.setActivityName("Prepare for examination");
        a.setExpectedHours(7);
        app.assignUserToActivity(app.getUser("ogc"), a);
        app.assignUserToActivity(app.getUser("nh"), a);
        app.assignUserToActivity(app.getUser("vh"), a);
        app.getUser("ots").registerTime(calendarYesterday,2, p.getID(), a.getSerialNumber());
        app.getUser("vh").registerTime(calendarYesterday,2, p.getID(), a.getSerialNumber());
        app.getUser("ogc").registerTime(calendarYesterday,3, p.getID(), a.getSerialNumber());
        a = p.createActivity();
        a.setActivityName("Do presentation");
        a.setExpectedHours(21);
        app.assignUserToActivity(app.getUser("ogc"), a);
        app.assignUserToActivity(app.getUser("nh"), a);
        app.assignUserToActivity(app.getUser("vh"), a);
        app.getUser("nh").registerTime(calendarYesterday,1, p.getID(), a.getSerialNumber());
        app.getUser("ots").registerTime(calendarYesterday,1, p.getID(), a.getSerialNumber());
        app.getUser("vh").registerTime(calendarYesterday,1, p.getID(), a.getSerialNumber());
        app.getUser("ogc").registerTime(calendarYesterday,1, p.getID(), a.getSerialNumber());
    }



}
