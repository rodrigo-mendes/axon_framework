package com.kmm.ordermanagement.core.domain.commands;

import com.kmm.ordermanagement.core.domain.model.aggregate.OrderStatus;
import java.util.UUID;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CancelOrderCommand(@TargetAggregateIdentifier UUID orderId, OrderStatus status) {

	public CancelOrderCommand(UUID orderId) {
		this(orderId, OrderStatus.CANCELLED);
	}
}
