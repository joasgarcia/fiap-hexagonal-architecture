Feature: API - Customer

  Scenario: Save a new customer
    When save a new customer
    Then the customer is successfully saved

  Scenario: Search an existent customer by CPF
    Given the customer already registered
    When do a search by CPF
    Then the customer is displayed

  Scenario: Save a customer with e-mail already registered
    Given the customer already registered
    When save another customer with same e-mail
    Then an error is displayed indicating that e-mail already registered

  Scenario: Save a customer with CPF already registered
    Given the customer already registered
    When save another customer with same CPF
    Then an error is displayed indicating that CPF already registered