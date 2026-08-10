package com.andresgm.erp_lite.customer;

import com.andresgm.erp_lite.domain.customer.CustomerInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CustomerInfo Domain Test")
public class CustomerInfoTest {

    @Test
    @DisplayName("Should create a valid CustomerInfo")
    void shouldCreateValidCustomerInfo(){
        CustomerInfo customerInfo = new CustomerInfo(1L, "John Doe", "john@example.com", "123", "Street 1", "City", "00000", "Company");

        assertEquals(1L, customerInfo.id());
        assertEquals("John Doe", customerInfo.name());
        assertEquals("john@example.com", customerInfo.email());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when id is null")
    void shouldThrowWhenIdIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new CustomerInfo(null, "John Doe", "john@example.com", "123", "Street 1", "City", "00000", "Company"));

        assertEquals("id is not present", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when name is null")
    void shouldThrowWhenNameIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new CustomerInfo(1L, null, "john@example.com", "123", "Street 1", "City", "00000", "Company"));

        assertEquals("name is not present", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when name is blank")
    void shouldThrowWhenNameIsBlank(){
        assertThrows(IllegalArgumentException.class,
                () -> new CustomerInfo(1L, "   ", "john@example.com", "123", "Street 1", "City", "00000", "Company"));
    }
}
