package com.kmm.ordermanagement.core.domain.model.entities;

import com.kmm.ordermanagement.core.domain.commands.AddProductCommand;
import com.kmm.ordermanagement.core.domain.events.ProductAddedEvent;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import org.axonframework.modelling.command.EntityId;

public class OrderItem {
  @EntityId
  private UUID productId;
  private String productName;
  private BigDecimal price;
  private Integer quantity;
  
  public OrderItem() {
  }
  
  public static OrderItem from(ProductAddedEvent productAddedEvent) {
    return new OrderItem(productAddedEvent.productId(),
      productAddedEvent.productName(),
      productAddedEvent.price(),
      productAddedEvent.quantity());
  }

  public OrderItem(UUID productId, String productName, BigDecimal price, Integer quantity) {
    this.productId = productId;
    this.productName = productName;
    this.price = price;
    this.quantity = quantity;
  }

  public UUID getProductId() {
    return productId;
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
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OrderItem orderItem)) {
      return false;
    }
	  return Objects.equals(getProductId(), orderItem.getProductId())
        && Objects.equals(getProductName(), orderItem.getProductName())
        && Objects.equals(getPrice(), orderItem.getPrice()) && Objects.equals(
        getQuantity(), orderItem.getQuantity());
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getProductId(), getProductName(), getPrice(), getQuantity());
  }
  
  @Override
  public String toString() {
    return "OrderItem{" +
        "productId=" + productId +
        ", productName='" + productName + '\'' +
        ", price=" + price +
        ", quantity=" + quantity +
        '}';
  }
}

