package com.kmm.ordermanagement.core.domain.events;

import com.kmm.ordermanagement.core.domain.commands.CancelOrderCommand;
import com.kmm.ordermanagement.core.domain.model.aggregate.OrderStatus;
import java.util.Objects;
import java.util.UUID;

public record OrderCancelledEvent(UUID orderId, OrderStatus status) {
	
	public OrderCancelledEvent(UUID orderId) {
        this(orderId, OrderStatus.CANCELLED);
    }
	
	public static OrderCancelledEvent from(CancelOrderCommand command) {
		return new OrderCancelledEvent(command.orderId(), command.status());
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OrderCancelledEvent that)) {
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
		return "OrderCancelledEvent{" +
			"orderId=" + orderId +
			", status=" + status +
			'}';
	}
}
