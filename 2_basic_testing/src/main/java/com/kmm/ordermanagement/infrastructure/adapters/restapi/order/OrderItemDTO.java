package com.kmm.ordermanagement.infrastructure.adapters.restapi.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kmm.ordermanagement.core.domain.model.entities.OrderItem;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderEntity;
import com.kmm.ordermanagement.infrastructure.adapters.postgres.repositories.entities.OrderItemEntity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderItemDTO( @JsonProperty("productId") UUID productId,
							@JsonProperty("productName") String productName,
							@JsonProperty("price") BigDecimal price,
							@JsonProperty("quantity") Integer quantity) {
							
	private OrderItemDTO(){
        this(null, null, null, null);
    }
	
    public OrderItemDTO from(OrderItemEntity orderItemEntity) {
		return new OrderItemDTO(orderItemEntity.getId().getProductId(),
			orderItemEntity.getProductName(),
			orderItemEntity.getPrice(),
			orderItemEntity.getQuantity());
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OrderItemDTO that)) {
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
		return "OrderItemDTO{" +
			"productId=" + productId +
			", productName='" + productName + '\'' +
			", price=" + price +
			", quantity=" + quantity +
			'}';
	}
}
