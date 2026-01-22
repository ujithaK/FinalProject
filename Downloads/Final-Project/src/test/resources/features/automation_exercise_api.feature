Feature: Automation Exercise API Validation

  As a QA engineer
  I want to validate AutomationExercise public APIs
  So that product, brand, search, and login APIs work as expected

  Background:
    Given the Automation Exercise API is available

  # ===================== PRODUCTS =====================

  Scenario: Get all products list successfully
    When I send a GET request to "/productsList"
    Then the API response code should be 200

  Scenario: POST request to products list should not be allowed
    When I send a POST request to "/productsList"
    Then the API response code should be 405

  # ===================== BRANDS =====================

  Scenario: Get all brands list successfully
    When I send a GET request to "/brandsList"
    Then the API response code should be 200

  Scenario: PUT request to brands list should not be allowed
    When I send a PUT request to "/brandsList"
    Then the API response code should be 405

  # SEARCH

  Scenario Outline: Search product using keywords
    When I search product with keyword "<keyword>"
    Then the API response code should be <statusCode>

    Examples:
      | keyword | statusCode |
      | dress   | 200        |
      |         | 400        |

  # ===================== LOGIN =====================

  Scenario Outline: Verify login API behavior
    When I verify login using email "<email>" and password "<password>"
    Then the API response code should be <statusCode>

    Examples:
      | email              | password  | statusCode |
      | ujitha@gmail.com   | uji@123   | 200        |
      | wrong@test.com     | wrongpass | 404        |

  Scenario: Verify login without email parameter
    When I verify login without email
    Then the API response code should be 400

  # ===================== USER DETAILS =====================

  Scenario Outline: Get user details by email
    When I get user details for email "<email>"
    Then the user details API should respond correctly

    Examples:
      | email             |
      | apiuser@test.com  |
