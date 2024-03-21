package com.kmm.ordermanagement.core.domain.commands;

import com.kmm.ordermanagement.infrastructure.adapters.restapi.product.AddProductRequest;
import java.math.BigDecimal;
import java.util.Objects;
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AddProductCommand that)) {
			return false;
		}
		return Objects.equals(orderId, that.orderId) && Objects.equals(productId,
			that.productId) && Objects.equals(productName, that.productName)
			&& Objects.equals(price, that.price) && Objects.equals(quantity,
			that.quantity);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId, productName, price, quantity);
	}
	
	@Override
	public String toString() {
		return "AddProductCommand{" +
			"orderId=" + orderId +
			", productId=" + productId +
			", productName='" + productName + '\'' +
			", price=" + price +
			", quantity=" + quantity +
			'}';
	}
}
