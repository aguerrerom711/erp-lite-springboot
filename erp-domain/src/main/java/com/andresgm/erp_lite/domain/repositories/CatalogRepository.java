package com.andresgm.erp_lite.domain.repositories;

import com.andresgm.erp_lite.domain.catalog.Catalog;
import com.andresgm.erp_lite.domain.catalog.CatalogItem;
import com.andresgm.erp_lite.domain.catalog.CatalogType;

import java.util.List;
import java.util.Optional;

/**
*Port read-only for Catalog
 */
interface CatalogRepository {
    Optional<Catalog> findByType(CatalogType type);
    List<CatalogItem> findItemsByType(CatalogType type);
    Optional<CatalogItem> findItemByTypeAndCode(CatalogType type, String code);
}
