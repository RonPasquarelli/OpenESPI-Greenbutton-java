Feature: Retail Customers
  As a Retail Customer,
  I want to be able to view my usage point in my browser
  So that I can see my UsagePoints

  Scenario: Retail Customer authorizes Usage Points access
    Given I have a Retail Customer account

    When I log in as Alan Turing into Third Party
    And I look at my Usage Points page
    And I enter my username and password
    And I authorize Third Party
    Then I should see Usage Point with title "House meter"

  Scenario: Retail Customer denies Usage Points access
    Given I have a Retail Customer account

    When I log in as Alan Turing into Third Party
    And I look at my Usage Points page
    And I enter my username and password
    And I deny Third Party
    Then I should be redirected to the home page
