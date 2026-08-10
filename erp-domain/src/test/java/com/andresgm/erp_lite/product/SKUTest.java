package com.andresgm.erp_lite.product;

import com.andresgm.erp_lite.domain.product.SKU;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("SKU Domain Test")
public class SKUTest {

    @Test
    @DisplayName("Should create a valid SKU")
    void shouldCreateValidSKU(){
        SKU sku = SKU.of("ABC-123");

        assertEquals("ABC-123", sku.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new SKU(null));

        assertEquals("SKU cannot be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when format is invalid")
    void shouldThrowWhenFormatIsInvalid(){
        assertThrows(IllegalArgumentException.class, () -> SKU.of("abc-123"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when digit count is invalid")
    void shouldThrowWhenDigitCountIsInvalid(){
        assertThrows(IllegalArgumentException.class, () -> SKU.of("ABC-12"));
    }
}
