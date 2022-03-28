Feature: Add Employee

Scenario: Add a user
	Given there is a user with the initials "ABC"
	When the user is added to the system
	Then there is a user in the system with the initials "ABC"


