package com.andresgm.erp_lite.order;

import com.andresgm.erp_lite.domain.order.OrderNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("OrderNumber Domain Test")
public class OrderNumberTest {

    @Test
    @DisplayName("Should create a valid OrderNumber")
    void shouldCreateValidOrderNumber(){
        OrderNumber orderNumber = OrderNumber.of("ORD-2025-001");

        assertEquals("ORD-2025-001", orderNumber.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new OrderNumber(null));

        assertEquals("OrderNumber value must not be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value does not match the pattern")
    void shouldThrowWhenValueDoesNotMatchPattern(){
        assertThrows(IllegalArgumentException.class, () -> OrderNumber.of("INVALID-001"));
    }

    @Test
    @DisplayName("Should generate an OrderNumber matching the expected pattern")
    void shouldGenerateOrderNumberMatchingPattern(){
        OrderNumber orderNumber = OrderNumber.generate();

        assertTrue(orderNumber.value().matches("ORD-\\d{4}-\\d{3}"));
    }
}
