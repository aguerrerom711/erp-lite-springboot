package com.andresgm.erp_lite.domain.product.events;

import com.andresgm.erp_lite.domain.common.DomainEvent;
import com.andresgm.erp_lite.domain.product.ProductId;

import java.time.Instant;

public record StockChanged(
        ProductId productId,
        Integer oldStock,
        Integer newStock,
        String reason,
        Instant timestamp
) implements DomainEvent {

    public StockChanged {
        if (productId == null) {
            throw new IllegalArgumentException("StockChanged productId must not be null");
        }
        if (oldStock == null || newStock == null) {
            throw new IllegalArgumentException("StockChanged oldStock and newStock must not be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("StockChanged timestamp must not be null");
        }
    }
}
