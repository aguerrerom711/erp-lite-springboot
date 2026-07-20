package com.andresgm.erp_lite.infrastructure.persistence.mongo.repositories;

import com.andresgm.erp_lite.infrastructure.persistence.mongo.document.CatalogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CatalogRepository extends MongoRepository<CatalogDocument,String> {
}
