package com.kmm.ordermanagement.infrastructure.adapters.restapi.product;

import java.util.Objects;
import java.util.UUID;

public record RemoveProductRequest(UUID orderId, UUID productId) {
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof RemoveProductRequest that)) {
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
		return "RemoveProductRequest{" +
			"orderId=" + orderId +
			", productId=" + productId +
			'}';
	}
}
