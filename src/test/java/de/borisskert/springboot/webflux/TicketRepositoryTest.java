package de.borisskert.springboot.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
class TicketRepositoryTest {

    @Autowired
    private TicketRepository repository;

    @Test
    public void shouldPersistOneNewTicket() throws Exception {
        TicketEntity entity = new TicketEntity();
        entity.setTitle("my title");
        entity.setDescription("my description");

        TicketEntity savedTicket = repository.save(entity).block();

        assertThat(savedTicket.getTitle(), is(equalTo("my title")));
        assertThat(savedTicket.getDescription(), is(equalTo("my description")));
        assertThat(savedTicket.getId(), is(notNullValue()));
    }
}
