package com.andresgm.erp_lite.domain.order.events;

import com.andresgm.erp_lite.domain.common.DomainEvent;
import com.andresgm.erp_lite.domain.order.OrderId;

import java.time.Instant;

public record OrderDelivered(
        OrderId orderId,
        Instant timestamp
) implements DomainEvent {

    public OrderDelivered {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderDelivered orderId must not be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("OrderDelivered timestamp must not be null");
        }
    }
}
