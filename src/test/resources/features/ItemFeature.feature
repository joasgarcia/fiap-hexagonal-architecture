Feature: API - Item

  Scenario: Save a new item
    Given the product is already registered
    When save a new item
    Then the item is successfully saved

  Scenario: Save a new item with no product list
    Given the product is already registered
    When save a new item with no product list
    Then an error is displayed indicating that product list not found

  Scenario: Delete an existent item
    Given the product is already registered
    And the item is already registered
    When delete an existent item
    Then the item is successfully deleted

  Scenario: Delete a nonexistent item
    When delete a nonexistent item
    Then an error is displayed indicating that item not found


