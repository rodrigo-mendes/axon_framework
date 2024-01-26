package com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories;

import com.kmm.ordermanagement.core.domain.model.aggregate.OrderStatus;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderEntity;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, UUID> {
	
	@Modifying
	@Transactional
	@Query("UPDATE OrderEntity o SET o.status = :status WHERE o.orderId = :orderId")
	void updateOrderStatus(UUID orderId, OrderStatus status);
	
	@Modifying
	@Transactional
	@Query("UPDATE OrderEntity o SET o.totalAmount = :totalAmount WHERE o.orderId = :orderId")
	void updateOrderTotalAmount(UUID orderId, BigDecimal totalAmount);
		

}
