package com.kmm.ordermanagement.core.application;

import com.kmm.ordermanagement.core.domain.queries.FindAllOrdersQuery;
import com.kmm.ordermanagement.core.domain.queries.FindOrderByIdQuery;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.OrderEntityRepository;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderEntity;
import com.kmm.ordermanagement.infrastructure.adapters.restapi.order.OrderDTO;
import com.kmm.ordermanagement.infrastructure.adapters.restapi.order.OrderQueryListAllResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderQueryHandler {
	
    Logger logger = LoggerFactory.getLogger(OrderQueryHandler.class);
	private final OrderEntityRepository orderEntityRepository;
	
	@Autowired
	public OrderQueryHandler(OrderEntityRepository orderEntityRepository) {
		this.orderEntityRepository = orderEntityRepository;
	}
	
    @QueryHandler
    public OrderDTO findById(FindOrderByIdQuery query) {
		Optional<OrderEntity> orderEntity = orderEntityRepository.findById(query.uuid());
		if (orderEntity.isPresent()) {
			logger.info("Order found: {}", orderEntity.get());
			return OrderDTO.from(orderEntity.get());
		}else {
			logger.info("Order not found: {}", query.uuid());
			throw new OrderNotFoundException("Order not found: " + query.uuid());
		}
    }
	
	@QueryHandler
	public OrderQueryListAllResponse findAll(FindAllOrdersQuery query) {
		List<OrderEntity> orderEntities = orderEntityRepository.findAll();
		List<OrderDTO> result = convertOrderEntities(orderEntities);
		logger.info("Order found: {}", result);
        return new OrderQueryListAllResponse(result);
    }
	
	private List<OrderDTO> convertOrderEntities(List<OrderEntity> orderEntities) {
		return orderEntities.stream()
			.map(OrderDTO::from)
			.collect(Collectors.toList());
	}
	
}