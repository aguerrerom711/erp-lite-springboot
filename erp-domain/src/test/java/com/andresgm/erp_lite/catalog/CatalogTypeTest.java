package com.andresgm.erp_lite.catalog;

import com.andresgm.erp_lite.domain.catalog.CatalogType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("CatalogType Domain Test")
public class CatalogTypeTest {

    @Test
    @DisplayName("Should expose code and displayName")
    void shouldExposeCodeAndDisplayName(){
        assertEquals("PRODUCT_CATEGORIES", CatalogType.PRODUCT_CATEGORIES.getCode());
        assertEquals("Product Categories", CatalogType.PRODUCT_CATEGORIES.getDisplayName());
    }

    @Test
    @DisplayName("Should convert every enum value from its code")
    void shouldConvertFromCodeForAllValues(){
        for (CatalogType type : CatalogType.values()) {
            assertEquals(type, CatalogType.fromCode(type.getCode()));
        }
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when code is null")
    void shouldThrowWhenCodeIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> CatalogType.fromCode(null));

        assertEquals("Catalog type code cannot be null or empty", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when code is blank")
    void shouldThrowWhenCodeIsBlank(){
        assertThrows(IllegalArgumentException.class, () -> CatalogType.fromCode("   "));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when code is invalid")
    void shouldThrowWhenCodeIsInvalid(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> CatalogType.fromCode("NOT_A_TYPE"));

        assertTrue(targetException.getMessage().contains("Invalid catalog type code"));
    }

    @Test
    @DisplayName("Should return true when code is valid")
    void shouldReturnTrueWhenCodeIsValid(){
        assertTrue(CatalogType.isValid("CURRENCIES"));
    }

    @Test
    @DisplayName("Should return false when code is invalid")
    void shouldReturnFalseWhenCodeIsInvalid(){
        assertFalse(CatalogType.isValid("NOT_A_TYPE"));
    }

    @Test
    @DisplayName("Should return false when code is null")
    void shouldReturnFalseWhenCodeIsNull(){
        assertFalse(CatalogType.isValid(null));
    }
}
