package com.kmm.ordermanagement.core.domain.events;

import com.kmm.ordermanagement.core.domain.commands.CreateOrderCommand;
import com.kmm.ordermanagement.core.domain.model.aggregate.OrderStatus;
import com.kmm.ordermanagement.core.domain.model.entities.DeliveryAddress;
import com.kmm.ordermanagement.core.domain.model.entities.OrderItem;
import java.util.List;
import java.util.Objects;
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OrderCreatedEvent that)) {
			return false;
		}
		return Objects.equals(orderId, that.orderId) && Objects.equals(customerId,
			that.customerId) && Objects.equals(orderItems, that.orderItems)
			&& Objects.equals(deliveryAddress, that.deliveryAddress) && status == that.status;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(orderId, customerId, orderItems, deliveryAddress, status);
	}
}
