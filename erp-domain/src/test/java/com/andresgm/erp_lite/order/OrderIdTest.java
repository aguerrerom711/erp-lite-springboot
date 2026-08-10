package com.andresgm.erp_lite.order;

import com.andresgm.erp_lite.domain.order.OrderId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("OrderId Domain Test")
public class OrderIdTest {

    @Test
    @DisplayName("Should create an OrderId from a UUID value")
    void shouldCreateOrderIdFromValue(){
        UUID uuid = UUID.randomUUID();

        OrderId orderId = OrderId.of(uuid);

        assertEquals(uuid, orderId.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new OrderId(null));

        assertEquals("OrderId value must not be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should generate distinct OrderIds")
    void shouldGenerateDistinctOrderIds(){
        OrderId first = OrderId.generate();
        OrderId second = OrderId.generate();

        assertNotEquals(first, second);
    }
}
