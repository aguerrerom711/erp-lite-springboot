package com.andresgm.erp_lite.product;

import com.andresgm.erp_lite.domain.product.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ProductId Domain Test")
public class ProductIdTest {

    @Test
    @DisplayName("Should create a ProductId from a UUID value")
    void shouldCreateProductIdFromValue(){
        UUID uuid = UUID.randomUUID();

        ProductId productId = ProductId.of(uuid);

        assertEquals(uuid, productId.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new ProductId(null));

        assertEquals("Product ID cannot be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should generate distinct ProductIds")
    void shouldGenerateDistinctProductIds(){
        ProductId first = ProductId.generate();
        ProductId second = ProductId.generate();

        assertNotEquals(first, second);
    }
}
