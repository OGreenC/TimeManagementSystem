Feature: Edit Activity
    Description: Edit an existing activity
    Actor: User

Scenario: Set expected hours
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity is added to the project
    When the expected hours of the activity with serial "0001" is changed to "200"
    Then the activity with serial "0001" is expected to take "200" hours

Scenario: Set startdate without finishdate
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" startDate is set to year 2022 month 4 date 20
    Then the activity with the serial "0001" has the startdate year 2022 month 4 date 20

Scenario: Set finishdate without startdate
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" finishDate is set to year 2022 month 4 date 20
    Then the activity with the serial "0001" has the finishdate year 2022 month 4 date 20

Scenario: Set startdate after finishdate
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity is added to the project
    And the activity with the serial "0001" finishDate is set to year 2022 month 4 date 20
    When the activity with the serial "0001" startDate is set to year 2022 month 5 date 21
    Then the error message "The finish date is before the start date" is given

Scenario: Set finishdate before startdate
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity is added to the project
    And the activity with the serial "0001" startDate is set to year 2022 month 6 date 20
    When the activity with the serial "0001" finishDate is set to year 2022 month 5 date 20
    Then the error message "The finish date is before the start date" is given

Scenario: Set activity name
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity is added to the project
    When the activity with the serial "0001" name is set to "ActivityName"
    Then the activity with the serial "0001" has the name "ActivityName"

Scenario: Activity has started
    Given there is a user with the initials "ABC"
        And the year is "2022"
        And no projects have been created
        And a project is registered in the system
        And there is a project with ID "220001"
        And there are no activities in the project
        And an activity is added to the project
        When the activity with the serial "0001" startDate is set to year 2020 month 1 date 1
        Then the activity with the serial "0001" has started

Scenario: Activity has ended
    Given there is a user with the initials "ABC"
        And the year is "2022"
        And no projects have been created
        And a project is registered in the system
        And there is a project with ID "220001"
        And there are no activities in the project
        And an activity is added to the project
        When the activity with the serial "0001" finishDate is set to year 2020 month 1 date 2
        Then the activity with the serial "0001" has ended