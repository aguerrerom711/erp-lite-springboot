package com.andresgm.erp_lite.catalog;

import com.andresgm.erp_lite.domain.catalog.Catalog;
import com.andresgm.erp_lite.domain.catalog.CatalogItem;
import com.andresgm.erp_lite.domain.catalog.CatalogType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Catalog Domain Test")
public class CatalogTest {

    private CatalogItem buildItem(String code) {
        return new CatalogItem(UUID.randomUUID().toString(), code, "Value " + code, "Description " + code, 1, null);
    }

    private Catalog buildCatalog(String id, List<CatalogItem> items) {
        return new Catalog(id, CatalogType.PRODUCT_CATEGORIES, "Categories", "Product categories catalog", items, true);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when id is null")
    void shouldThrowWhenIdIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> buildCatalog(null, List.of(buildItem("A"))));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when catalogType is null")
    void shouldThrowWhenCatalogTypeIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new Catalog(UUID.randomUUID().toString(), null, "Categories", "desc", List.of(), true));

        assertEquals("CatalogType cannot be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when name is null")
    void shouldThrowWhenNameIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new Catalog(UUID.randomUUID().toString(), CatalogType.PRODUCT_CATEGORIES, null, "desc", List.of(), true));

        assertEquals("name cannot be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should create Catalog with valid data")
    void shouldCreateCatalogWithValidData(){
        String id = UUID.randomUUID().toString();
        List<CatalogItem> items = List.of(buildItem("A"));

        Catalog catalog = buildCatalog(id, items);

        assertEquals(id, catalog.getId());
        assertEquals(CatalogType.PRODUCT_CATEGORIES, catalog.getCatalogType());
        assertEquals("Categories", catalog.getName());
        assertEquals("Product categories catalog", catalog.getDescription());
        assertEquals(items, catalog.getItems());
        assertTrue(catalog.isActive());
    }

    @Test
    @DisplayName("Should find an item by its code")
    void shouldFindItemByCode(){
        CatalogItem item = buildItem("A");
        Catalog catalog = buildCatalog(UUID.randomUUID().toString(), List.of(item));

        Optional<CatalogItem> found = catalog.findItemByCode("A");

        assertTrue(found.isPresent());
        assertEquals(item, found.get());
    }

    @Test
    @DisplayName("Should return empty Optional when item code is not found")
    void shouldReturnEmptyWhenItemCodeNotFound(){
        Catalog catalog = buildCatalog(UUID.randomUUID().toString(), List.of(buildItem("A")));

        assertTrue(catalog.findItemByCode("Z").isEmpty());
    }

    @Test
    @DisplayName("Should return true when catalog contains an item")
    void shouldContainItem(){
        Catalog catalog = buildCatalog(UUID.randomUUID().toString(), List.of(buildItem("A")));

        assertTrue(catalog.containsItem("A"));
    }

    @Test
    @DisplayName("Should return false when catalog does not contain an item")
    void shouldNotContainItem(){
        Catalog catalog = buildCatalog(UUID.randomUUID().toString(), List.of(buildItem("A")));

        assertFalse(catalog.containsItem("Z"));
    }

    @Test
    @DisplayName("Should return only active items")
    void shouldReturnOnlyActiveItems(){
        CatalogItem activeItem = buildItem("A");
        CatalogItem inactiveItem = buildItem("B");
        inactiveItem.turnOffStatus();
        Catalog catalog = buildCatalog(UUID.randomUUID().toString(), List.of(activeItem, inactiveItem));

        List<CatalogItem> activeItems = catalog.findItemsActive();

        assertEquals(List.of(activeItem), activeItems);
    }

    @Test
    @DisplayName("Should return an unmodifiable list with all items")
    void shouldReturnAllItemsUnmodifiable(){
        Catalog catalog = buildCatalog(UUID.randomUUID().toString(), List.of(buildItem("A")));

        List<CatalogItem> all = catalog.findAll();

        assertEquals(1, all.size());
        assertThrows(UnsupportedOperationException.class, () -> all.add(buildItem("B")));
    }

    @Test
    @DisplayName("Should be equal when id and fields match")
    void shouldBeEqualWhenFieldsMatch(){
        String id = UUID.randomUUID().toString();
        List<CatalogItem> items = List.of(buildItem("A"));
        Catalog catalogA = buildCatalog(id, items);
        Catalog catalogB = buildCatalog(id, items);

        assertEquals(catalogA, catalogB);
        assertEquals(catalogA.hashCode(), catalogB.hashCode());
    }

    @Test
    @DisplayName("Should not be equal when ids differ")
    void shouldNotBeEqualWhenIdsDiffer(){
        List<CatalogItem> items = List.of(buildItem("A"));
        Catalog catalogA = buildCatalog(UUID.randomUUID().toString(), items);
        Catalog catalogB = buildCatalog(UUID.randomUUID().toString(), items);

        assertNotEquals(catalogA, catalogB);
    }

    @Test
    @DisplayName("Should include key fields in toString")
    void shouldIncludeKeyFieldsInToString(){
        Catalog catalog = buildCatalog(UUID.randomUUID().toString(), List.of(buildItem("A")));

        assertTrue(catalog.toString().contains("Categories"));
    }
}
