package com.andresgm.erp_lite.infrastructure.persistence.mongo.document;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "audit_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuditLogDocument {

    @Id
    private ObjectId id;

    @Indexed
    private String className;

    private String methodName;

    private String userId;

    @Indexed
    private Instant timestamp;

    private Long executionTimeMs;

    private boolean success;

    private String errorMessage;

    private String ipAddress;

    private String endpoint;
}
