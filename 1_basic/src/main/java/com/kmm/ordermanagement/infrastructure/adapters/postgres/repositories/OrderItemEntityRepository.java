package com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories;

import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderItemEntity;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderItemKey;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, OrderItemKey> {

}