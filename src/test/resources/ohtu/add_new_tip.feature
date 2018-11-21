Feature: As a user can add new tip to database

  Scenario: user can navigate to add new tip view
    Given front page is selected
    When list all tips clicked
    When new tip is clicked
    Then new tip view is open

  Scenario: user wants to add tip
    Given new tip view is selected
    When otsikko "title", kirjoittaja "writer", tyyppi "type" are given
    Then . . .