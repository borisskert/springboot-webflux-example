package de.borisskert.springboot.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

@SpringBootTest
class TicketTest {

    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    public void shouldSerializeAndDeserializeWithJson() throws Exception {
        Ticket original = Ticket.withId("my id", "my title", "my description");

        String json = jsonMapper.writeValueAsString(original);

        Ticket deserialized = jsonMapper.readValue(json, Ticket.class);

        assertThat(deserialized, is(equalTo(original)));
        assertThat(deserialized, is(not(sameInstance(original))));
    }
}
