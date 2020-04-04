package de.borisskert.springboot.features.steps;

import de.borisskert.springboot.WebFluxWithMongoTest;
import de.borisskert.springboot.features.FeaturesConfiguration;
import de.borisskert.springboot.webflux.Application;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;


@WebFluxWithMongoTest
@ActiveProfiles("IT")
@ContextConfiguration(
        classes = {
                Application.class,
                FeaturesConfiguration.class
        }
)
public class SpringBootSteps {

    @Autowired
    private ApplicationContext context;

    @Given("our spring application is running")
    public void ourSpringApplicationIsRunning() {
        assertThat(context, is(not(nullValue())));
    }
}
