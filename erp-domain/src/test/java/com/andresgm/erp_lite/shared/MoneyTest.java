package com.andresgm.erp_lite.shared;

import com.andresgm.erp_lite.domain.shared.Money;
import com.andresgm.erp_lite.domain.shared.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Money Domain Test")
public class MoneyTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency EUR = Currency.getInstance("EUR");

    @Test
    @DisplayName("Should create Money from BigDecimal amount")
    void shouldCreateMoneyFromBigDecimal(){
        Money money = Money.of(BigDecimal.TEN, USD);

        assertEquals(BigDecimal.TEN, money.amount());
        assertEquals(USD, money.currency());
    }

    @Test
    @DisplayName("Should create Money from double amount")
    void shouldCreateMoneyFromDouble(){
        Money money = Money.of(10.5, USD);

        assertEquals(BigDecimal.valueOf(10.5), money.amount());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when amount is null")
    void shouldThrowWhenAmountIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new Money(null, USD));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when currency is null")
    void shouldThrowWhenCurrencyIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new Money(BigDecimal.TEN, null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when amount is negative")
    void shouldThrowWhenAmountIsNegative(){
        assertThrows(IllegalArgumentException.class, () -> Money.of(BigDecimal.valueOf(-1), USD));
    }

    @Test
    @DisplayName("Should add two Money of the same currency")
    void shouldAddMoneyOfSameCurrency(){
        Money result = Money.of(BigDecimal.TEN, USD).add(Money.of(BigDecimal.ONE, USD));

        assertEquals(BigDecimal.valueOf(11), result.amount());
    }

    @Test
    @DisplayName("Should subtract two Money of the same currency")
    void shouldSubtractMoneyOfSameCurrency(){
        Money result = Money.of(BigDecimal.TEN, USD).subtract(Money.of(BigDecimal.ONE, USD));

        assertEquals(BigDecimal.valueOf(9), result.amount());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding different currencies")
    void shouldThrowWhenAddingDifferentCurrencies(){
        Money usd = Money.of(BigDecimal.TEN, USD);
        Money eur = Money.of(BigDecimal.ONE, EUR);

        assertThrows(IllegalArgumentException.class, () -> usd.add(eur));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when subtracting different currencies")
    void shouldThrowWhenSubtractingDifferentCurrencies(){
        Money usd = Money.of(BigDecimal.TEN, USD);
        Money eur = Money.of(BigDecimal.ONE, EUR);

        assertThrows(IllegalArgumentException.class, () -> usd.subtract(eur));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when operating against a null Money")
    void shouldThrowWhenOtherMoneyIsNull(){
        Money usd = Money.of(BigDecimal.TEN, USD);

        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> usd.add(null));

        assertEquals("Other Money must not be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should multiply Money by an int multiplier")
    void shouldMultiplyByInt(){
        Money result = Money.of(BigDecimal.TEN, USD).multiply(3);

        assertEquals(BigDecimal.valueOf(30), result.amount());
    }

    @Test
    @DisplayName("Should multiply Money by a Quantity")
    void shouldMultiplyByQuantity(){
        Money result = Money.of(BigDecimal.TEN, USD).multiply(Quantity.of(3));

        assertEquals(BigDecimal.valueOf(30), result.amount());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when multiplying by a null Quantity")
    void shouldThrowWhenQuantityIsNull(){
        Money usd = Money.of(BigDecimal.TEN, USD);

        assertThrows(IllegalArgumentException.class, () -> usd.multiply((Quantity) null));
    }
}
