Feature: Add Employee

  Scenario: Add a user
	Given there is a user with the initials "ABC"
	When the user is added to the system
	Then there is a user in the system with the initials "ABC"

  Scenario: Add a user that is already added to the system
    Given there is a user with the initials "ABC"
    And the user is added to the system
    When the user is added to the system
    Then the error message "The user with the given initials is already in the system" is given

  Scenario: Assign a user to an activity
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity with serial "0001" is added to the project with ID "220001"
    When the user with initials "ABC" is assigned to the activity with serial "0001"
    Then the activity with serial "0001" in the project with ID "220001" has the user with initials "ABC" assigned

  Scenario: Assign a non-existing user to an activity
    Given the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity with serial "0001" is added to the project with ID "220001"
    When the user with initials "ABC" is assigned to the activity with serial "0001"
    Then the error message "User does not exist" is given

  Scenario: Remove a user from an activity
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity with serial "0001" is added to the project with ID "220001"
    When the user with initials "ABC" is removed from the activity with serial "0001"
    Then the activity with serial "0001" in the project with ID "220001" does not have the user with initials "ABC" assigned

  Scenario: Remove a non-existing user from an activity
    Given the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity with serial "0001" is added to the project with ID "220001"
    When the user with initials "ABC" is removed from the activity with serial "0001"
    Then the error message "User does not exist" is given

  Scenario: Remove a user from the system
    Given a project is registered in the system
    And an activity is added to the project
    And there is a user in the system
    And the user is added to the activity
    And the user is set as project leader of the project
    When the user is removed from the system
    Then the user is not in the system
    And no project has the user as project leader
    And no activity has the user assigned to it