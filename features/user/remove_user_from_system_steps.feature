# Author: Oliver Tobias Siggaard (s204450)

Feature: Remove user from system
  Description: Remove a user from the system
  Actor: User

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