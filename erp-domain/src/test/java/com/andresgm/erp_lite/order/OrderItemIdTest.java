package com.andresgm.erp_lite.order;

import com.andresgm.erp_lite.domain.order.OrderItemId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("OrderItemId Domain Test")
public class OrderItemIdTest {

    @Test
    @DisplayName("Should create an OrderItemId from a UUID value")
    void shouldCreateOrderItemIdFromValue(){
        UUID uuid = UUID.randomUUID();

        OrderItemId orderItemId = OrderItemId.of(uuid);

        assertEquals(uuid, orderItemId.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new OrderItemId(null));

        assertEquals("OrderItemId value must not be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should generate distinct OrderItemIds")
    void shouldGenerateDistinctOrderItemIds(){
        OrderItemId first = OrderItemId.generate();
        OrderItemId second = OrderItemId.generate();

        assertNotEquals(first, second);
    }
}
