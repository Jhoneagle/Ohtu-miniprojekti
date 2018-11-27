Feature: As a user I can view detailed view of one tip at a time

  Scenario: User can navigate to single tip view
    Given the page displaying all tips is selected
    When heading of the chosen tip is clicked
    Then single tip details are displayed on a separate page
