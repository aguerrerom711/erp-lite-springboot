package com.andresgm.erp_lite.common;

import com.andresgm.erp_lite.domain.common.AggregateRoot;
import com.andresgm.erp_lite.domain.common.DomainEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("AggregateRoot Domain Test")
public class AggregateRootTest {

    private record TestEvent(String description) implements DomainEvent {
    }

    private static class TestAggregate extends AggregateRoot<String> {
        TestAggregate(String id) {
            super(id);
        }

        void publish(DomainEvent event) {
            registerEvent(event);
        }
    }

    @Test
    @DisplayName("Should have no domain events by default")
    void shouldHaveNoDomainEventsByDefault(){
        TestAggregate aggregate = new TestAggregate("id-1");

        assertTrue(aggregate.getDomainEvents().isEmpty());
    }

    @Test
    @DisplayName("Should register a domain event")
    void shouldRegisterDomainEvent(){
        TestAggregate aggregate = new TestAggregate("id-1");
        TestEvent event = new TestEvent("created");

        aggregate.publish(event);

        assertEquals(1, aggregate.getDomainEvents().size());
        assertEquals(event, aggregate.getDomainEvents().get(0));
    }

    @Test
    @DisplayName("Should ignore null domain events")
    void shouldIgnoreNullDomainEvent(){
        TestAggregate aggregate = new TestAggregate("id-1");

        aggregate.publish(null);

        assertTrue(aggregate.getDomainEvents().isEmpty());
    }

    @Test
    @DisplayName("Should return an unmodifiable list of domain events")
    void shouldReturnUnmodifiableDomainEvents(){
        TestAggregate aggregate = new TestAggregate("id-1");
        aggregate.publish(new TestEvent("created"));

        assertThrows(UnsupportedOperationException.class,
                () -> aggregate.getDomainEvents().add(new TestEvent("other")));
    }

    @Test
    @DisplayName("Should clear domain events")
    void shouldClearDomainEvents(){
        TestAggregate aggregate = new TestAggregate("id-1");
        aggregate.publish(new TestEvent("created"));

        aggregate.clearDomainEvents();

        assertTrue(aggregate.getDomainEvents().isEmpty());
    }
}
