package com.andresgm.erp_lite.order.events;

import com.andresgm.erp_lite.domain.order.OrderId;
import com.andresgm.erp_lite.domain.order.events.OrderCreated;
import com.andresgm.erp_lite.domain.shared.CustomerId;
import com.andresgm.erp_lite.domain.shared.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("OrderCreated Domain Event Test")
public class OrderCreatedTest {

    private static final Currency USD = Currency.getInstance("USD");

    @Test
    @DisplayName("Should create a valid OrderCreated event")
    void shouldCreateValidEvent(){
        OrderId orderId = OrderId.generate();
        Instant now = Instant.now();

        OrderCreated event = new OrderCreated(orderId, CustomerId.of(1L), "John Doe", Money.of(BigDecimal.TEN, USD), now);

        assertEquals(orderId, event.orderId());
        assertEquals("John Doe", event.customerName());
        assertEquals(now, event.timestamp());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when orderId is null")
    void shouldThrowWhenOrderIdIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> new OrderCreated(null, CustomerId.of(1L), "John Doe", Money.of(BigDecimal.TEN, USD), Instant.now()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when timestamp is null")
    void shouldThrowWhenTimestampIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> new OrderCreated(OrderId.generate(), CustomerId.of(1L), "John Doe", Money.of(BigDecimal.TEN, USD), null));
    }
}
