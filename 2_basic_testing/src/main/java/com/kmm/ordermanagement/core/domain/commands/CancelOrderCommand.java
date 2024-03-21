package com.kmm.ordermanagement.core.domain.commands;

import com.kmm.ordermanagement.core.domain.model.aggregate.OrderStatus;
import java.util.Objects;
import java.util.UUID;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CancelOrderCommand(@TargetAggregateIdentifier UUID orderId, OrderStatus status) {

	public CancelOrderCommand(UUID orderId) {
		this(orderId, OrderStatus.CANCELLED);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CancelOrderCommand that)) {
			return false;
		}
		return Objects.equals(orderId, that.orderId) && status == that.status;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(orderId, status);
	}
	
	@Override
	public String toString() {
		return "CancelOrderCommand{" +
			"orderId=" + orderId +
			", status=" + status +
			'}';
	}
}
