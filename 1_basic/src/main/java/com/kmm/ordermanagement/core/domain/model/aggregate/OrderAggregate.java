package com.kmm.ordermanagement.core.domain.model.aggregate;

import com.google.common.base.Preconditions;
import com.kmm.ordermanagement.core.domain.commands.AddProductCommand;
import com.kmm.ordermanagement.core.domain.commands.CancelOrderCommand;
import com.kmm.ordermanagement.core.domain.commands.CreateOrderCommand;
import com.kmm.ordermanagement.core.domain.commands.RemoveProductCommand;
import com.kmm.ordermanagement.core.domain.events.OrderCancelledEvent;
import com.kmm.ordermanagement.core.domain.events.OrderCreatedEvent;
import com.kmm.ordermanagement.core.domain.events.ProductAddedEvent;
import com.kmm.ordermanagement.core.domain.events.ProductRemovedEvent;
import com.kmm.ordermanagement.core.domain.model.entities.OrderItem;
import com.kmm.ordermanagement.core.domain.model.entities.DeliveryAddress;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class OrderAggregate {
	
	@AggregateIdentifier
	private UUID orderId;
	private UUID customerId;
	@AggregateMember
	private List<OrderItem> orderItems;
	private BigDecimal totalAmount;
	private DeliveryAddress deliveryAddress;
	private OrderStatus status;
	
	public OrderAggregate() {
	}
	
	@CommandHandler
	public OrderAggregate(CreateOrderCommand command) {
		Preconditions.checkArgument(command != null, "CreateOrderCommand cannot be null");
		Preconditions.checkArgument(command.orderId() != null, "orderId cannot be null in the CreateOrderCommand");
		AggregateLifecycle.apply(OrderCreatedEvent.from(command));
	}
	
	
	@EventSourcingHandler
	public void handle(OrderCreatedEvent event) {
		this.orderId = event.orderId();
		this.customerId = event.customerId();
		this.orderItems = event.orderItems();
		this.totalAmount = calculateTotalAmount(event.orderItems());
		this.deliveryAddress = event.deliveryAddress();
		this.status = event.status();
	}
	
	@CommandHandler
	public void handle(CancelOrderCommand command) {
		Preconditions.checkArgument(command != null, "CreateOrderCommand cannot be null");
		Preconditions.checkArgument(command.orderId() != null, "orderId cannot be null in the CreateOrderCommand");
		Preconditions.checkArgument(command.status() != null, "status cannot be null in the CreateOrderCommand");
		Preconditions.checkArgument(command.status() == OrderStatus.CANCELLED, "status must be CANCELLED in the CancelOrderCommand");
        AggregateLifecycle.apply(OrderCancelledEvent.from(command));
    }
	
	@EventSourcingHandler
	public void on(OrderCancelledEvent event) {
		this.status = event.status();
	}
	
	//create method to calculate total amount
	private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
		return orderItems.stream().map(OrderItem::getPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	@CommandHandler
	public void handle(AddProductCommand addProductCommand) {
		Preconditions.checkArgument(addProductCommand!= null, "AddProductCommand cannot be null");
		Preconditions.checkArgument(addProductCommand.orderId()!= null, "orderId cannot be null in the AddProductCommand");
		Preconditions.checkArgument(addProductCommand.productId()!= null, "productId cannot be null in the AddProductCommand");
		Preconditions.checkArgument(addProductCommand.quantity()!= null, "quantity cannot be null in the AddProductCommand");
		Preconditions.checkArgument(addProductCommand.price()!= null, "price cannot be null in the AddProductCommand");
		Preconditions.checkArgument(addProductCommand.productName()!= null, "productName cannot be null in the AddProductCommand");
		
		AggregateLifecycle.apply(new ProductAddedEvent(addProductCommand));
	}
	
	@EventSourcingHandler
	public void on(ProductAddedEvent event) {
		this.orderItems.add( OrderItem.from(event));
		this.totalAmount = calculateTotalAmount(orderItems);
		
	}
	
	@CommandHandler
	public void handle(RemoveProductCommand removeProductCommand ) {
		Preconditions.checkArgument(removeProductCommand!= null, "RemoveProductCommand cannot be null");
        Preconditions.checkArgument(removeProductCommand.orderId()!= null, "orderId cannot be null in the RemoveProductCommand");
        Preconditions.checkArgument(removeProductCommand.productId()!= null, "productId cannot be null in the RemoveProductCommand");
        
        AggregateLifecycle.apply(ProductRemovedEvent.from(removeProductCommand));
	}
	
	@EventSourcingHandler
    public void on(ProductRemovedEvent event) {
        this.orderItems.removeIf(orderItem -> orderItem.getProductId().equals(event.productId()));
        this.totalAmount = calculateTotalAmount(orderItems);
    }
	
}