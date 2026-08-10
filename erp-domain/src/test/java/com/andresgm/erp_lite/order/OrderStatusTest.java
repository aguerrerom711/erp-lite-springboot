package com.andresgm.erp_lite.order;

import com.andresgm.erp_lite.domain.order.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("OrderStatus Domain Test")
public class OrderStatusTest {

    @Test
    @DisplayName("Should create every status via its named factory")
    void shouldCreateEveryStatusViaFactory(){
        assertEquals(OrderStatus.PENDING, OrderStatus.pending().value());
        assertEquals(OrderStatus.CONFIRMED, OrderStatus.confirmed().value());
        assertEquals(OrderStatus.SHIPPED, OrderStatus.shipped().value());
        assertEquals(OrderStatus.DELIVERED, OrderStatus.delivered().value());
        assertEquals(OrderStatus.CANCELLED, OrderStatus.cancelled().value());
    }

    @Test
    @DisplayName("Should create a status via of()")
    void shouldCreateStatusViaOf(){
        assertEquals(OrderStatus.PENDING, OrderStatus.of(OrderStatus.PENDING).value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new OrderStatus(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is invalid")
    void shouldThrowWhenValueIsInvalid(){
        assertThrows(IllegalArgumentException.class, () -> OrderStatus.of("UNKNOWN"));
    }

    @Test
    @DisplayName("Should allow PENDING to transition to CONFIRMED or CANCELLED only")
    void shouldValidatePendingTransitions(){
        OrderStatus pending = OrderStatus.pending();

        assertTrue(pending.canTransitionTo(OrderStatus.confirmed()));
        assertTrue(pending.canTransitionTo(OrderStatus.cancelled()));
        assertFalse(pending.canTransitionTo(OrderStatus.shipped()));
        assertFalse(pending.canTransitionTo(OrderStatus.delivered()));
    }

    @Test
    @DisplayName("Should allow CONFIRMED to transition to SHIPPED or CANCELLED only")
    void shouldValidateConfirmedTransitions(){
        OrderStatus confirmed = OrderStatus.confirmed();

        assertTrue(confirmed.canTransitionTo(OrderStatus.shipped()));
        assertTrue(confirmed.canTransitionTo(OrderStatus.cancelled()));
        assertFalse(confirmed.canTransitionTo(OrderStatus.pending()));
        assertFalse(confirmed.canTransitionTo(OrderStatus.delivered()));
    }

    @Test
    @DisplayName("Should allow SHIPPED to transition to DELIVERED only")
    void shouldValidateShippedTransitions(){
        OrderStatus shipped = OrderStatus.shipped();

        assertTrue(shipped.canTransitionTo(OrderStatus.delivered()));
        assertFalse(shipped.canTransitionTo(OrderStatus.cancelled()));
        assertFalse(shipped.canTransitionTo(OrderStatus.pending()));
    }

    @Test
    @DisplayName("Should not allow DELIVERED to transition to any status")
    void shouldNotAllowDeliveredTransitions(){
        OrderStatus delivered = OrderStatus.delivered();

        assertFalse(delivered.canTransitionTo(OrderStatus.pending()));
        assertFalse(delivered.canTransitionTo(OrderStatus.cancelled()));
    }

    @Test
    @DisplayName("Should not allow CANCELLED to transition to any status")
    void shouldNotAllowCancelledTransitions(){
        OrderStatus cancelled = OrderStatus.cancelled();

        assertFalse(cancelled.canTransitionTo(OrderStatus.pending()));
        assertFalse(cancelled.canTransitionTo(OrderStatus.confirmed()));
    }

    @Test
    @DisplayName("Should expose status predicates")
    void shouldExposeStatusPredicates(){
        assertTrue(OrderStatus.pending().isPending());
        assertTrue(OrderStatus.confirmed().isConfirmed());
        assertTrue(OrderStatus.shipped().isShipped());
        assertTrue(OrderStatus.delivered().isDelivered());
        assertTrue(OrderStatus.cancelled().isCancelled());

        assertFalse(OrderStatus.confirmed().isPending());
        assertFalse(OrderStatus.pending().isConfirmed());
        assertFalse(OrderStatus.pending().isShipped());
        assertFalse(OrderStatus.pending().isDelivered());
        assertFalse(OrderStatus.pending().isCancelled());
    }

    @Test
    @DisplayName("Should identify final states")
    void shouldIdentifyFinalStates(){
        assertTrue(OrderStatus.delivered().isFinalState());
        assertTrue(OrderStatus.cancelled().isFinalState());
        assertFalse(OrderStatus.pending().isFinalState());
        assertFalse(OrderStatus.confirmed().isFinalState());
        assertFalse(OrderStatus.shipped().isFinalState());
    }
}
