Feature: Register time
  Description: Register time for an activity
  Actor: User

  Scenario: Successfully register time for activity
    Given a project is registered in the system
    And an activity is added to the project
    And there is a user in the system
    And there are no activities in the project
    When ...