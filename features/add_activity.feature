Feature: Add activity
  Description: Add a new activity to a project
  Actor: User

  Scenario: Successfully add a new activity to an existing project
    Given there is a project
    When the user adds the activity with the name "ActivityName" to the project
    Then the activity with the name "ActivityName" is added to the project
