package com.kmm.ordermanagement.core.domain.events;

import com.kmm.ordermanagement.core.domain.commands.AddProductCommand;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductAddedEvent(UUID orderId, UUID productId, String productName, BigDecimal price, Integer quantity) {

	public ProductAddedEvent(AddProductCommand command) {
		this(command.orderId(),
			command.productId(),
			command.productName(),
			command.price(),
			command.quantity());
	}
}
