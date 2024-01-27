Feature: API - Order

  Scenario: Save a new order
    Given the customer is already registered
    And the order product is already registered
    And the order item is already registered
    When save a new order
    Then the order is successfully saved

  Scenario: Find an existent order
    Given the customer is already registered
    And the order product is already registered
    And the order item is already registered
    And the order is already registered
    When an order by id is requested
    Then the order is successfully displayed

  Scenario: Find a nonexistent order
    When an non existent order by id is requested
    Then an error is displayed indicating that order not found

  Scenario: Update status of an existent order
    Given the customer is already registered
    And the order product is already registered
    And the order item is already registered
    And the order is already registered
    When the order status is updated
    Then the order status is successfully updated

  Scenario: Update status of a nonexistent order
    When the nonexistent order status is updated
    Then an error is displayed indicating that order not found

  Scenario: Update payment status of an existent order
    Given the customer is already registered
    And the order product is already registered
    And the order item is already registered
    And the order is already registered
    When the order payment status is updated
    Then the order payment status is successfully updated

  Scenario: Update payment status of a nonexistent order
    When the nonexistent order payment status is updated
    Then an error is displayed indicating that order not found
