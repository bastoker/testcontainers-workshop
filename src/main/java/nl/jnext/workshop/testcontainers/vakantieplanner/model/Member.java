package nl.jnext.workshop.testcontainers.vakantieplanner.model;

import java.util.List;

public record Member(String name, List<Holiday> holidays) {
}
