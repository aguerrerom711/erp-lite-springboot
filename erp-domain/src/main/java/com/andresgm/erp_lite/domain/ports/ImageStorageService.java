package com.andresgm.erp_lite.domain.ports;

import com.andresgm.erp_lite.domain.product.ProductImage;

public interface ImageStorageService {
    ProductImage upload(String imageName, byte[] imageData);
    void delete(ProductImage productImage);
    byte[] download(ProductImage productImage);
}
