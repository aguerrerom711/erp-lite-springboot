package com.andresgm.erp_lite.infrastructure.persistence.jpa.repositories;

import com.andresgm.erp_lite.infrastructure.persistence.jpa.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, UUID> {
}
