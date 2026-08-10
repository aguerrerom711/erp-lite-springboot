package com.andresgm.erp_lite.order;

import com.andresgm.erp_lite.domain.order.Customer;
import com.andresgm.erp_lite.domain.shared.CustomerId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Customer Domain Test")
public class CustomerTest {

    @Test
    @DisplayName("Should create a valid Customer")
    void shouldCreateValidCustomer(){
        CustomerId customerId = CustomerId.of(1L);

        Customer customer = Customer.of(customerId, "John Doe");

        assertEquals(customerId, customer.customerId());
        assertEquals("John Doe", customer.customerName());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when customerId is null")
    void shouldThrowWhenCustomerIdIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new Customer(null, "John Doe"));

        assertEquals("Customer customerId must not be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when customerName is null")
    void shouldThrowWhenCustomerNameIsNull(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(CustomerId.of(1L), null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when customerName is blank")
    void shouldThrowWhenCustomerNameIsBlank(){
        assertThrows(IllegalArgumentException.class, () -> new Customer(CustomerId.of(1L), "  "));
    }
}
