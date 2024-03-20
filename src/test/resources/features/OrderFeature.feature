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

  Scenario: Finish an order
    Given the customer is already registered
    And the order product is already registered
    And the order item is already registered
    And the order is already registered
    When finish an order
    Then the order is successfully finished
