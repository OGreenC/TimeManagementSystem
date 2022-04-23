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
    And an activity with the name "Activity1" is added to the project with ID "220001"
    When the user with initials "ABC" is assigned to the activity named "Activity1" in the project with ID "220001"
    Then the activity named "Activity1" in the project with ID "220001" has the user with initials "ABC" assigned

#  Scenario: Assign user to non-existing project
#    Given there is a user with the initials "ABC"
#    And a project with ID "220001" is not in the system
#    When the user with initials "ABC" is assigned to the project with ID "220001"
#    Then the error message "Project does not exist" is given


  Scenario: Remove a user from an activity
    Given there is a user with the initials "ABC"
    And the year is "2022"
    And no projects have been created
    And a project is registered in the system
    And there is a project with ID "220001"
    And an activity with the name "Activity1" is added to the project with ID "220001"
    When the user with initials "ABC" is removed from the activity named "Activity1" in the project with ID "220001"
    Then the activity named "Activity1" in the project with ID "220001" does not have the user with initials "ABC" assigned

  #Scenario: Remove a user from the system
  # Given there is a user with the initials "ABC"
  # When the user with initials "ABC" is removed from the system
  # Then there is no user with the initials "ABC" in the system
  # And no project has a project leader with the initials "ABC"
  # And no project has the user with the initials "ABC" assigned to it
  # And no time should be assigned to the user with the initials "ABC"