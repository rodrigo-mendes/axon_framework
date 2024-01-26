package com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities;

import com.kmm.ordermanagement.core.domain.events.ProductAddedEvent;
import com.kmm.ordermanagement.core.domain.model.entities.OrderItem;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders_items")
public class OrderItemEntity {
	
	@EmbeddedId
	private OrderItemKey id;
	
	@Column(nullable = false)
	private String productName;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "order_id", insertable = false, updatable = false)
	private OrderEntity orderEntities;
	
	
	// Default constructor for JPA
	public OrderItemEntity() {
	}
	
	public OrderItemEntity(UUID orderId, OrderItem orderItem) {
		this.id = new OrderItemKey(orderId, orderItem.getProductId());
		this.productName = orderItem.getProductName();
		this.price = orderItem.getPrice();
		this.quantity = orderItem.getQuantity();
	}
	
	public OrderItemEntity(UUID orderId, UUID productId, String productName, BigDecimal price, Integer quantity) {
		this.id = new OrderItemKey(orderId, productId);
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}
	
	public OrderItemEntity(ProductAddedEvent event) {
		this.id = new OrderItemKey(event.orderId(), event.productId());
        this.productName = event.productName();
        this.price = event.price();
        this.quantity = event.quantity();
	}
	
	public OrderItemKey getId() {
        return id;
    }
	
	public String getProductName() {
		return productName;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	
	public OrderEntity getOrderEntities() {
		return orderEntities;
	}
	
	public void setOrderEntities(
		OrderEntity orderEntities) {
		this.orderEntities = orderEntities;
	}
	
}

