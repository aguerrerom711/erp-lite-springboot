package com.andresgm.erp_lite.infrastructure.persistence.mongo.document;

/**
 * Mirrors the distinct {@code catalogType} values seeded into
 * {@code erp_catalog_db.catalogs} (see db/mongodb/init-mongo.js).
 */
public enum CatalogType {
    PRODUCT_CATEGORIES,
    ORDER_STATUSES,
    PAYMENT_METHODS,
    SHIPPING_METHODS,
    COUNTRIES,
    CURRENCIES
}
