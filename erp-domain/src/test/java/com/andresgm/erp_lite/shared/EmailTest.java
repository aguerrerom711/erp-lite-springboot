package com.andresgm.erp_lite.shared;

import com.andresgm.erp_lite.domain.shared.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Email Domain Test")
public class EmailTest {

    @Test
    @DisplayName("Should create a valid Email")
    void shouldCreateValidEmail(){
        Email email = Email.of("john.doe@example.com");

        assertEquals("john.doe@example.com", email.value());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is null")
    void shouldThrowWhenValueIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new Email(null));

        assertEquals("Email value must not be null or blank", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value is blank")
    void shouldThrowWhenValueIsBlank(){
        assertThrows(IllegalArgumentException.class, () -> Email.of("   "));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when value has invalid format")
    void shouldThrowWhenValueIsInvalidFormat(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> Email.of("not-an-email"));

        assertEquals("Email value must be a valid email address: not-an-email", targetException.getMessage());
    }
}
