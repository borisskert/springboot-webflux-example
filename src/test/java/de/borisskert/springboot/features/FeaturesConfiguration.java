package de.borisskert.springboot.features;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "de.borisskert.springboot.features.world")
public class FeaturesConfiguration {
}
