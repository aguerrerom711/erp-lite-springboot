package com.andresgm.erp_lite.order;

import com.andresgm.erp_lite.domain.order.OrderItem;
import com.andresgm.erp_lite.domain.product.CategoryReference;
import com.andresgm.erp_lite.domain.product.Product;
import com.andresgm.erp_lite.domain.product.ProductName;
import com.andresgm.erp_lite.domain.product.SKU;
import com.andresgm.erp_lite.domain.product.Stock;
import com.andresgm.erp_lite.domain.shared.Money;
import com.andresgm.erp_lite.domain.shared.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("OrderItem Domain Test")
public class OrderItemTest {

    private static final Currency USD = Currency.getInstance("USD");

    private Product buildProduct(int stock) {
        return Product.create(
                SKU.of("ABC-123"),
                ProductName.of("Chair"),
                "A comfortable chair",
                Money.of(BigDecimal.TEN, USD),
                Stock.of(stock),
                CategoryReference.of("CAT-1"),
                null,
                "tester"
        );
    }

    @Test
    @DisplayName("Should create an OrderItem from a product and quantity")
    void shouldCreateOrderItemFromProduct(){
        Product product = buildProduct(10);

        OrderItem item = OrderItem.from(product, Quantity.of(3));

        assertEquals(product.getId(), item.getProductReference());
        assertEquals("Chair", item.getProductName());
        assertEquals(3, item.getQuantity().value());
        assertEquals(BigDecimal.TEN, item.getUnitPrice().amount());
        assertEquals(BigDecimal.valueOf(30), item.getSubtotal().amount());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when product is null")
    void shouldThrowWhenProductIsNull(){
        assertThrows(IllegalArgumentException.class, () -> OrderItem.from(null, Quantity.of(1)));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when quantity is null")
    void shouldThrowWhenQuantityIsNull(){
        Product product = buildProduct(10);

        assertThrows(IllegalArgumentException.class, () -> OrderItem.from(product, null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when product is inactive")
    void shouldThrowWhenProductIsInactive(){
        Product product = buildProduct(10);
        product.deactivate();

        assertThrows(IllegalArgumentException.class, () -> OrderItem.from(product, Quantity.of(1)));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when stock is insufficient")
    void shouldThrowWhenStockIsInsufficient(){
        Product product = buildProduct(2);

        assertThrows(IllegalArgumentException.class, () -> OrderItem.from(product, Quantity.of(5)));
    }

    @Test
    @DisplayName("Should calculate the subtotal as quantity times unit price")
    void shouldCalculateSubtotal(){
        Product product = buildProduct(10);
        OrderItem item = OrderItem.from(product, Quantity.of(4));

        assertEquals(BigDecimal.valueOf(40), item.calculateSubtotal().amount());
    }
}
