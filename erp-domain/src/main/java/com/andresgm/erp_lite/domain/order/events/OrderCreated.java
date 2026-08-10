package com.andresgm.erp_lite.domain.order.events;

import com.andresgm.erp_lite.domain.common.DomainEvent;
import com.andresgm.erp_lite.domain.order.OrderId;
import com.andresgm.erp_lite.domain.shared.CustomerId;
import com.andresgm.erp_lite.domain.shared.Money;

import java.time.Instant;

public record OrderCreated(
        OrderId orderId,
        CustomerId customerId,
        String customerName,
        Money totalAmount,
        Instant timestamp
) implements DomainEvent {

    public OrderCreated {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderCreated orderId must not be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("OrderCreated timestamp must not be null");
        }
    }
}
