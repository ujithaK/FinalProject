Feature: Products and Cart Operations

  @ui @positive
  Scenario Outline: Add product to cart and login successfully
    Given user is on products page
    When user adds a product to cart
    And user proceeds to checkout page
    And user logs in with "<email>" and "<password>"
    Then user should see the logout button

    Examples:
      | email              | password |
      | ujitha@gmail.com  | uji@123  |

  @ui @negative
  Scenario: Proceed to checkout with empty cart
    Given user is on products page
    When user opens the cart without adding items
    Then cart should be empty
    And user clicks here link if present
