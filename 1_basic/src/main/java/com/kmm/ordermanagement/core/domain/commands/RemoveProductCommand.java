package com.kmm.ordermanagement.core.domain.commands;

import java.util.UUID;
import com.kmm.ordermanagement.infrastructure.adapters.restapi.product.RemoveProductRequest;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record RemoveProductCommand(@TargetAggregateIdentifier UUID orderId, UUID productId) {

}
