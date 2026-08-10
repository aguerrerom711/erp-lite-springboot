package com.andresgm.erp_lite.product.events;

import com.andresgm.erp_lite.domain.product.ProductId;
import com.andresgm.erp_lite.domain.product.ProductName;
import com.andresgm.erp_lite.domain.product.SKU;
import com.andresgm.erp_lite.domain.product.events.ProductCreated;
import com.andresgm.erp_lite.domain.shared.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ProductCreated Domain Event Test")
public class ProductCreatedTest {

    private static final Currency USD = Currency.getInstance("USD");

    @Test
    @DisplayName("Should create a valid ProductCreated event")
    void shouldCreateValidEvent(){
        ProductId productId = ProductId.generate();
        Instant now = Instant.now();

        ProductCreated event = new ProductCreated(productId, SKU.of("ABC-123"), ProductName.of("Chair"), Money.of(BigDecimal.TEN, USD), now);

        assertEquals(productId, event.productId());
        assertEquals(now, event.timestamp());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when productId is null")
    void shouldThrowWhenProductIdIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> new ProductCreated(null, SKU.of("ABC-123"), ProductName.of("Chair"), Money.of(BigDecimal.TEN, USD), Instant.now()));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when timestamp is null")
    void shouldThrowWhenTimestampIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> new ProductCreated(ProductId.generate(), SKU.of("ABC-123"), ProductName.of("Chair"), Money.of(BigDecimal.TEN, USD), null));
    }
}
