package dtu.timeManagement.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

// The DateServer has not 100% code coverage, as it basically replaced immediately by the
// mock date server for testing.

/**
 * @author Victor Hyltoft (s214964) with inspiration from the library-app provided as an example from the course
 */
public class DateServer {

    /**
     * Return the current date without the current time.
     * @return the current date without the current time
     */
    public Calendar getDate() {
        Calendar calendar = new GregorianCalendar();
        return new GregorianCalendar(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String getDateAsString(Calendar c) {
        return "" + c.get(Calendar.YEAR) + ":" + c.get(Calendar.MONTH) + ":" + c.get(Calendar.DAY_OF_MONTH);
    }

}
