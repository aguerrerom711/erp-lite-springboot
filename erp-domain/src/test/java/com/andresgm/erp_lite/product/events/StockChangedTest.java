package com.andresgm.erp_lite.product.events;

import com.andresgm.erp_lite.domain.product.ProductId;
import com.andresgm.erp_lite.domain.product.events.StockChanged;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("StockChanged Domain Event Test")
public class StockChangedTest {

    @Test
    @DisplayName("Should create a valid StockChanged event")
    void shouldCreateValidEvent(){
        ProductId productId = ProductId.generate();
        Instant now = Instant.now();

        StockChanged event = new StockChanged(productId, 5, 10, "restock", now);

        assertEquals(productId, event.productId());
        assertEquals(5, event.oldStock());
        assertEquals(10, event.newStock());
        assertEquals("restock", event.reason());
        assertEquals(now, event.timestamp());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when productId is null")
    void shouldThrowWhenProductIdIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new StockChanged(null, 5, 10, "restock", Instant.now()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when oldStock is null")
    void shouldThrowWhenOldStockIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> new StockChanged(ProductId.generate(), null, 10, "restock", Instant.now()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when newStock is null")
    void shouldThrowWhenNewStockIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> new StockChanged(ProductId.generate(), 5, null, "restock", Instant.now()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when timestamp is null")
    void shouldThrowWhenTimestampIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> new StockChanged(ProductId.generate(), 5, 10, "restock", null));
    }
}
