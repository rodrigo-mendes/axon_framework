package com.kmm.ordermanagement.infrastructure.adapters.restapi.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kmm.ordermanagement.core.domain.model.entities.OrderItem;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderEntity;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderItemEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderItemDTO( @JsonProperty("productId") UUID productId,
							@JsonProperty("productName") String productName,
							@JsonProperty("price") BigDecimal price,
							@JsonProperty("quantity") Integer quantity) {
							
	private OrderItemDTO(){
        this(null, null, null, null);
    }
	
    public OrderItemDTO from(OrderItemEntity orderItemEntity) {
		return new OrderItemDTO(orderItemEntity.getId().getProductId(),
			orderItemEntity.getProductName(),
			orderItemEntity.getPrice(),
			orderItemEntity.getQuantity());
	}
}
