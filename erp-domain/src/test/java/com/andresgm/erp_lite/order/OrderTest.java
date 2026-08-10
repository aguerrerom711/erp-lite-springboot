package com.andresgm.erp_lite.order;

import com.andresgm.erp_lite.domain.common.DomainEvent;
import com.andresgm.erp_lite.domain.order.Customer;
import com.andresgm.erp_lite.domain.order.Order;
import com.andresgm.erp_lite.domain.order.OrderItem;
import com.andresgm.erp_lite.domain.order.OrderNumber;
import com.andresgm.erp_lite.domain.order.events.OrderCancelled;
import com.andresgm.erp_lite.domain.order.events.OrderConfirmed;
import com.andresgm.erp_lite.domain.order.events.OrderCreated;
import com.andresgm.erp_lite.domain.order.events.OrderDelivered;
import com.andresgm.erp_lite.domain.order.events.OrderShipped;
import com.andresgm.erp_lite.domain.product.CategoryReference;
import com.andresgm.erp_lite.domain.product.Product;
import com.andresgm.erp_lite.domain.product.ProductName;
import com.andresgm.erp_lite.domain.product.SKU;
import com.andresgm.erp_lite.domain.product.Stock;
import com.andresgm.erp_lite.domain.shared.CustomerId;
import com.andresgm.erp_lite.domain.shared.Money;
import com.andresgm.erp_lite.domain.shared.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Order Domain Test")
public class OrderTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency EUR = Currency.getInstance("EUR");
    private static int skuSequence = 0;

    private Product buildProduct(BigDecimal price, Currency currency, int stock) {
        skuSequence++;
        return Product.create(
                SKU.of("ABC-%03d".formatted(skuSequence)),
                ProductName.of("Product " + skuSequence),
                "description",
                Money.of(price, currency),
                Stock.of(stock),
                CategoryReference.of("CAT-1"),
                null,
                "tester"
        );
    }

    private Customer buildCustomer() {
        return Customer.of(CustomerId.of(1L), "John Doe");
    }

    private Order buildPendingOrder() {
        Product product = buildProduct(BigDecimal.TEN, USD, 10);
        OrderItem item = OrderItem.from(product, Quantity.of(2));
        return Order.create(OrderNumber.of("ORD-2025-001"), buildCustomer(), List.of(item), "tester");
    }

    private <T extends DomainEvent> T findEvent(Order order, Class<T> type) {
        return order.getDomainEvents().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected event of type " + type.getSimpleName()));
    }

    @Test
    @DisplayName("Should create a valid Order and register OrderCreated event")
    void shouldCreateValidOrder(){
        Order order = buildPendingOrder();

        assertEquals("ORD-2025-001", order.getOrderNumber().value());
        assertEquals("John Doe", order.getCustomer().customerName());
        assertTrue(order.getStatus().isPending());
        assertEquals(1, order.getItems().size());
        assertEquals(BigDecimal.valueOf(20), order.getTotalAmount().amount());
        findEvent(order, OrderCreated.class);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when orderNumber is null")
    void shouldThrowWhenOrderNumberIsNull(){
        Product product = buildProduct(BigDecimal.TEN, USD, 10);
        OrderItem item = OrderItem.from(product, Quantity.of(1));

        assertThrows(IllegalArgumentException.class,
                () -> Order.create(null, buildCustomer(), List.of(item), "tester"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when customer is null")
    void shouldThrowWhenCustomerIsNull(){
        Product product = buildProduct(BigDecimal.TEN, USD, 10);
        OrderItem item = OrderItem.from(product, Quantity.of(1));

        assertThrows(IllegalArgumentException.class,
                () -> Order.create(OrderNumber.of("ORD-2025-001"), null, List.of(item), "tester"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when items is null")
    void shouldThrowWhenItemsIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> Order.create(OrderNumber.of("ORD-2025-001"), buildCustomer(), null, "tester"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when items is empty")
    void shouldThrowWhenItemsIsEmpty(){
        assertThrows(IllegalArgumentException.class,
                () -> Order.create(OrderNumber.of("ORD-2025-001"), buildCustomer(), List.of(), "tester"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when items have different currencies")
    void shouldThrowWhenItemsHaveDifferentCurrencies(){
        Product usdProduct = buildProduct(BigDecimal.TEN, USD, 10);
        Product eurProduct = buildProduct(BigDecimal.TEN, EUR, 10);
        OrderItem usdItem = OrderItem.from(usdProduct, Quantity.of(1));
        OrderItem eurItem = OrderItem.from(eurProduct, Quantity.of(1));

        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> Order.create(OrderNumber.of("ORD-2025-001"), buildCustomer(), List.of(usdItem, eurItem), "tester"));

        assertTrue(targetException.getMessage().contains("same currency"));
    }

    @Test
    @DisplayName("Should confirm a pending order and register OrderConfirmed event")
    void shouldConfirmPendingOrder(){
        Order order = buildPendingOrder();

        order.confirm();

        assertTrue(order.getStatus().isConfirmed());
        findEvent(order, OrderConfirmed.class);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when confirming a non-pending order")
    void shouldThrowWhenConfirmingNonPendingOrder(){
        Order order = buildPendingOrder();
        order.confirm();

        assertThrows(IllegalStateException.class, order::confirm);
    }

    @Test
    @DisplayName("Should ship a confirmed order and register OrderShipped event")
    void shouldShipConfirmedOrder(){
        Order order = buildPendingOrder();
        order.confirm();

        order.ship();

        assertTrue(order.getStatus().isShipped());
        findEvent(order, OrderShipped.class);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when shipping a pending order")
    void shouldThrowWhenShippingPendingOrder(){
        Order order = buildPendingOrder();

        assertThrows(IllegalStateException.class, order::ship);
    }

    @Test
    @DisplayName("Should deliver a shipped order and register OrderDelivered event")
    void shouldDeliverShippedOrder(){
        Order order = buildPendingOrder();
        order.confirm();
        order.ship();

        order.deliver();

        assertTrue(order.getStatus().isDelivered());
        findEvent(order, OrderDelivered.class);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when delivering a non-shipped order")
    void shouldThrowWhenDeliveringNonShippedOrder(){
        Order order = buildPendingOrder();

        assertThrows(IllegalStateException.class, order::deliver);
    }

    @Test
    @DisplayName("Should cancel a pending order and register OrderCancelled event")
    void shouldCancelPendingOrder(){
        Order order = buildPendingOrder();

        order.cancel("Customer request");

        assertTrue(order.getStatus().isCancelled());
        OrderCancelled event = findEvent(order, OrderCancelled.class);
        assertEquals("Customer request", event.reason());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when cancelling with a blank reason")
    void shouldThrowWhenCancellingWithBlankReason(){
        Order order = buildPendingOrder();

        assertThrows(IllegalArgumentException.class, () -> order.cancel("  "));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when cancelling with a null reason")
    void shouldThrowWhenCancellingWithNullReason(){
        Order order = buildPendingOrder();

        assertThrows(IllegalArgumentException.class, () -> order.cancel(null));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when cancelling a final state order")
    void shouldThrowWhenCancellingFinalStateOrder(){
        Order order = buildPendingOrder();
        order.confirm();
        order.ship();
        order.deliver();

        assertThrows(IllegalStateException.class, () -> order.cancel("too late"));
    }

    @Test
    @DisplayName("Should add an item to a pending order and recalculate the total")
    void shouldAddItemToPendingOrder(){
        Order order = buildPendingOrder();
        Product extraProduct = buildProduct(BigDecimal.valueOf(5), USD, 10);
        OrderItem extraItem = OrderItem.from(extraProduct, Quantity.of(1));

        order.addItem(extraItem);

        assertEquals(2, order.getItems().size());
        assertEquals(BigDecimal.valueOf(25), order.getTotalAmount().amount());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding a null item")
    void shouldThrowWhenAddingNullItem(){
        Order order = buildPendingOrder();

        assertThrows(IllegalArgumentException.class, () -> order.addItem(null));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when adding an item to a non-pending order")
    void shouldThrowWhenAddingItemToNonPendingOrder(){
        Order order = buildPendingOrder();
        order.confirm();
        Product extraProduct = buildProduct(BigDecimal.valueOf(5), USD, 10);
        OrderItem extraItem = OrderItem.from(extraProduct, Quantity.of(1));

        assertThrows(IllegalStateException.class, () -> order.addItem(extraItem));
    }

    @Test
    @DisplayName("Should remove an item from a pending order and recalculate the total")
    void shouldRemoveItemFromPendingOrder(){
        Order order = buildPendingOrder();
        Product extraProduct = buildProduct(BigDecimal.valueOf(5), USD, 10);
        OrderItem extraItem = OrderItem.from(extraProduct, Quantity.of(1));
        order.addItem(extraItem);

        order.removeItem(extraItem);

        assertEquals(1, order.getItems().size());
        assertEquals(BigDecimal.valueOf(20), order.getTotalAmount().amount());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when removing a null item")
    void shouldThrowWhenRemovingNullItem(){
        Order order = buildPendingOrder();

        assertThrows(IllegalArgumentException.class, () -> order.removeItem(null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when removing an item not in the order")
    void shouldThrowWhenRemovingItemNotInOrder(){
        Order order = buildPendingOrder();
        Product otherProduct = buildProduct(BigDecimal.valueOf(5), USD, 10);
        OrderItem otherItem = OrderItem.from(otherProduct, Quantity.of(1));

        assertThrows(IllegalArgumentException.class, () -> order.removeItem(otherItem));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when removing the last item")
    void shouldThrowWhenRemovingLastItem(){
        Order order = buildPendingOrder();
        OrderItem onlyItem = order.getItems().get(0);

        assertThrows(IllegalStateException.class, () -> order.removeItem(onlyItem));
    }

    @Test
    @DisplayName("Should throw IllegalStateException when removing an item from a non-pending order")
    void shouldThrowWhenRemovingItemFromNonPendingOrder(){
        Order order = buildPendingOrder();
        OrderItem onlyItem = order.getItems().get(0);
        order.confirm();

        assertThrows(IllegalStateException.class, () -> order.removeItem(onlyItem));
    }

    @Test
    @DisplayName("Should return an unmodifiable list of items")
    void shouldReturnUnmodifiableItems(){
        Order order = buildPendingOrder();

        assertThrows(UnsupportedOperationException.class,
                () -> order.getItems().add(order.getItems().get(0)));
    }
}
