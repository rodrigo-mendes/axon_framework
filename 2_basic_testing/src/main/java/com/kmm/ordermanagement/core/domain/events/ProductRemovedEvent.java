package com.kmm.ordermanagement.core.domain.events;

import com.kmm.ordermanagement.core.domain.commands.RemoveProductCommand;
import java.util.Objects;
import java.util.UUID;

public record ProductRemovedEvent(UUID orderId, UUID productId) {
	
	public static ProductRemovedEvent from(RemoveProductCommand command) {
		return new ProductRemovedEvent(command.orderId(), command.productId());
    }
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ProductRemovedEvent that)) {
			return false;
		}
		return Objects.equals(orderId, that.orderId) && Objects.equals(productId,
			that.productId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId);
	}

	@Override
	public String toString() {
		return "ProductRemovedEvent{" +
			"orderId=" + orderId +
			", productId=" + productId +
			'}';
	}
}
