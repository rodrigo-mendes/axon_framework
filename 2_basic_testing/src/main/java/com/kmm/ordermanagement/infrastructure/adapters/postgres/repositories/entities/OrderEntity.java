package com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities;

import com.kmm.ordermanagement.core.domain.events.OrderCreatedEvent;
import com.kmm.ordermanagement.core.domain.model.aggregate.OrderStatus;
import com.kmm.ordermanagement.core.domain.model.entities.DeliveryAddress;
import com.kmm.ordermanagement.core.domain.model.entities.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {
	
	@Id
	@Column(name = "order_id")
	private UUID orderId;
	@Column(name = "customer_id")
	private UUID customerId;
	@OneToMany(mappedBy = "orderEntities", cascade = CascadeType.MERGE, orphanRemoval = true)
	private List<OrderItemEntity> orderItems;
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
	@Embedded
	private DeliveryAddress deliveryAddress;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private OrderStatus status;
	
	public OrderEntity() {
    }
	public OrderEntity(OrderCreatedEvent event) {
		this.orderId = event.orderId();
		this.customerId = event.customerId();
		this.orderItems = convertOrderItems(event.orderId(), event.orderItems());
		this.totalAmount = calculateTotalAmount(event.orderItems());
		this.deliveryAddress = event.deliveryAddress();
		this.status = event.status();
	}
	

	public OrderEntity(UUID orderId, UUID customerId, List<OrderItemEntity> orderItems, BigDecimal totalAmount, DeliveryAddress deliveryAddress, OrderStatus status) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.orderItems = orderItems;
		this.totalAmount = totalAmount;
		this.deliveryAddress = deliveryAddress;
		this.status = status;
	}
	
	public UUID getOrderId() {
        return orderId;
    }
	
	public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
	
    public UUID getCustomerId() {
        return customerId;
    }
	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}
	public List<OrderItemEntity> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemEntity> orderItems) {
		this.orderItems = orderItems;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public DeliveryAddress getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
		return orderItems.stream().map(OrderItem::getPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public List<OrderItemEntity> convertOrderItems(UUID orderId,List<OrderItem> orderItems) {
		return orderItems.stream()
			.map(orderItem -> new OrderItemEntity(orderId, orderItem.getProductId(), orderItem.getProductName(), orderItem.getPrice(), orderItem.getQuantity()))
			.toList();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OrderEntity that)) {
			return false;
		}
		return Objects.equals(getOrderId(), that.getOrderId()) && Objects.equals(
			getCustomerId(), that.getCustomerId()) && Objects.equals(getOrderItems(),
			that.getOrderItems()) && Objects.equals(getTotalAmount(), that.getTotalAmount())
			&& Objects.equals(getDeliveryAddress(), that.getDeliveryAddress())
			&& getStatus() == that.getStatus();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getOrderId(), getCustomerId(), getOrderItems(), getTotalAmount(),
			getDeliveryAddress(), getStatus());
	}
	
	@Override
	public String toString() {
		return "OrderEntity{" +
			"orderId=" + orderId +
			", customerId=" + customerId +
			", orderItems=" + orderItems +
			", totalAmount=" + totalAmount +
			", deliveryAddress=" + deliveryAddress +
			", status=" + status +
			'}';
	}
}

