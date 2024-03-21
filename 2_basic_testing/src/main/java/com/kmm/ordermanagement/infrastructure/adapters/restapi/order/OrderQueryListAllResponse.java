package com.kmm.ordermanagement.infrastructure.adapters.restapi.order;

import java.util.List;
import java.util.Objects;

public record OrderQueryListAllResponse(List<OrderDTO> orders) {
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OrderQueryListAllResponse that)) {
			return false;
		}
		return Objects.equals(orders, that.orders);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(orders);
	}
	
	@Override
	public String toString() {
		return "OrderQueryListAllResponse{" +
			"orders=" + orders +
			'}';
	}
}
