package com.kmm.ordermanagement.core.domain.events;

import com.kmm.ordermanagement.core.domain.commands.CancelOrderCommand;
import com.kmm.ordermanagement.core.domain.model.aggregate.OrderStatus;
import java.util.UUID;

public record OrderCancelledEvent(UUID orderId, OrderStatus status) {
	
	public OrderCancelledEvent(UUID orderId) {
        this(orderId, OrderStatus.CANCELLED);
    }
	
	public static OrderCancelledEvent from(CancelOrderCommand command) {
		return new OrderCancelledEvent(command.orderId(), command.status());
	}

}
