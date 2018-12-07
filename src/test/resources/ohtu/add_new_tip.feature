Feature: As a user can add new tip to database

  Scenario: user can navigate to add new tip view
    Given front page is selected
    When list all tips clicked
    When new tip is clicked
    Then new tip view is open

  Scenario: user wants to add tip
    Given new tip view is selected
    When otsikko "testi", tekija "writer", kuvaus "testi addays", linkki "/testiroute/index.html" are given
    Then list all view is open

  Scenario: user adds tip with tags about video
    Given new tip view is selected
    When video "www.youtube.com/mustwatch" and tags "test,amazing" are given
    When heading of the tip with otsikko "tagsTest" is clicked
    Then automatic tag can be found

  Scenario: user can give valid isbn and system automatically adds info
    Given new tip view is selected
    When isbn "0553293354" is given
    Then fields are occupied

  Scenario: user can give unvalid isbn and system does nothing
    Given new tip view is selected
    When isbn "1234567890" is given
    Then fields are not occupied