package dtu.timeManagement.app;


import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Victor Hyltoft (s214964) with inspiration from the library-app from the course
 */
public class MockDateHolder {
    DateServer dateServer = mock(DateServer.class);

    public MockDateHolder(TimeManagementApp timeManagementApp) {
        GregorianCalendar calendar = new GregorianCalendar();
        setDate(calendar);
        timeManagementApp.setDateServer(dateServer);
    }

    public void setDate(Calendar calendar) {
        Calendar c = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        when(this.dateServer.getDate()).thenReturn(c);
    }

    public Calendar getDate() {
        return dateServer.getDate();
    }

    public void advanceDateByYears(int years) {
        Calendar currentDate = dateServer.getDate();
        // Important: we need to create a new object,
        // otherwise, the old calendar object gets changed,
        // which suddenly changes the date for objects
        // using that old calendar object
        Calendar nextDate = new GregorianCalendar();
        nextDate.setTime(currentDate.getTime());
        nextDate.add(Calendar.YEAR, years);
        setDate(nextDate);
    }
}
