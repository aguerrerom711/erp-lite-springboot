package com.andresgm.erp_lite.infrastructure.persistence.mongo.document;

import java.math.BigDecimal;
import java.util.List;

/**
 * Embedded, catalogType-dependent metadata bag for a {@link CatalogItem}.
 * Each {@link CatalogType} only populates the subset of fields relevant to it
 * (e.g. PAYMENT_METHODS uses icon/fee, COUNTRIES uses flag/currency/phonePrefix).
 */
public record CatalogItemMetadata(
        String icon,
        String color,
        BigDecimal fee,
        BigDecimal cost,
        Integer estimatedDays,
        List<String> nextStatuses,
        String flag,
        String currency,
        String phonePrefix,
        String symbol,
        Integer decimalPlaces
) {
}
