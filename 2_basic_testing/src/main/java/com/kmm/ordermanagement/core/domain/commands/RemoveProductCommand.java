package com.kmm.ordermanagement.core.domain.commands;

import java.util.Objects;
import java.util.UUID;
import com.kmm.ordermanagement.infrastructure.adapters.restapi.product.RemoveProductRequest;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record RemoveProductCommand(@TargetAggregateIdentifier UUID orderId, UUID productId) {
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof RemoveProductCommand that)) {
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
		return "RemoveProductCommand{" +
			"orderId=" + orderId +
			", productId=" + productId +
			'}';
	}
}
