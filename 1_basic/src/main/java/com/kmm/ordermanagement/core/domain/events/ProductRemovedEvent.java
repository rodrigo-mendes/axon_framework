package com.kmm.ordermanagement.core.domain.events;

import com.kmm.ordermanagement.core.domain.commands.RemoveProductCommand;
import java.util.UUID;

public record ProductRemovedEvent(UUID orderId, UUID productId) {
	
	public static ProductRemovedEvent from(RemoveProductCommand command) {
		return new ProductRemovedEvent(command.orderId(), command.productId());
    }

}
