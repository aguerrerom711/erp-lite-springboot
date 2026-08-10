package com.andresgm.erp_lite.product;

import com.andresgm.erp_lite.domain.product.CategoryReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CategoryReference Domain Test")
public class CategoryReferenceTest {

    @Test
    @DisplayName("Should create a CategoryReference from a valid categoryId")
    void shouldCreateCategoryReference(){
        CategoryReference categoryReference = CategoryReference.of("CAT-1");

        assertEquals("CAT-1", categoryReference.categoryId());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when categoryId is null")
    void shouldThrowWhenCategoryIdIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new CategoryReference(null));

        assertEquals("Category ID cannot be null", targetException.getMessage());
    }
}
