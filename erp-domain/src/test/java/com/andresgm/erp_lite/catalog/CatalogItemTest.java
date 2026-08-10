package com.andresgm.erp_lite.catalog;

import com.andresgm.erp_lite.domain.catalog.CatalogItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("CatalogItem Domain Test")
public class CatalogItemTest {

    private CatalogItem buildValidCatalogItem(String id) {
        return new CatalogItem(
                id,
                "CODE-01",
                "Some value",
                "This product is a test",
                1,
                Map.of("Value display", "Test")
        );
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when code is null")
    void shouldThrowIllegalArgumentExceptionWhenCodeIsBlank(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> {
                    new CatalogItem(
                            UUID.randomUUID().toString(),
                            null,
                            "Some value",
                            "This product is a test",
                            1,
                            Map.of("Value display", "Test")
                    );
                });

        assertEquals("Code cannot be null or empty", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when code is empty")
    void shouldThrowIllegalArgumentExceptionWhenCodeIsEmpty(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> {
                    new CatalogItem(
                            UUID.randomUUID().toString(),
                            "",
                            "Some value",
                            "This product is a test",
                            1,
                            Map.of("Value display", "Test")
                    );
                });

        assertEquals("Code cannot be null or empty", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when id is null")
    void shouldThrowIllegalArgumentExceptionWhenIdIsNull(){
        assertThrows(IllegalArgumentException.class,
                () -> new CatalogItem(
                        null,
                        "CODE-01",
                        "Some value",
                        "This product is a test",
                        1,
                        Map.of("Value display", "Test")
                ));
    }

    @Test
    @DisplayName("Should create CatalogItem with valid data and active by default")
    void shouldCreateCatalogItemWithValidData(){
        String id = UUID.randomUUID().toString();
        CatalogItem catalogItem = new CatalogItem(
                id,
                "CODE-01",
                "Some value",
                "This product is a test",
                1,
                Map.of("Value display", "Test")
        );

        assertEquals(id, catalogItem.getId());
        assertEquals("CODE-01", catalogItem.getCode());
        assertEquals("Some value", catalogItem.getValue());
        assertEquals("This product is a test", catalogItem.getDescription());
        assertEquals(1, catalogItem.getDisplayOrder());
        assertEquals(Map.of("Value display", "Test"), catalogItem.getMetadata());
        assertTrue(catalogItem.isActive());
    }

    @Test
    @DisplayName("Should default metadata to an empty map when null is provided")
    void shouldDefaultToEmptyMapWhenMetadataIsNull(){
        CatalogItem catalogItem = new CatalogItem(
                UUID.randomUUID().toString(),
                "CODE-01",
                "Some value",
                "This product is a test",
                1,
                null
        );

        assertNotNull(catalogItem.getMetadata());
        assertTrue(catalogItem.getMetadata().isEmpty());
    }

    @Test
    @DisplayName("Should return an immutable copy of the metadata map")
    void shouldReturnImmutableMetadataMap(){
        CatalogItem catalogItem = buildValidCatalogItem(UUID.randomUUID().toString());

        assertThrows(UnsupportedOperationException.class,
                () -> catalogItem.getMetadata().put("new-key", "new-value"));
    }

    @Test
    @DisplayName("Should turn off status")
    void shouldTurnOffStatus(){
        CatalogItem catalogItem = buildValidCatalogItem(UUID.randomUUID().toString());

        catalogItem.turnOffStatus();

        assertFalse(catalogItem.isActive());
    }

    @Test
    @DisplayName("Should turn on status after being turned off")
    void shouldTurnOnStatus(){
        CatalogItem catalogItem = buildValidCatalogItem(UUID.randomUUID().toString());
        catalogItem.turnOffStatus();

        catalogItem.turnOnStatus();

        assertTrue(catalogItem.isActive());
    }

    @Test
    @DisplayName("Should return metadata value for an existing key")
    void shouldReturnMetadataValueForExistingKey(){
        CatalogItem catalogItem = buildValidCatalogItem(UUID.randomUUID().toString());

        assertEquals("Test", catalogItem.getMetadata("Value display"));
    }

    @Test
    @DisplayName("Should return null for a non-existing metadata key")
    void shouldReturnNullForNonExistingMetadataKey(){
        CatalogItem catalogItem = buildValidCatalogItem(UUID.randomUUID().toString());

        assertNull(catalogItem.getMetadata("non-existing-key"));
    }

    @Test
    @DisplayName("Should return true when metadata contains the given key")
    void shouldReturnTrueWhenHasMetadata(){
        CatalogItem catalogItem = buildValidCatalogItem(UUID.randomUUID().toString());

        assertTrue(catalogItem.hasMetadata("Value display"));
    }

    @Test
    @DisplayName("Should return false when metadata does not contain the given key")
    void shouldReturnFalseWhenDoesNotHaveMetadata(){
        CatalogItem catalogItem = buildValidCatalogItem(UUID.randomUUID().toString());

        assertFalse(catalogItem.hasMetadata("non-existing-key"));
    }

    @Test
    @DisplayName("Should be equal when ids are equal")
    void shouldBeEqualWhenIdsAreEqual(){
        String id = UUID.randomUUID().toString();
        CatalogItem catalogItemA = buildValidCatalogItem(id);
        CatalogItem catalogItemB = buildValidCatalogItem(id);

        assertEquals(catalogItemA, catalogItemB);
        assertEquals(catalogItemA.hashCode(), catalogItemB.hashCode());
    }

    @Test
    @DisplayName("Should not be equal when ids are different")
    void shouldNotBeEqualWhenIdsAreDifferent(){
        CatalogItem catalogItemA = buildValidCatalogItem(UUID.randomUUID().toString());
        CatalogItem catalogItemB = buildValidCatalogItem(UUID.randomUUID().toString());

        assertNotEquals(catalogItemA, catalogItemB);
    }

    @Test
    @DisplayName("Should not be equal to null or a different type")
    void shouldNotBeEqualToNullOrDifferentType(){
        CatalogItem catalogItem = buildValidCatalogItem(UUID.randomUUID().toString());

        assertNotEquals(catalogItem, null);
        assertNotEquals(catalogItem, "not a catalog item");
    }

    @Test
    @DisplayName("Should include key fields in toString")
    void shouldIncludeKeyFieldsInToString(){
        CatalogItem catalogItem = buildValidCatalogItem(UUID.randomUUID().toString());

        String result = catalogItem.toString();

        assertTrue(result.contains("CODE-01"));
        assertTrue(result.contains("Some value"));
    }
}
