Feature: Delete Project
    Description: Delete a project
    Actor: User

Scenario: Successfully delete a project
    Given a project is registered in the system
    When the project is deleted
    Then the project does not exist

Scenario: Delete a non-existing project
    Given a project is registered in the system
    When the project is deleted
    And the project is deleted
    Then the error message "Project does not exist" is given



# These scenario might not be needed in our current implementation as
# we store the activities of the project in the project instance
# so when we delete a project, then everything part of the project
# is automatically deleted.
# Furthermore we can not check if the activities exists or not anymore
# because the project instance is null...

#Scenario: Successfully delete an empty project
#    Given a project is registered in the system
#    # The line below might not be needed...
#    And there are no activities in the project
#    When the project is deleted
#    Then the project does not exist

# This scenario could be useful if we wanted to move the activities to another
# location (like a trash can which serves as a backup) or simply another project

#Scenario: Successfully delete a project with activities
#    Given a project is registered in the system
#    And an activity is registered to the project
#    And an activity is registered to the project
#    And the project has activities
#    When the project is deleted
#    Then the project does not exist
##    And the activities are moved to the trash

