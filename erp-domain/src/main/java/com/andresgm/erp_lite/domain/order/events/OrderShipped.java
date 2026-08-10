package com.andresgm.erp_lite.domain.order.events;

import com.andresgm.erp_lite.domain.common.DomainEvent;
import com.andresgm.erp_lite.domain.order.OrderId;

import java.time.Instant;

public record OrderShipped(
        OrderId orderId,
        Instant timestamp
) implements DomainEvent {

    public OrderShipped {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderShipped orderId must not be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("OrderShipped timestamp must not be null");
        }
    }
}
