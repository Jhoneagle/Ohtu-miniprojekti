Feature: As a user i can search tips with tags

  Scenario: user can use search form
    Given the page displaying all tips is selected
    When wanted tags "" typed and search button clicked
    Then result gives found tip "testi" with tags asked