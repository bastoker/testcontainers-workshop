package nl.jnext.workshop.testcontainers.vakantieplanner.model;

import java.time.LocalDate;

public record Holiday(String description, LocalDate from, LocalDate to) {
}
