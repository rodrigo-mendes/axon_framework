package com.kmm.ordermanagement.infrastructure.adapters.restapi.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kmm.ordermanagement.core.application.OrderQueryHandler;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderEntity;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderItemEntity;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDTO(@JsonProperty("orderId") UUID orderId,
					   @JsonProperty("customerId") UUID customerId,
					   @JsonProperty("orderItems") List<OrderItemDTO> orderItems,
					   @JsonProperty("street") String street,
					   @JsonProperty("city") String city,
					   @JsonProperty("state") String state,
					   @JsonProperty("zipCode") String zipCode
) {
	
	private OrderDTO(){
		this(null, null, List.of(), null, null, null, null);
	}
	
	public static OrderDTO from(OrderEntity orderEntity) {
		return new OrderDTO(orderEntity.getOrderId(),
				orderEntity.getCustomerId(),
				getOrderItems(orderEntity.getOrderItems()),
				orderEntity.getDeliveryAddress().getStreet(),
				orderEntity.getDeliveryAddress().getCity(),
				orderEntity.getDeliveryAddress().getState(),
				orderEntity.getDeliveryAddress().getZipCode());
	}
	
	private static List<OrderItemDTO> getOrderItems( List<OrderItemEntity> orderItems) {
        return orderItems.stream()
                .map(orderItem -> {
					logger.info("OrderItem: {}", orderItem);
					return new OrderItemDTO(orderItem.getId().getProductId(), orderItem.getProductName(), orderItem.getPrice(), orderItem.getQuantity());
					
				})
                .collect(Collectors.toList());
    }
	
	static Logger logger = LoggerFactory.getLogger(OrderDTO.class);

}
