package de.borisskert.springboot.webflux;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Ticket {

    private final String id;
    private final String title;
    private final String description;

    private Ticket(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static Ticket fromEntity(TicketEntity ticketEntity) {
        return new Ticket(
                ticketEntity.getId(),
                ticketEntity.getTitle(),
                ticketEntity.getDescription()
        );
    }

    public static Ticket withoutId(String title, String description) {
        requireNonNull(title, "[Ticket] The variable 'title' must not be null");
        requireNonNull(description, "[Ticket] The variable 'description' must not be null");

        return new Ticket(
                null,
                title,
                description
        );
    }

    public static Ticket withId(String id, String title, String description) {
        requireNonNull(id, "[Ticket] The variable 'id' must not be null");
        requireNonNull(title, "[Ticket] The variable 'title' must not be null");
        requireNonNull(description, "[Ticket] The variable 'description' must not be null");

        return new Ticket(
                id,
                title,
                description
        );
    }

    public TicketEntity toEntity() {
        TicketEntity entity = new TicketEntity();

        entity.setId(id);
        entity.setTitle(title);
        entity.setDescription(description);

        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) &&
                title.equals(ticket.title) &&
                description.equals(ticket.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }
}
