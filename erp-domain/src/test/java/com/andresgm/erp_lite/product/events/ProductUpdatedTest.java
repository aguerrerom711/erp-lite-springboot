package com.andresgm.erp_lite.product.events;

import com.andresgm.erp_lite.domain.product.ProductId;
import com.andresgm.erp_lite.domain.product.events.ProductUpdated;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ProductUpdated Domain Event Test")
public class ProductUpdatedTest {

    @Test
    @DisplayName("Should create a valid ProductUpdated event")
    void shouldCreateValidEvent(){
        ProductId productId = ProductId.generate();
        Instant now = Instant.now();

        ProductUpdated event = new ProductUpdated(productId, now);

        assertEquals(productId, event.productId());
        assertEquals(now, event.timestamp());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when productId is null")
    void shouldThrowWhenProductIdIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new ProductUpdated(null, Instant.now()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when timestamp is null")
    void shouldThrowWhenTimestampIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new ProductUpdated(ProductId.generate(), null));
    }
}
