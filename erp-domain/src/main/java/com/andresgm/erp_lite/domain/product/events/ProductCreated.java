package com.andresgm.erp_lite.domain.product.events;

import com.andresgm.erp_lite.domain.common.DomainEvent;
import com.andresgm.erp_lite.domain.product.ProductId;
import com.andresgm.erp_lite.domain.product.ProductName;
import com.andresgm.erp_lite.domain.product.SKU;
import com.andresgm.erp_lite.domain.shared.Money;

import java.time.Instant;

public record ProductCreated(
        ProductId productId,
        SKU sku,
        ProductName name,
        Money price,
        Instant timestamp
) implements DomainEvent {

    public ProductCreated {
        if (productId == null) {
            throw new IllegalArgumentException("ProductCreated productId must not be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("ProductCreated timestamp must not be null");
        }
    }
}
