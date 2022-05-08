# Author: Oliver Tobias Siggaard (s204450)

Feature: Add User
  Description: Add a user to the system
  Actor: User

  Scenario: Add a user
	Given there is a user with the initials "ABC"
	When the user is added to the system
	Then there is a user in the system with the initials "ABC"

  Scenario: Add user with lower case initials
    Given there is a user with the initials "abc"
    When the user is added to the system
    Then there is a user in the system with the initials "ABC"

  Scenario: Adding multiple users to the system
    Given there is a user with the initials "ABC"
    When the user is added to the system
    And there is a user with the initials "CBA"
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