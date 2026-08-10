package com.andresgm.erp_lite.shared;

import com.andresgm.erp_lite.domain.shared.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Quantity Domain Test")
public class QuantityTest {

    @Test
    @DisplayName("Should create a valid Quantity")
    void shouldCreateValidQuantity(){
        Quantity quantity = Quantity.of(5);

        assertEquals(5, quantity.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new Quantity(null));

        assertEquals("Quantity value must not be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is zero")
    void shouldThrowWhenValueIsZero(){
        assertThrows(IllegalArgumentException.class, () -> Quantity.of(0));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is negative")
    void shouldThrowWhenValueIsNegative(){
        assertThrows(IllegalArgumentException.class, () -> Quantity.of(-1));
    }
}
