package com.andresgm.erp_lite.order.events;

import com.andresgm.erp_lite.domain.order.OrderId;
import com.andresgm.erp_lite.domain.order.events.OrderCancelled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("OrderCancelled Domain Event Test")
public class OrderCancelledTest {

    @Test
    @DisplayName("Should create a valid OrderCancelled event")
    void shouldCreateValidEvent(){
        OrderId orderId = OrderId.generate();
        Instant now = Instant.now();

        OrderCancelled event = new OrderCancelled(orderId, "Customer request", now);

        assertEquals(orderId, event.orderId());
        assertEquals("Customer request", event.reason());
        assertEquals(now, event.timestamp());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when orderId is null")
    void shouldThrowWhenOrderIdIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new OrderCancelled(null, "reason", Instant.now()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when reason is null")
    void shouldThrowWhenReasonIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new OrderCancelled(OrderId.generate(), null, Instant.now()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when reason is blank")
    void shouldThrowWhenReasonIsBlank(){
        assertThrows(IllegalArgumentException.class, () -> new OrderCancelled(OrderId.generate(), "  ", Instant.now()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when timestamp is null")
    void shouldThrowWhenTimestampIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new OrderCancelled(OrderId.generate(), "reason", null));
    }
}
