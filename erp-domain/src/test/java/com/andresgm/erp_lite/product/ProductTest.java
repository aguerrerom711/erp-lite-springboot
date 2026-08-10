package com.andresgm.erp_lite.product;

import com.andresgm.erp_lite.domain.common.DomainEvent;
import com.andresgm.erp_lite.domain.product.CategoryReference;
import com.andresgm.erp_lite.domain.product.Product;
import com.andresgm.erp_lite.domain.product.ProductImage;
import com.andresgm.erp_lite.domain.product.ProductName;
import com.andresgm.erp_lite.domain.product.SKU;
import com.andresgm.erp_lite.domain.product.Stock;
import com.andresgm.erp_lite.domain.product.events.ProductCreated;
import com.andresgm.erp_lite.domain.product.events.ProductDeactivated;
import com.andresgm.erp_lite.domain.product.events.ProductUpdated;
import com.andresgm.erp_lite.domain.product.events.StockChanged;
import com.andresgm.erp_lite.domain.shared.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Product Domain Test")
public class ProductTest {

    private static final Currency USD = Currency.getInstance("USD");

    private Product buildProduct() {
        return Product.create(
                SKU.of("ABC-123"),
                ProductName.of("Chair"),
                "A comfortable chair",
                Money.of(BigDecimal.TEN, USD),
                Stock.of(10),
                CategoryReference.of("CAT-1"),
                ProductImage.of("https://cdn.example.com/chair.png"),
                "tester"
        );
    }

    private <T extends DomainEvent> T findEvent(Product product, Class<T> type) {
        return product.getDomainEvents().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected event of type " + type.getSimpleName()));
    }

