package com.andresgm.erp_lite.product;

import com.andresgm.erp_lite.domain.product.ProductImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("ProductImage Domain Test")
public class ProductImageTest {

    @Test
    @DisplayName("Should create a ProductImage from a valid https URL")
    void shouldCreateProductImageFromHttps(){
        ProductImage image = ProductImage.of("https://cdn.example.com/images/product.png");

        assertEquals("https://cdn.example.com/images/product.png", image.imageUrl());
        assertEquals("https://cdn.example.com/images/product.png", image.getFullUrl());
    }

    @Test
    @DisplayName("Should create a ProductImage from a valid http URL")
    void shouldCreateProductImageFromHttp(){
        ProductImage image = ProductImage.of("http://cdn.example.com/images/product.png");

        assertEquals("http://cdn.example.com/images/product.png", image.imageUrl());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when imageUrl is null")
    void shouldThrowWhenImageUrlIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new ProductImage(null));

        assertEquals("Image URL cannot be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when URL is not absolute")
    void shouldThrowWhenUrlIsNotAbsolute(){
        assertThrows(IllegalArgumentException.class, () -> ProductImage.of("/images/product.png"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when scheme is not http or https")
    void shouldThrowWhenSchemeIsInvalid(){
        assertThrows(IllegalArgumentException.class, () -> ProductImage.of("ftp://cdn.example.com/product.png"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when URL is malformed")
    void shouldThrowWhenUrlIsMalformed(){
        assertThrows(IllegalArgumentException.class, () -> ProductImage.of("http://cdn.example.com/my file.png"));
    }

    @Test
    @DisplayName("Should extract the file name from the URL")
    void shouldExtractFileName(){
        ProductImage image = ProductImage.of("https://cdn.example.com/images/product.png");

        assertEquals("product.png", image.getFileName());
    }

    @Test
    @DisplayName("Should return the full URL as file name when it ends with a slash")
    void shouldReturnFullUrlWhenEndsWithSlash(){
        ProductImage image = ProductImage.of("https://cdn.example.com/images/");

        assertEquals("https://cdn.example.com/images/", image.getFileName());
    }
}
