package com.kmm.ordermanagement.core.domain.commands;

import com.kmm.ordermanagement.infrastructure.adapters.restapi.product.AddProductRequest;
import java.math.BigDecimal;
import java.util.UUID;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record AddProductCommand(@TargetAggregateIdentifier UUID orderId, UUID productId, String productName, BigDecimal price, Integer quantity) {
	
	public static AddProductCommand from(UUID orderId, 	AddProductRequest addProductRequest) {
		return new AddProductCommand(orderId,
			addProductRequest.productId(),
			addProductRequest.productName(),
			addProductRequest.price(),
			addProductRequest.quantity());
	}
	
}
