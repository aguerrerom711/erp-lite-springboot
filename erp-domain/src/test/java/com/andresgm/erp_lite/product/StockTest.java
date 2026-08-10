package com.andresgm.erp_lite.product;

import com.andresgm.erp_lite.domain.product.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Stock Domain Test")
public class StockTest {

    @Test
    @DisplayName("Should create Stock from a positive value")
    void shouldCreateStockFromValue(){
        Stock stock = Stock.of(5);

        assertEquals(5, stock.value());
    }

    @Test
    @DisplayName("Should create zero Stock")
    void shouldCreateZeroStock(){
        assertEquals(0, Stock.zero().value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new Stock(null));

        assertEquals("Stock cannot be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is negative")
    void shouldThrowWhenValueIsNegative(){
        assertThrows(IllegalArgumentException.class, () -> Stock.of(-1));
    }

    @Test
    @DisplayName("Should increment stock")
    void shouldIncrementStock(){
        Stock stock = Stock.of(5).increment(3);

        assertEquals(8, stock.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when increment quantity is negative")
    void shouldThrowWhenIncrementIsNegative(){
        assertThrows(IllegalArgumentException.class, () -> Stock.of(5).increment(-1));
    }

    @Test
    @DisplayName("Should decrement stock")
    void shouldDecrementStock(){
        Stock stock = Stock.of(5).decrement(3);

        assertEquals(2, stock.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when decrement quantity is negative")
    void shouldThrowWhenDecrementIsNegative(){
        assertThrows(IllegalArgumentException.class, () -> Stock.of(5).decrement(-1));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when decrement would go below zero")
    void shouldThrowWhenDecrementBelowZero(){
        assertThrows(IllegalArgumentException.class, () -> Stock.of(5).decrement(6));
    }

    @Test
    @DisplayName("Should return true when required stock is available")
    void shouldReturnTrueWhenAvailable(){
        assertTrue(Stock.of(5).hasAvailable(5));
    }

    @Test
    @DisplayName("Should return false when required stock is not available")
    void shouldReturnFalseWhenNotAvailable(){
        assertFalse(Stock.of(5).hasAvailable(6));
    }
}
