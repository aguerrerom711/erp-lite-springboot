package com.andresgm.erp_lite.order.events;

import com.andresgm.erp_lite.domain.order.OrderId;
import com.andresgm.erp_lite.domain.order.events.OrderDelivered;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("OrderDelivered Domain Event Test")
public class OrderDeliveredTest {

    @Test
    @DisplayName("Should create a valid OrderDelivered event")
    void shouldCreateValidEvent(){
        OrderId orderId = OrderId.generate();
        Instant now = Instant.now();

        OrderDelivered event = new OrderDelivered(orderId, now);

        assertEquals(orderId, event.orderId());
        assertEquals(now, event.timestamp());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when orderId is null")
    void shouldThrowWhenOrderIdIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new OrderDelivered(null, Instant.now()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when timestamp is null")
    void shouldThrowWhenTimestampIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new OrderDelivered(OrderId.generate(), null));
    }
}
