package de.borisskert.springboot.features.world;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.ExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @see <a href="https://github.com/borisskert/springboot-cucumber-wiremock-example"></a>
 */
@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class CucumberHttpClient {
    /* *****************************************************************************************************************
     * Readonly fields
     **************************************************************************************************************** */

    private final WebTestClient webClient;
    private final MultiValueMap<String, String> headers = new HttpHeaders();

    /* *****************************************************************************************************************
     * Mutable fields
     **************************************************************************************************************** */

    private WebTestClient.ResponseSpec lastResponse;

    /* *****************************************************************************************************************
     * Constructor
     **************************************************************************************************************** */

    @Autowired
    public CucumberHttpClient(WebTestClient webClient, ObjectMapper mapper) {
        this.webClient = webClient;
    }

    /* *****************************************************************************************************************
     * Methods to request
     **************************************************************************************************************** */

    public void addHeader(String key, String value) {
        headers.add(key, value);
    }

    public void get(String url, Object... urlVariables) {
        lastResponse = webClient.get()
                .uri(url, urlVariables)
                .headers(setupHeaders())
                .exchange();
    }

    public void post(String url, Object body, Object... urlVariables) {
        lastResponse = webClient.post()
                .uri(url, urlVariables)
                .bodyValue(body)
                .headers(setupHeaders())
                .exchange();
    }

    public void postEmptyBody(String url, Object... urlVariables) {
        lastResponse = webClient.post()
                .uri(url, urlVariables)
                .headers(setupHeaders())
                .exchange();
    }

    public void put(String url, Object body, Object... urlVariables) {
        lastResponse = webClient.put()
                .uri(url, urlVariables)
                .bodyValue(body)
                .headers(setupHeaders())
                .exchange();
    }

    /* *****************************************************************************************************************
     * Methods to verify response
     **************************************************************************************************************** */

    public void verifyLatestStatusIsEqualTo(HttpStatus expectedStatus) {
        getLastResponse()
                .map(WebTestClient.ResponseSpec::expectStatus)
                .ifPresentOrElse(
                        status -> status.isEqualTo(expectedStatus),
                        () -> {
                            throw new AssertionFailedError("Got no response");
                        }
                );
    }

    public void verifyLatestStatusIs2xxSuccessful() {
        getLastResponse()
                .map(WebTestClient.ResponseSpec::expectStatus)
                .ifPresentOrElse(
                        StatusAssertions::is2xxSuccessful,
                        () -> {
                            throw new AssertionFailedError("Got no response");
                        }
                );
    }

    public void verifyLatestBodyIsEqualTo(String expectedBody) {
        getLastResponse()
                .map(response -> response.returnResult(String.class))
                .map(FluxExchangeResult::getResponseBody)
                .map(Flux::blockLast)
                .ifPresentOrElse(
                        body -> assertThat(body, is(equalTo(expectedBody))),
                        () -> {
                            throw new AssertionFailedError("Got no response");
                        }
                );
    }

    public <T> void verifyLatestBodyIsEqualTo(T expectedBody, Class<T> type) {
        getLastResponse()
                .map(response -> response.returnResult(type))
                .map(FluxExchangeResult::getResponseBody)
                .map(Flux::blockLast)
                .ifPresentOrElse(
                        body -> assertThat(body, is(equalTo(expectedBody))),
                        () -> {
                            throw new AssertionFailedError("Got no response");
                        }
                );
    }

    public <T> void verifyLatestBodyIsEqualTo(List<T> expectedBody, ParameterizedTypeReference<T> type) {
        getLastResponse()
                .map(response -> response.expectBodyList(type))
                .ifPresentOrElse(
                        body -> body.isEqualTo(expectedBody),
                        () -> {
                            throw new AssertionFailedError("Got no response");
                        }
                );
    }

    public <T> void verifyLatestBodyContainsInAnyOrder(List<T> expectedBody, ParameterizedTypeReference<T> type) {
        getLastResponse()
                .map(response -> response.expectBodyList(type))
                .ifPresentOrElse(
                        body -> body.contains((T[]) expectedBody.toArray()),
                        () -> {
                            throw new AssertionFailedError("Got no response");
                        }
                );
    }

    public void verifyLatestBodyIsEmpty() {
        getLastResponse()
                .map(WebTestClient.ResponseSpec::expectBody)
                .ifPresentOrElse(
                        WebTestClient.BodyContentSpec::isEmpty,
                        () -> {
                            throw new AssertionFailedError("Got no response");
                        }
                );
    }

    public void verifyLatestBodyIsEmptyArray() {
        getLastResponse()
                .map(response -> response.expectBodyList(Object.class))
                .ifPresentOrElse(
                        body -> {
                            body.hasSize(0);
                        },
                        () -> {
                            throw new AssertionFailedError("Got no response");
                        }
                );
    }

    public String getLatestResponseHeaderParam(String key) {
        return getLastResponse()
                .map(response -> response.returnResult(String.class))
                .map(ExchangeResult::getResponseHeaders)
                .map(headers -> headers.getFirst(key))
                .orElseThrow(() -> {
                    throw new AssertionFailedError("Got no response");
                });
    }

    /* *****************************************************************************************************************
     * Private methods
     **************************************************************************************************************** */

    private Optional<WebTestClient.ResponseSpec> getLastResponse() {
        return Optional.ofNullable(lastResponse);
    }

    private Consumer<HttpHeaders> setupHeaders() {
        return requestHeaders -> {
            requestHeaders.addAll(headers);
            headers.clear();
        };
    }
}
