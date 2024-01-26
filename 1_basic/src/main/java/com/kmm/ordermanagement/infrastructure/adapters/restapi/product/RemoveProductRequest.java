package com.kmm.ordermanagement.infrastructure.adapters.restapi.product;

import java.util.UUID;

public record RemoveProductRequest(UUID orderId, UUID productId) {

}
