# Author: Niels Thormann (s216160)

Feature: Edit Activity
  Description: Edit an existing activity
  Actor: User

  Scenario: Set expected hours
    Given a project is registered in the system
    And there are no activities in the project
    And an activity is added to the project
    When the expected hours of the activity with serial "0001" is changed to "200"
    Then the activity with serial "0001" is expected to take "200" hours

  Scenario: Set startdate without finishdate
    Given a project is registered in the system
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" startDate is set to year 2022 month 4 date 20
    Then the activity with the serial "0001" has the startdate year 2022 month 4 date 20

  Scenario: Set finishdate without startdate
    Given a project is registered in the system
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" finishDate is set to year 2022 month 4 date 20
    Then the activity with the serial "0001" has the finishdate year 2022 month 4 date 20

  Scenario: Set startdate after finishdate
    Given a project is registered in the system
    And there are no activities in the project
    And an activity is added to the project
    And the activity with the serial "0001" finishDate is set to year 2022 month 4 date 20
    When the activity with the serial "0001" startDate is set to year 2022 month 5 date 21
    Then the error message "The finish date is before the start date" is given

  Scenario: Set finishdate before startdate
    Given a project is registered in the system
    And there are no activities in the project
    And an activity is added to the project
    And the activity with the serial "0001" startDate is set to year 2022 month 6 date 20
    When the activity with the serial "0001" finishDate is set to year 2022 month 5 date 20
    Then the error message "The finish date is before the start date" is given

  Scenario: Set activity name
    Given a project is registered in the system
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" name is set to "ActivityName"
    Then the activity with the serial "0001" has the name "ActivityName"

  Scenario: Activity has started
    Given a project is registered in the system
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" startDate is set to year 2020 month 1 date 1
    Then the activity with the serial "0001" has started

  Scenario: Activity has ended
    Given a project is registered in the system
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" finishDate is set to year 2020 month 1 date 2
    Then the activity with the serial "0001" has ended

  Scenario: Set start date before the project start date
    Given a project is registered in the system
    And the startDate is set to year 2022 month 5 date 21
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" startDate is set to year 2022 month 4 date 20
    Then the error message "The start date is not in the interval of the projects dates" is given

  Scenario: Set start date after the project finish date
    Given a project is registered in the system
    And the finishDate is set to year 2022 month 5 date 21
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" startDate is set to year 2022 month 6 date 20
    Then the error message "The start date is not in the interval of the projects dates" is given

  Scenario: Set finish date after the project finish date
    Given a project is registered in the system
    And the finishDate is set to year 2022 month 5 date 21
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" finishDate is set to year 2022 month 6 date 20
    Then the error message "The finish date is not in the interval of the projects dates" is given

  Scenario: Set finish date before the project start date
    Given a project is registered in the system
    And the startDate is set to year 2022 month 5 date 21
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" finishDate is set to year 2022 month 4 date 20
    Then the error message "The finish date is not in the interval of the projects dates" is given