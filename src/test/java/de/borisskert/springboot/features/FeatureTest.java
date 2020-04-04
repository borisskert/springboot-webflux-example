package de.borisskert.springboot.features;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Cucumber entry point
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = "classpath:features",
        plugin = {"pretty", "html:target/cucumber"}
)
public class FeatureTest {
}
