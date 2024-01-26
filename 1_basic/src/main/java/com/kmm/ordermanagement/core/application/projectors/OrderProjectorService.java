package com.kmm.ordermanagement.core.application.projectors;

import com.kmm.ordermanagement.core.domain.events.OrderCancelledEvent;
import com.kmm.ordermanagement.core.domain.events.OrderCreatedEvent;
import com.kmm.ordermanagement.core.domain.events.ProductAddedEvent;
import com.kmm.ordermanagement.core.domain.events.ProductRemovedEvent;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.OrderEntityRepository;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.OrderItemEntityRepository;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderEntity;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderItemEntity;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderItemKey;
import java.math.BigDecimal;
import java.util.Optional;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProjectorService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderProjectorService.class);
	private final OrderEntityRepository orderEntityRepository;
	private final OrderItemEntityRepository orderItemEntityRepository;
	
	@Autowired
	public OrderProjectorService(OrderEntityRepository orderEntityRepository, OrderItemEntityRepository orderItemEntityRepository) {
		this.orderEntityRepository = orderEntityRepository;
		this.orderItemEntityRepository = orderItemEntityRepository;
	}
	
	@EventHandler
	public void on(OrderCreatedEvent event) {
        logger.info("Projecting OrderCreatedEvent: {}", event);
		// Add logic to project the OrderCreatedEvent
		OrderEntity orderEntity = new OrderEntity(event);
		orderEntityRepository.save(orderEntity);
		
    }
	@EventHandler
	public void on(OrderCancelledEvent event) {
        logger.info("Projecting OrderCancelledEvent: {}", event);
		// Add logic to project the OrderCreatedEvent
		orderEntityRepository.updateOrderStatus(event.orderId(), event.status());
    }
	
	@EventHandler
	public void on(ProductAddedEvent event) {
		// Add logic to project the ProductAddedEvent
		logger.info("Projecting ProductAddedEvent: {}", event);
		OrderItemEntity orderItemEntity = new OrderItemEntity(event);
		Optional<OrderEntity> orderEntity = orderEntityRepository.findById(event.orderId());
		orderEntity.ifPresent(entity ->
			orderEntityRepository.updateOrderTotalAmount(entity.getOrderId(), entity.getTotalAmount().add(
				event.price().multiply(BigDecimal.valueOf(event.quantity())))
			)
		);
		orderItemEntityRepository.save(orderItemEntity);
	}
	
	@EventHandler
	public void on(ProductRemovedEvent event) {
		// Add logic to project the ProductAddedEvent
		logger.info("Projecting ProductAddedEvent: {}", event);
		OrderItemKey key = new OrderItemKey(event.orderId(), event.productId());
		Optional<OrderEntity> orderEntity = orderEntityRepository.findById(event.orderId());
		Optional<OrderItemEntity> orderItemEntity = orderItemEntityRepository.findById(key);
		OrderItemEntity newItens = orderItemEntity.get();
		orderEntity.ifPresent(entity ->
			orderEntityRepository.updateOrderTotalAmount(entity.getOrderId(),
				entity.getTotalAmount().subtract(
					newItens.getPrice().multiply(BigDecimal.valueOf(newItens.getQuantity()))
				)
			)
		);
		orderItemEntityRepository.deleteById(key);
	}
}
