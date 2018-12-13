Feature: As a user I can view detailed view of one tip at a time

  Scenario: User can navigate to single tip view
    Given the page displaying all tips is selected
    When heading of the chosen tip is clicked
    Then single tip details are displayed on a separate page

#  Scenario: User can sign tip read
#    Given the page displaying all tips is selected
#    When heading of the chosen tip is clicked
#    When submitted read of the tip
#    Then tip is signed read and have returned list view

  Scenario: User can go tips edit view
    Given the page displaying all tips is selected
    When heading of the tip with otsikko "tagsTest" is clicked
    When pressed the edit button
    Then view for editing tip is opened

  Scenario: User can go tips edit view
    Given the tips otsikko "admin" and selected the tip
    When pressed the edit button
    When details been chanced with "123"
    Then new details with "123" can be seen in the "admin" tips info

  Scenario: User can add comment to tip
    Given the page displaying all tips is selected
    When heading of the tip with otsikko "admin123" is clicked
    When details nick "aloha" and content "peace man!" for comment are given
    Then tip has new comment with nick "aloha" and content "peace man!"