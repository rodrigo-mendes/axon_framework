package com.kmm.ordermanagement.core.domain.model.entities;

import com.kmm.ordermanagement.core.domain.commands.AddProductCommand;
import com.kmm.ordermanagement.core.domain.events.ProductAddedEvent;
import java.math.BigDecimal;
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
}

