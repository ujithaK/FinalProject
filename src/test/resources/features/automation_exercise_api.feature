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

  # ===================== SEARCH =====================

  Scenario: Search product using valid keyword
    When I search product with keyword "dress"
    Then the API response code should be 200

  Scenario: Search product without search parameter
    When I search product without keyword
    Then the API response code should be 400

  # ===================== LOGIN =====================

  Scenario: Verify login API behavior with valid credentials
    When I verify login using email "ujitha@gmail.com" and password "uji@123"
    Then the login API should respond correctly

  Scenario: Verify login without email parameter
    When I verify login without email
    Then the API response code should be 400

  Scenario: Verify login with invalid credentials
    When I verify login using email "wrong@test.com" and password "wrongpass"
    Then the API response code should be 404

  # ===================== USER DETAILS =====================

  Scenario: Get user details by email
    When I get user details for email "apiuser@test.com"
    Then the user details API should respond correctly
