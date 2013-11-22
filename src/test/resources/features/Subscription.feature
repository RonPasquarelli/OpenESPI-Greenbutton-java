Feature: Subscription
  As a Third Party
  I want to access Retail Customer data
  So that I can display Retail Customer data

  Scenario: Third Party accesses Subscription
    Given an authorized Third Party
    And I log in as Alan Turing into Data Custodian

    When I log in as Alan Turing
    And I request a Subscription using the REST API
    Then I should receive XML for that Subscription

  Scenario: Third Party customer accesses Authorizations page
    Given an authorized Third Party

    When I log in as Alan Turing
    And I navigate to the Authorizations page
    Then I should see a list of Authorizations
