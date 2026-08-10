package com.andresgm.erp_lite.domain.order.events;

import com.andresgm.erp_lite.domain.common.DomainEvent;
import com.andresgm.erp_lite.domain.order.OrderId;

import java.time.Instant;

public record OrderConfirmed(
        OrderId orderId,
        Instant timestamp
) implements DomainEvent {

    public OrderConfirmed {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderConfirmed orderId must not be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("OrderConfirmed timestamp must not be null");
        }
    }
}
