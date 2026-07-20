package com.andresgm.erp_lite.infrastructure.persistence.mongo.document;

/**
 * Embedded element of {@link CatalogDocument#getItems()}.
 */
public record CatalogItem(
        String id,
        String code,
        String value,
        String description,
        Integer displayOrder,
        CatalogItemMetadata metadata
) {
}
