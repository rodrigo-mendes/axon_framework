package com.kmm.ordermanagement.infrastructure.adapters.restapi.order;

import com.kmm.ordermanagement.core.domain.model.entities.OrderItem;
import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(UUID orderId,
                                 UUID customerId,
                                 List<OrderItem> orderItems,
                                 String street,
                                 String city,
                                 String state,
                                 String zipCode) {

}
