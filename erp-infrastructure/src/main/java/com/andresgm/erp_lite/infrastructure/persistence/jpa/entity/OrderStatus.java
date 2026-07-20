package com.andresgm.erp_lite.infrastructure.persistence.jpa.entity;

/**
 * Mirrors the {@code chk_order_status} CHECK constraint on {@code orders.status}
 * and the {@code catalog-order-statuses} catalog stored in MongoDB.
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
