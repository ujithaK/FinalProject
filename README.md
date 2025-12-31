FinalProject – Test Automation Framework
1. Overview

FinalProject is an enterprise-level end-to-end test automation framework developed using Java, Selenium WebDriver, TestNG, and Maven.

The framework validates real-world e-commerce workflows such as:

Product listing

Add to cart

Checkout

Login validation

It follows industry best practices like Page Object Model (POM), external test data management, and reusable utilities.

2. Technology Stack

Programming Language: Java

UI Automation Tool: Selenium WebDriver

Test Framework: TestNG

Build Tool: Maven

Design Pattern: Page Object Model (POM)

Test Data Management: JSON

Browser: Google Chrome

IDE: IntelliJ IDEA / Eclipse

3. Project Structure
FinalProject
│
├── src
│   ├── main
│   │   └── java
│   │       ├── driver        # DriverManager, DriverFactory
│   │       ├── pages         # Page Object classes
│   │       └── utils         # Utilities (WaitUtils, JsonUtils)
│   │
│   └── test
│       ├── java
│       │   └── ui            # TestNG UI tests
│       │
│       └── resources
│           └── testdata      # JSON test data
│
├── pom.xml
└── README.md

4. Prerequisites

Before running the project, ensure the following are installed:

Java JDK 17 or above

Maven 3.8 or above

Google Chrome (latest version)

IntelliJ IDEA or Eclipse IDE

Stable internet connection

Verify installations:

java -version
mvn -version

5. Setup Instructions
5.1 Clone the Repository
git clone https://github.com/ujithaK/FinalProject.git
cd FinalProject

5.2 Import Project into IDE

IntelliJ IDEA

Open IntelliJ IDEA

Click File → Open

Select the FinalProject folder

Import as a Maven project

Eclipse

File → Import

Select Existing Maven Project

Choose the project directory

5.3 Install Dependencies

Run the following command:

mvn clean install


This will download all required dependencies and build the project.

6. Test Data Management

Test data is maintained externally using JSON files.

Location:

src/test/resources/testdata/


Sample JSON structure:

{
  "validUser": {
    "email": "testuser@example.com",
    "password": "Test@123"
  }
}

7. Running the Tests
7.1 Run All Tests
mvn test

7.2 Run a Specific Test Class
mvn test -Dtest=ProductsUITest

7.3 Run Tests from IDE

Navigate to src/test/java

Right-click on the test class

Select Run

8. Test Scenarios Covered

Launch application

Navigate to products page

Add product to cart

View cart

Proceed to checkout

Login validation

Empty cart validation

9. Framework Highlights

Thread-safe WebDriver management using ThreadLocal

Explicit waits implemented via WaitUtils

JavaScriptExecutor used for reliable element interaction

Externalized test data using JSON

Clean separation of test logic and page logic

Easily extendable for API and database testing

10. Known Warnings
WARNING: Unable to find CDP implementation matching Chrome version


These are Selenium DevTools warnings

They do not affect test execution

11. Future Enhancements

Integration with Allure / Extent Reports

Parallel execution support

Cross-browser testing

CI/CD integration using GitHub Actions

API and Database validation layer
