Feature: As a user i can search tips with tags

  Scenario: user can use search form
    Given the page displaying all tips is selected
    When wanted tags "" typed and search button clicked
    Then result gives found tip "testi" with tags asked

  Scenario: User can choose to search for only tips not read
    Given front page is selected
    When checkbox show only tips not read is checked
    When search text "" is given and search button clicked
    Then tips already read isnt shown