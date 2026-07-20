package com.andresgm.erp_lite.infrastructure.persistence.mongo.document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "product_documents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductInCatalogDocument {

    @Id
    private String id;


    private String sku;

    private String name;

    private String description;

    private BigDecimal price;

    private String currency;

    private Integer stock;


    private String categoryId;

    private String categoryName;

    private String imageUrl;

    private boolean active;

    private List<String> tags;

    private Map<String, String> specifications;

    private Instant createdAt;

    private Instant updatedAt;
}
