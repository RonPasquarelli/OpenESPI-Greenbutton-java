Feature: Notification
  As Data Custodian,
  I should be able to notify Third Party about data updates,
  So Third Party can download updated data

  Scenario: Data Custodian notifies Third Party of updated Subscription
    Given a Retail Customer with Usage Points
    And a Third Party with an updated subscription

    When I notify the Third Party

    Then the Third Party should be notified of the update