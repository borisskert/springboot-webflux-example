package de.borisskert.springboot.features.steps;

import de.borisskert.springboot.features.model.Ticket;
import de.borisskert.springboot.features.world.CucumberHttpClient;
import de.borisskert.springboot.webflux.TicketEntity;
import de.borisskert.springboot.webflux.TicketRepository;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketSteps {

    @Autowired
    private CucumberHttpClient httpClient;

    @Autowired
    private TicketRepository repository;

    @DataTableType
    public Ticket readFromDataTable(Map<String, String> entry) {
        return Ticket.from(entry);
    }

    @When("^I ask for all tickets$")
    public void iAskForAllTickets() {
        httpClient.get("/api/v1/tickets");
    }

    @Then("^I should get no tickets$")
    public void iShouldGetNoTickets() {
        httpClient.verifyLatestStatusIsEqualTo(HttpStatus.OK);
        httpClient.verifyLatestBodyIsEmptyArray();
    }

    @Given("^mongo contains the following tickets$")
    public void mongoContainsTheFollowingTickets(List<Ticket> dataTable) {
        List<TicketEntity> entities = dataTable.stream()
                .map(Ticket::toEntity)
                .collect(Collectors.toUnmodifiableList());

        repository.saveAll(entities).blockLast();
    }

    @Then("^I should get following tickets$")
    public void iShouldGetFollowingTickets(List<Ticket> dataTable) {
        httpClient.verifyLatestStatusIsEqualTo(HttpStatus.OK);
        httpClient.verifyLatestBodyContainsInAnyOrder(dataTable, Ticket.TYPE_REFERENCE);
    }
}
