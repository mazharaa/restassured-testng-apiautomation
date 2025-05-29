package cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources",
    glue = "cucumber.definitions",
    plugin = {"pretty", "json:target/cucumber.json"},
    monochrome = true
)
public class CucumberRunner extends AbstractTestNGCucumberTests {
}
