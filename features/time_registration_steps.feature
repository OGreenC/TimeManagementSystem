Feature: Register time
  Description: Register time for an activity
  Actor: User

  Scenario: Successfully register time for activity on date
   Given a project is registered in the system
    And an activity is added to the project
    And there is a user in the system
    When the user registers 4 hours on the activity on year 2022 month 3 date 5
    Then the user has registered 4 hours on the activity on year 2022 month 3 date 5

  Scenario: Successfully replace register time for instance
    Given a project is registered in the system
    And an activity is added to the project
    And there is a user in the system
    When the user registers 4 hours on the activity on year 2022 month 3 date 5
    When the user registers 3 hours on the activity on year 2022 month 3 date 5
    Then the user has registered 3 hours on the activity on year 2022 month 3 date 5

  Scenario: Register more than 24 hours on a single activity
    Given a project is registered in the system
    And an activity is added to the project
    And there is a user in the system
    When the user registers 25 hours on the activity on year 2022 month 3 date 5
    Then the error message "Can't register more than 24 hours a day" is given

  Scenario: Register more than 24 hours on a single day combined
    Given a project is registered in the system
    And an activity is added to the project
    And there is a user in the system
    And the user registers 16 hours on the activity on year 2022 month 3 date 5
    When the user registers 12 hours on the activity on year 2022 month 3 date 5
    Then the error message "Can't register more than 24 hours a day" is given

  Scenario: Register 0 or less hours to activity
    Given a project is registered in the system
    And an activity is added to the project
    And there is a user in the system
    When the user registers 0 hours on the activity
    Then the error message "Can't register 0 or negative hours on activity" is given

  Scenario: Successfully delete time registration
    Given a project is registered in the system
    And an activity is added to the project
    And there is a user in the system
    And the user registers 16 hours on the activity on year 2022 month 3 date 5
    When the user removes time registration of activity on year 2022 month 3 date 5
    Then there is no time registration for activity on year 2022 month 3 date 5

  Scenario: Successfully delete time registration
    Given a project is registered in the system
    And an activity is added to the project
    And there is a user in the system
    And the user registers 16 hours on the activity on year 2022 month 3 date 5
    When the user removes time registration of activity on year 2022 month 3 date 5
    Then there is no time registration for activity on year 2022 month 3 date 5

  Scenario: Successfully add time registrations on multiple activities in project
    Given a project is registered in the system
    And an activity is added to the project
    And there is a user in the system
    And the user registers 3 hours on the activity on year 2022 month 3 date 5
    And a project is registered in the system
    And an activity is added to the project
    When the user registers 4 hours on the activity on year 2022 month 3 date 5
    Then there is 2 time registrations on year 2022 month 3 date 5