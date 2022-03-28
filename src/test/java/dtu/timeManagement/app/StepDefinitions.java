package dtu.timeManagement.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.timeManagement.app.TimeManagementApp;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

	private TimeManagementApp timeManagementApp;


	public StepDefinitions(TimeManagementApp timeManagementApp) {
		this.timeManagementApp = timeManagementApp;
	}

	@When("I do nothing")
	public void i_do_nothing() {
	    timeManagementApp.sendMessage();
	}

	@Then("everything is okay")
	public void everything_is_okay() {
	    assertTrue(true);
	}

}

