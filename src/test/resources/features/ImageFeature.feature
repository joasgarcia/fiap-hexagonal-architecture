Feature: API - Image

  Scenario: Save a new image
    Given the product image is already registered
    When save a new image
    Then the image is successfully saved

  Scenario: Update an existent image
    Given the product image is already registered
    And the image is already registered
    When update a image
    Then the image is successfully updated

  Scenario: Update a nonexistent image
    When update a nonexistent image
    Then an error is displayed indicating that image not found

  Scenario: Delete an existent image
    Given the product image is already registered
    And the image is already registered
    When delete a image
    Then the image is successfully deleted

  Scenario: Delete a nonexistent image
    When delete a nonexistent image
    Then an error is displayed indicating that image not found

  Scenario: Find all images by product id
    Given the product image is already registered
    And save a new image
    And save another new image
    When all images by product id is requested
    Then the image list is displayed
