Feature: Add Employee

  Scenario: Add a user
	Given there is a user with the initials "ABC"
	When the user is added to the system
	Then there is a user in the system with the initials "ABC"

  Scenario: Add user with lower case initials
    Given there is a user with the initials "abc"
    When the user is added to the system
    Then there is a user in the system with the initials "ABC"

  Scenario: Adding multiple users to the system
    Given there is a user with the initials "abc"
    When the user is added to the system
    And there is a user with the initials "cba"
    And the user is added to the system
    Then there is 2 users in the system

  Scenario: Add a user with more initials than 4
    Given there is a user with the initials "ABCDE"
    When the user is added to the system
    Then the error message "The users initials are too long" is given

  Scenario: Add a user that is already added to the system
    Given there is a user with the initials "ABC"
    And the user is added to the system
    When the user is added to the system
    Then the error message "The user with the given initials is already in the system" is given

  Scenario: Add the same user with initials of different case
    Given there is a user with the initials "ABC"
    And the user is added to the system
    And there is a user with the initials "abc"
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
    And there is 1 user added to the activity

  Scenario: Assign a non-existing user to an activity
    Given the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity with serial "0001" is added to the project with ID "220001"
    When the user with initials "ABC" is assigned to the activity with serial "0001"
    Then the error message "User does not exist" is given

  Scenario: Assign a user to a non-existing activity
    Given the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And there is a user with the initials "ABC"
    When the user with initials "ABC" is assigned to the activity with serial "0001"
    Then the error message "Activity does not exist" is given

  Scenario: Assign a user to an activity that is already assigned to the activity
    Given there is a user with the initials "ABC"
    And a project is registered in the system
    And an activity is added to the project
    And the user with initials "ABC" is assigned to this activity
    When the user with initials "ABC" is assigned to the activity
    Then the error message "User is already assigned to this activity" is given

  Scenario: Remove a user from an activity
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And there are no activities in the project
    And an activity with serial "0001" is added to the project with ID "220001"
    When the user is removed from the activity
    Then the activity with serial "0001" in the project with ID "220001" does not have the user with initials "ABC" assigned

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
    
  Scenario: Remove a user from the system that is not in the system
    Given there is a user with the initials "ABC"
    And there is no users in the system
    When the user is removed from the system
    Then the error message "User is not in the system" is given