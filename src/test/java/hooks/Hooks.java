package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.DriverFactory;

public class Hooks {

    @Before
    public void setUp() {
        // Initialize WebDriver before each scenario
        DriverFactory.getDriver();
    }

    @After
    public void tearDown() {
        // Quit WebDriver after each scenario
        DriverFactory.quitDriver();
    }
}
