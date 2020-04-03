package de.borisskert.springboot.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketRepository repository;

    @Autowired
    public TicketController(TicketRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Flux<Ticket> getAll() {
        return repository.findAll().map(Ticket::fromEntity);
    }

    @PostMapping
    public Mono<Ticket> create(@RequestBody Mono<Ticket> ticket) {
        return ticket.map(Ticket::toEntity)
                .flatMap(repository::save)
                .map(Ticket::fromEntity);
    }
}
