package com.kmm.ordermanagement.core.domain.events;

import com.kmm.ordermanagement.core.domain.commands.AddProductCommand;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record ProductAddedEvent(UUID orderId, UUID productId, String productName, BigDecimal price, Integer quantity) {

	public ProductAddedEvent(AddProductCommand command) {
		this(command.orderId(),
			command.productId(),
			command.productName(),
			command.price(),
			command.quantity());
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ProductAddedEvent that)) {
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
}
