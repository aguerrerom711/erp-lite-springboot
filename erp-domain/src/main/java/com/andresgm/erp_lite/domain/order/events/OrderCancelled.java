package com.andresgm.erp_lite.domain.order.events;

import com.andresgm.erp_lite.domain.common.DomainEvent;
import com.andresgm.erp_lite.domain.order.OrderId;

import java.time.Instant;

public record OrderCancelled(
        OrderId orderId,
        String reason,
        Instant timestamp
) implements DomainEvent {

    public OrderCancelled {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderCancelled orderId must not be null");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("OrderCancelled reason must not be null or blank");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("OrderCancelled timestamp must not be null");
        }
    }
}
