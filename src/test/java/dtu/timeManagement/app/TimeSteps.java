package dtu.timeManagement.app;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.Calendar;

/**
 * @author Victor Hyltoft (s214964)
 */
public class TimeSteps {
    MockDateHolder dateHolder;

    public TimeSteps(MockDateHolder dateHolder) {
        this.dateHolder = dateHolder;
    }

    @When("{int} years has passed")
    public void yearsHasPassed(int years) {
        dateHolder.advanceDateByYears(years);
    }

    @Given("the year is {string}")
    public void theYearIs(String year) {
        Calendar c = dateHolder.getDate();
        c.set(Integer.parseInt(year), c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        dateHolder.setDate(c);
    }
}
