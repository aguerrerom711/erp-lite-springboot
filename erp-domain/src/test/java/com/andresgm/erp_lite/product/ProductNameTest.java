package com.andresgm.erp_lite.product;

import com.andresgm.erp_lite.domain.product.ProductName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ProductName Domain Test")
public class ProductNameTest {

    @Test
    @DisplayName("Should create a valid ProductName")
    void shouldCreateValidProductName(){
        ProductName productName = ProductName.of("Chair");

        assertEquals("Chair", productName.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new ProductName(null));

        assertEquals("Product name cannot be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is too short")
    void shouldThrowWhenValueIsTooShort(){
        assertThrows(IllegalArgumentException.class, () -> ProductName.of("ab"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value exceeds max length")
    void shouldThrowWhenValueExceedsMaxLength(){
        String tooLong = "a".repeat(201);

        assertThrows(IllegalArgumentException.class, () -> ProductName.of(tooLong));
    }

    @Test
    @DisplayName("Should accept boundary lengths")
    void shouldAcceptBoundaryLengths(){
        ProductName min = ProductName.of("abc");
        ProductName max = ProductName.of("a".repeat(200));

        assertEquals(3, min.value().length());
        assertEquals(200, max.value().length());
    }
}
