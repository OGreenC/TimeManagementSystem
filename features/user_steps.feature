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

