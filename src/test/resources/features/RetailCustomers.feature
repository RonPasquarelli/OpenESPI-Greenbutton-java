Feature: Retail Customers
  As a Retail Customer,
  I want to be able to view my usage point in my browser
  So that I can see my UsagePoints

  Scenario: Retail Customers logs into Third Party
    Given I have a Retail Customer account
    And There is a Third Party

    When I log in as Alan Turing
    And I click on the Select Third Party link
    And I select the Third Party

    Then I should be taken to the Third Party login page
