package com.kmm.ordermanagement.infrastructure.adapters.restapi.product;

import java.math.BigDecimal;
import java.util.UUID;

public record AddProductRequest(UUID productId, String productName, BigDecimal price, Integer quantity) {

}
