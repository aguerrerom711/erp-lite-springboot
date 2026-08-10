package com.andresgm.erp_lite.domain.product.events;

import com.andresgm.erp_lite.domain.common.DomainEvent;
import com.andresgm.erp_lite.domain.product.ProductId;

import java.time.Instant;

public record ProductDeactivated(
        ProductId productId,
        Instant timestamp
) implements DomainEvent {

    public ProductDeactivated {
        if (productId == null) {
            throw new IllegalArgumentException("ProductDeactivated productId must not be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("ProductDeactivated timestamp must not be null");
        }
    }
}
