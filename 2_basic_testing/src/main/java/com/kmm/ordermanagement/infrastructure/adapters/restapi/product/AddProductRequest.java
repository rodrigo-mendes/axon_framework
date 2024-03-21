package com.kmm.ordermanagement.infrastructure.adapters.restapi.product;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record AddProductRequest(UUID productId, String productName, BigDecimal price, Integer quantity) {
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AddProductRequest that)) {
			return false;
		}
		return Objects.equals(productId, that.productId) && Objects.equals(
			productName, that.productName) && Objects.equals(price, that.price)
			&& Objects.equals(quantity, that.quantity);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(productId, productName, price, quantity);
	}
	
	@Override
	public String toString() {
		return "AddProductRequest{" +
			"productId=" + productId +
			", productName='" + productName + '\'' +
			", price=" + price +
			", quantity=" + quantity +
			'}';
	}
}
