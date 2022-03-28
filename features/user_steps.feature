Feature: Add Employee

Scenario: Add a user
	Given there is a user with the name "Anders Bo Hansen" and the initials "ABH"
	When the user is added to the system
	Then there is a user in the system with the initials "ABH" with the name "Anders Bo Hansen"


