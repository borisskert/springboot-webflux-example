package de.borisskert.springboot.webflux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@WebFluxTest
class TicketControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private TicketRepository repository;

    @Nested
    class EmptyRepository {
        @BeforeEach
        public void setup() throws Exception {
            reset(repository);
            when(repository.findAll()).thenReturn(Flux.empty());
        }

        @Test
        public void shouldReturnEmptyList() throws Exception {
            WebTestClient.ResponseSpec response = client.get()
                    .uri("/api/v1/tickets")
                    .exchange();

            response.expectStatus().is2xxSuccessful()
                    .expectBodyList(Ticket.class).isEqualTo(List.of());
        }
    }

    @Nested
    class WithOneTicket {
        @BeforeEach
        public void setup() throws Exception {
            reset(repository);

            TicketEntity entity = new TicketEntity();
            entity.setId("my id");
            entity.setTitle("my title");
            entity.setDescription("my description");

            when(repository.findAll()).thenReturn(Flux.just(entity));
        }

        @Test
        public void shouldReturnEmptyList() throws Exception {
            WebTestClient.ResponseSpec response = client.get()
                    .uri("/api/v1/tickets")
                    .exchange();

            Ticket expectedTicket = Ticket.withId("my id", "my title", "my description");

            response.expectStatus().is2xxSuccessful()
                    .expectBodyList(Ticket.class).isEqualTo(List.of(expectedTicket));
        }
    }

    @Nested
    class SaveOneTicket {
        @BeforeEach
        public void setup() throws Exception {
            reset(repository);

            TicketEntity savedEntity = new TicketEntity();
            savedEntity.setId("saved id");
            savedEntity.setTitle("saved title");
            savedEntity.setDescription("saved description");

            when(repository.save(any())).thenReturn(Mono.just(savedEntity));
        }

        @Test
        public void shouldPersistOneTicket() throws Exception {
            Ticket ticketToCreate = Ticket.withoutId("my title", "my description");
            WebTestClient.ResponseSpec response = client.post()
                    .uri("/api/v1/tickets")
                    .bodyValue(ticketToCreate)
                    .exchange();

            response.expectStatus().is2xxSuccessful()
                    .expectBody()
                    .jsonPath("id").isEqualTo("saved id")
                    .jsonPath("title").isEqualTo("saved title")
                    .jsonPath("description").isEqualTo("saved description");
        }
    }
}