    @Test
    @DisplayName("Should create a valid Product and register ProductCreated event")
    void shouldCreateValidProduct(){
        Product product = buildProduct();

        assertEquals("ABC-123", product.getSku().value());
        assertEquals("Chair", product.getName().value());
        assertEquals("A comfortable chair", product.getDescription());
        assertEquals(BigDecimal.TEN, product.getPrice().amount());
        assertEquals(10, product.getStock().value());
        assertEquals("CAT-1", product.getCategory().categoryId());
        assertTrue(product.isActive());
        assertEquals("tester", product.getAuditInfo().createdBy());
        assertEquals(1, product.getDomainEvents().size());
        assertTrue(product.getDomainEvents().get(0) instanceof ProductCreated);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when price is null on creation")
    void shouldThrowWhenPriceIsNullOnCreation(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> Product.create(SKU.of("ABC-123"), ProductName.of("Chair"), "desc", null, Stock.of(1), CategoryReference.of("CAT-1"), null, "tester"));

        assertEquals("Price cannot be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when price is zero on creation")
    void shouldThrowWhenPriceIsZeroOnCreation(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> Product.create(SKU.of("ABC-123"), ProductName.of("Chair"), "desc", Money.of(BigDecimal.ZERO, USD), Stock.of(1), CategoryReference.of("CAT-1"), null, "tester"));

        assertEquals("Price must be greater than 0", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when price is negative on creation")
    void shouldThrowWhenPriceIsNegativeOnCreation(){
        assertThrows(IllegalArgumentException.class,
                () -> Product.create(SKU.of("ABC-123"), ProductName.of("Chair"), "desc", Money.of(BigDecimal.valueOf(-1), USD), Stock.of(1), CategoryReference.of("CAT-1"), null, "tester"));
    }

    @Test
    @DisplayName("Should update product information and register ProductUpdated event")
    void shouldUpdateProduct(){
        Product product = buildProduct();

        product.update(ProductName.of("Big Chair"), "New description", Money.of(BigDecimal.valueOf(20), USD), CategoryReference.of("CAT-2"), null);

        assertEquals("Big Chair", product.getName().value());
        assertEquals("New description", product.getDescription());
        assertEquals(BigDecimal.valueOf(20), product.getPrice().amount());
        assertEquals("CAT-2", product.getCategory().categoryId());
        findEvent(product, ProductUpdated.class);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updating with an invalid price")
    void shouldThrowWhenUpdatingWithInvalidPrice(){
        Product product = buildProduct();

        assertThrows(IllegalArgumentException.class,
                () -> product.update(ProductName.of("Big Chair"), "desc", Money.of(BigDecimal.ZERO, USD), CategoryReference.of("CAT-2"), null));
    }

    @Test
    @DisplayName("Should increment stock and register StockChanged event")
    void shouldIncrementStock(){
        Product product = buildProduct();

        product.incrementStock(5, "restock");

        assertEquals(15, product.getStock().value());
        StockChanged event = findEvent(product, StockChanged.class);
        assertEquals(10, event.oldStock());
        assertEquals(15, event.newStock());
        assertEquals("restock", event.reason());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when incrementing stock with a blank reason")
    void shouldThrowWhenIncrementReasonIsBlank(){
        Product product = buildProduct();

        assertThrows(IllegalArgumentException.class, () -> product.incrementStock(5, "  "));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when incrementing stock with a null reason")
    void shouldThrowWhenIncrementReasonIsNull(){
        Product product = buildProduct();

        assertThrows(IllegalArgumentException.class, () -> product.incrementStock(5, null));
    }

    @Test
    @DisplayName("Should decrement stock and register StockChanged event")
    void shouldDecrementStock(){
        Product product = buildProduct();

        product.decrementStock(4, "sale");

        assertEquals(6, product.getStock().value());
        StockChanged event = findEvent(product, StockChanged.class);
        assertEquals(10, event.oldStock());
        assertEquals(6, event.newStock());
        assertEquals("sale", event.reason());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when decrementing stock with a blank reason")
    void shouldThrowWhenDecrementReasonIsBlank(){
        Product product = buildProduct();

        assertThrows(IllegalArgumentException.class, () -> product.decrementStock(4, ""));
    }

    @Test
    @DisplayName("Should throw when decrementing more stock than available")
    void shouldThrowWhenDecrementingMoreThanAvailable(){
        Product product = buildProduct();

        assertThrows(IllegalArgumentException.class, () -> product.decrementStock(100, "sale"));
    }

    @Test
    @DisplayName("Should change price and register ProductUpdated event")
    void shouldChangePrice(){
        Product product = buildProduct();

        product.changePrice(Money.of(BigDecimal.valueOf(50), USD));

        assertEquals(BigDecimal.valueOf(50), product.getPrice().amount());
        findEvent(product, ProductUpdated.class);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when changing to an invalid price")
    void shouldThrowWhenChangingToInvalidPrice(){
        Product product = buildProduct();

        assertThrows(IllegalArgumentException.class, () -> product.changePrice(Money.of(BigDecimal.ZERO, USD)));
    }

    @Test
    @DisplayName("Should deactivate an active product and register ProductDeactivated event")
    void shouldDeactivateProduct(){
        Product product = buildProduct();

        product.deactivate();

        assertFalse(product.isActive());
        findEvent(product, ProductDeactivated.class);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when deactivating an already inactive product")
    void shouldThrowWhenDeactivatingInactiveProduct(){
        Product product = buildProduct();
        product.deactivate();

        assertThrows(IllegalStateException.class, product::deactivate);
    }

    @Test
    @DisplayName("Should activate an inactive product and register ProductUpdated event")
    void shouldActivateProduct(){
        Product product = buildProduct();
        product.deactivate();
        product.clearDomainEvents();

        product.activate();

        assertTrue(product.isActive());
        findEvent(product, ProductUpdated.class);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when activating an already active product")
    void shouldThrowWhenActivatingActiveProduct(){
        Product product = buildProduct();

        assertThrows(IllegalStateException.class, product::activate);
    }

    @Test
    @DisplayName("Should return true when product is active and has enough stock")
    void shouldHaveAvailableStock(){
        Product product = buildProduct();

        assertTrue(product.hasAvailableStock(10));
    }

    @Test
    @DisplayName("Should return false when product is inactive even with enough stock")
    void shouldNotHaveAvailableStockWhenInactive(){
        Product product = buildProduct();
        product.deactivate();

        assertFalse(product.hasAvailableStock(1));
    }

    @Test
    @DisplayName("Should return false when required stock exceeds available stock")
    void shouldNotHaveAvailableStockWhenInsufficient(){
        Product product = buildProduct();

        assertFalse(product.hasAvailableStock(100));
    }
}
