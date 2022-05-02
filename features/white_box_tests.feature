Feature: whitebox tests

#Niels Whitebox tests
  Scenario: Input Set A - users is empty user = "Dick"
        Given the year is "2022"
        And no projects have been created
        And a project is registered in the system
        And there is a project with ID "220001"
        And there are no activities in the project
        And an activity with serial "0001" is added to the project with ID "220001"
        When the user with initials "Dick" is assigned to the activity with serial "0001"
        Then the error message "User does not exist" is given

  Scenario: Input Set B - users = ["Nick"] user = "Nick"
      Given there is a user with the initials "Nick"
      And the year is "2022"
      And no projects have been created
      And a project is registered in the system
      And there is a project with ID "220001"
      And there are no activities in the project
      And an activity with serial "0001" is added to the project with ID "220001"
      When the user with initials "Nick" is assigned to the activity with serial "0001"
      Then the activity with serial "0001" in the project with ID "220001" has the user with initials "Nick" assigned

