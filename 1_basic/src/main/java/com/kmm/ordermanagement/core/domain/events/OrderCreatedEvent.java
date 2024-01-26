package com.kmm.ordermanagement.core.domain.events;

import com.kmm.ordermanagement.core.domain.commands.CreateOrderCommand;
import com.kmm.ordermanagement.core.domain.model.aggregate.OrderStatus;
import com.kmm.ordermanagement.core.domain.model.entities.DeliveryAddress;
import com.kmm.ordermanagement.core.domain.model.entities.OrderItem;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(UUID orderId,
                                UUID customerId,
                                List<OrderItem> orderItems,
                                DeliveryAddress deliveryAddress,
								
                                OrderStatus status) {

	private OrderCreatedEvent() {
		this(null, null, null, null, null);
	}

	public static OrderCreatedEvent from(CreateOrderCommand command) {
		return new OrderCreatedEvent(command.orderId(),
			command.customerId(),
			command.orderItems(),
			command.deliveryAddress(),
			command.status());
	}

}
