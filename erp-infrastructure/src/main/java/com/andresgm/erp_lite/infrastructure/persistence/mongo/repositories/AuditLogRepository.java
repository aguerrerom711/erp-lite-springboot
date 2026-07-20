package com.andresgm.erp_lite.infrastructure.persistence.mongo.repositories;

import com.andresgm.erp_lite.infrastructure.persistence.mongo.document.AuditLogDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditLogRepository extends MongoRepository<AuditLogDocument, ObjectId> {
}
