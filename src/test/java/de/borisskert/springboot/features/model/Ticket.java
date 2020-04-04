package de.borisskert.springboot.features.model;

import de.borisskert.springboot.webflux.TicketEntity;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Map;
import java.util.Objects;

public class Ticket {
    public static final ParameterizedTypeReference<Ticket> TYPE_REFERENCE = new ParameterizedTypeReference<>() {
    };

    public final String id;
    public final String title;
    public final String description;

    private Ticket(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public TicketEntity toEntity() {
        TicketEntity entity = new TicketEntity();
        entity.setId(id);
        entity.setTitle(title);
        entity.setDescription(description);

        return entity;
    }

    public static Ticket from(Map<String, String> entry) {
        String id = entry.get("ID");
        String title = entry.get("Title");
        String description = entry.get("Description");

        return new Ticket(
                id,
                title,
                description
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) &&
                Objects.equals(title, ticket.title) &&
                Objects.equals(description, ticket.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
