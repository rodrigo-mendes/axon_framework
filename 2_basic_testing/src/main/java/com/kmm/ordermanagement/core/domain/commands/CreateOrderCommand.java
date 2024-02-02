package com.kmm.ordermanagement.core.domain.commands;

import com.kmm.ordermanagement.core.domain.model.aggregate.OrderStatus;
import com.kmm.ordermanagement.core.domain.model.entities.DeliveryAddress;
import com.kmm.ordermanagement.core.domain.model.entities.OrderItem;
import com.kmm.ordermanagement.infrastructure.adapters.restapi.order.CreateOrderRequest;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.UUID;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateOrderCommand(@TargetAggregateIdentifier UUID orderId,
                                 UUID customerId,
                                 List<OrderItem> orderItems,
                                 DeliveryAddress deliveryAddress,
                                 OrderStatus status)  {
	
	private CreateOrderCommand() {
		this(null,	null, null, null, null);
	}
	public static CreateOrderCommand from(CreateOrderRequest createOrderRequest) {
		Preconditions.checkNotNull(createOrderRequest, "createOrderRequest cannot be null");
		Preconditions.checkNotNull(createOrderRequest.customerId(), "customerId cannot be null");
		Preconditions.checkArgument(!createOrderRequest.orderItems().isEmpty(), "orderItems cannot be null or empty");
		Preconditions.checkNotNull(createOrderRequest.city(), "city cannot be null");
		Preconditions.checkNotNull(createOrderRequest.state(), "state cannot be null");
		Preconditions.checkNotNull(createOrderRequest.street(), "street cannot be null");
		Preconditions.checkNotNull(createOrderRequest.zipCode(), "zipCode cannot be null");
    return new CreateOrderCommand(
			UUID.randomUUID(),
			createOrderRequest.customerId(),
			createOrderRequest.orderItems(),
			new DeliveryAddress(
				createOrderRequest.city(),
				createOrderRequest.state(),
				createOrderRequest.street(),
				createOrderRequest.zipCode()
			),
			OrderStatus.CREATED
			);
  }
}
