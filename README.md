**FinalProject – Test Automation Framework**
________________________________________
1. Overview
FinalProject is an enterprise-level end-to-end test automation framework developed using Java, Selenium WebDriver, TestNG, and Maven.
The framework validates real-world e-commerce workflows such as:
•	Product listing
•	Add to cart
•	Checkout
•	Login validation
It follows industry best practices like Page Object Model (POM), external test data management, and reusable utilities.
________________________________________
**2. Technology Stack**
1.	Programming Language: Java
2.	UI Automation Tool: Selenium WebDriver
3.	Test Framework: TestNG
4.	Build Tool: Maven
5.	Design Pattern: Page Object Model (POM)
6.	Test Data Management: JSON
7.	Browser: Google Chrome
8.	IDE: IntelliJ IDEA / Eclipse
________________________________________
**3. Project Structure**
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
________________________________________
**4. Prerequisites**
Before running the project, ensure the following are installed:
1.	Java JDK 17 or above
2.	Maven 3.8 or above
3.	Google Chrome (latest version)
4.	IntelliJ IDEA or Eclipse IDE
5.	Stable internet connection
Verify installations:
java -version
mvn -version
________________________________________
**5. Setup Instructions**
5.1 Clone the Repository
git clone https://github.com/ujithaK/FinalProject.git
cd FinalProject
________________________________________
5.2 Import Project into IDE
IntelliJ IDEA
1.	Open IntelliJ IDEA
2.	Click File → Open
3.	Select the FinalProject folder
4.	Import as a Maven project
Eclipse
1.	File → Import
2.	Select Existing Maven Project
3.	Choose the project directory
________________________________________
5.3 Install Dependencies
Run the following command:
mvn clean install
This will download all required dependencies and build the project.
________________________________________
**6. Test Data Management**
1.	Test data is maintained externally using JSON files.
2.	Location:
3.	src/test/resources/testdata/
4.	Sample JSON structure:
5.	{
6.	  "validUser": {
7.	    "email": "testuser@example.com",
8.	    "password": "Test@123"
9.	  }
10.	}
________________________________________
**7. Running the Tests**
7.1 Run All Tests
mvn test
________________________________________
7.2 Run a Specific Test Class
mvn test -Dtest=ProductsUITest
________________________________________
7.3 Run Tests from IDE
1.	Navigate to src/test/java
2.	Right-click on the test class
3.	Select Run
________________________________________
**8. Test Scenarios Covered**
1.	Launch application
2.	Navigate to products page
3.	Add product to cart
4.	View cart
5.	Proceed to checkout
6.	Login validation
7.	Empty cart validation
________________________________________
**9. Framework Highlights**
1.	Thread-safe WebDriver management using ThreadLocal
2.	Explicit waits implemented via WaitUtils
3.	JavaScriptExecutor used for reliable element interaction
4.	Externalized test data using JSON
5.	Clean separation of test logic and page logic
6.	Easily extendable for API and database testing
________________________________________
**10. Known Warnings**
WARNING: Unable to find CDP implementation matching Chrome version
•	These are Selenium DevTools warnings
•	They do not affect test execution
________________________________________
**11. Future Enhancements**
1.	Integration with Allure / Extent Reports
2.	Parallel execution support
3.	Cross-browser testing
4.	CI/CD integration using GitHub Actions
5.	API and Database validation layer

