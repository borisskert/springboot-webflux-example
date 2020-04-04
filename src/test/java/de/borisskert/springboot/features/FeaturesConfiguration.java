package de.borisskert.springboot.features;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class which enables correct component scanning on test packages
 */
@Configuration
@ComponentScan(basePackages = "de.borisskert.springboot.features.world")
public class FeaturesConfiguration {
}
