package com.kmm.ordermanagement.infrastructure.adapters.restapi.order;

import com.kmm.ordermanagement.core.domain.model.entities.OrderItem;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record CreateOrderRequest(UUID orderId,
                                 UUID customerId,
                                 List<OrderItem> orderItems,
                                 String street,
                                 String city,
                                 String state,
                                 String zipCode) {
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CreateOrderRequest that)) {
			return false;
		}
		return Objects.equals(orderId, that.orderId) && Objects.equals(customerId,
			that.customerId) && Objects.equals(orderItems, that.orderItems)
			&& Objects.equals(street, that.street) && Objects.equals(city,
			that.city) && Objects.equals(state, that.state) && Objects.equals(
			zipCode, that.zipCode);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(orderId, customerId, orderItems, street, city, state, zipCode);
	}
	
	@Override
	public String toString() {
		return "CreateOrderRequest{" +
			"orderId=" + orderId +
			", customerId=" + customerId +
			", orderItems=" + orderItems +
			", street='" + street + '\'' +
			", city='" + city + '\'' +
			", state='" + state + '\'' +
			", zipCode='" + zipCode + '\'' +
			'}';
	}
}
