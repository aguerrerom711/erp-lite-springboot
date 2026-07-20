package com.andresgm.erp_lite.infrastructure.persistence.mongo.repositories;

import com.andresgm.erp_lite.infrastructure.persistence.mongo.document.ProductInCatalogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductInCatalogRepository extends MongoRepository<ProductInCatalogDocument,String> {
}
