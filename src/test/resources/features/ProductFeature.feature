Feature: API - Product

  Scenario: Save a new product
    When save a new product
    Then the product is successfully saved

  Scenario: Update an existent product
    Given the product already registered
    When update a product
    Then the product is successfully updated

  Scenario: Update a nonexistent product
    When update a nonexistent product
    Then an error is displayed indicating that product not found

  Scenario: Delete an existent product
    Given the product already registered
    When delete a product
    Then the product is successfully deleted

  Scenario: Delete a nonexistent product
    When delete a nonexistent product
    Then an error is displayed indicating that product not found

  Scenario: List all products
    Given save a new product
    And a another product is saved
    When a product list is requested
    Then the product list is displayed

  Scenario: Find all products by category
    Given save a new product
    And save a new product
    When all products by category is requested
    Then the product list is displayed

