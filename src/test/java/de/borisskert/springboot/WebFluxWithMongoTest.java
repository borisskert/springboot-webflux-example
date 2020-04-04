package de.borisskert.springboot;

import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This slice enables Mongo configuration in composition with WebFluxTests.
 * @see <a href="https://www.sudoinit5.com/post/spring-boot-testing-producer/">Testing Reactive Apps with SpringBoot @ sudoinit5</a>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@TypeExcludeFilters(DataMongoTypeExcludeFilter.class)
@AutoConfigureDataMongo
@WebFluxTest
public @interface WebFluxWithMongoTest {
}
