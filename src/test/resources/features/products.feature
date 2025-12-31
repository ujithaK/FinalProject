Feature: Product Cart and Checkout Scenarios

  Background:
    Given User launches the AutomationExercise application

  # ================= POSITIVE SCENARIO =================
  Scenario: Add first product to cart and login
    Given User is on the Products page
    When User adds the first product to the cart
    And User clicks on "View Cart"
    And User clicks on "Proceed To Checkout"
    And User clicks on "Register / Login"
    And User logs in with email "ujitha@gmail.com" and password "uji@123"
    Then Logout button should be displayed

  # ================= NEGATIVE SCENARIO =================
  Scenario: Proceed to checkout with empty cart
    Given User is on the Products page
    When User opens cart without adding any product
    Then Empty cart message should be displayed
