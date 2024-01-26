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

