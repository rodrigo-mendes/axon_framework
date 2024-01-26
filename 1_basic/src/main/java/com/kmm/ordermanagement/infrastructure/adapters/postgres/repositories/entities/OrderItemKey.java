package com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderItemKey implements Serializable {
	
	@Column(name = "order_id")
	private UUID orderId;
	
	@Column(name = "product_id")
	private UUID productId;
	
	public OrderItemKey() {
		// Default constructor
	}
	
	public OrderItemKey(UUID orderId, UUID productId) {
		this.orderId = orderId;
		this.productId = productId;
	}
	
	public UUID getOrderId() {
		return orderId;
	}
	
	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}
	
	public UUID getProductId() {
		return productId;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OrderItemKey that)) {
			return false;
		}
		return Objects.equals(getOrderId(), that.getOrderId()) && Objects.equals(
			getProductId(), that.getProductId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getOrderId(), getProductId());
	}
	
	public void setProductId(UUID productId) {
		this.productId = productId;
	}
}
