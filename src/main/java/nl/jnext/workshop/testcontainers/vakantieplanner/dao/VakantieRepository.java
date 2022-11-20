package nl.jnext.workshop.testcontainers.vakantieplanner.dao;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VakantieRepository {

    private final DSLContext create;

    @Autowired
    VakantieRepository(DSLContext dslContext) {
        this.create = dslContext;
    }
}