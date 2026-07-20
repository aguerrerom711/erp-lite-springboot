package com.andresgm.erp_lite.infrastructure.persistence.mongo.document;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "catalogs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CatalogDocument {

    @Id
    private String id;

    private CatalogType catalogType;

    private String name;

    private String description;

    private boolean active;

    private List<CatalogItem> items;

    private Instant createdAt;

    private Instant updatedAt;
}
