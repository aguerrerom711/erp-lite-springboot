package com.andresgm.erp_lite.shared;

import com.andresgm.erp_lite.domain.shared.CustomerId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CustomerId Domain Test")
public class CustomerIdTest {

    @Test
    @DisplayName("Should create a valid CustomerId")
    void shouldCreateValidCustomerId(){
        CustomerId customerId = CustomerId.of(10L);

        assertEquals(10L, customerId.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new CustomerId(null));

        assertEquals("CustomerId value must not be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is zero")
    void shouldThrowWhenValueIsZero(){
        assertThrows(IllegalArgumentException.class, () -> CustomerId.of(0L));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is negative")
    void shouldThrowWhenValueIsNegative(){
        assertThrows(IllegalArgumentException.class, () -> CustomerId.of(-1L));
    }
}
