package com.kmm.ordermanagement.infrastructure.adapters.restapi.order;

import java.util.UUID;

import com.kmm.ordermanagement.core.domain.commands.CancelOrderCommand;
import com.kmm.ordermanagement.core.domain.commands.CreateOrderCommand;
import com.kmm.ordermanagement.core.domain.queries.FindAllOrdersQuery;
import com.kmm.ordermanagement.core.domain.queries.FindOrderByIdQuery;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
//@Api(tags = ["OrderAggregate API"])
public class OrderController {
	
	Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	private final CommandGateway commandGateway;
	private final QueryGateway queryGateway;
	
	@Autowired
	public OrderController(CommandGateway commandGateway, QueryGateway queryGateway) {
		this.commandGateway = commandGateway;
		this.queryGateway = queryGateway;
	}
	
	@PostMapping(value = "/",consumes = "application/json")
	public CompletableFuture<ResponseEntity<String>> createOrder(@RequestBody CreateOrderRequest order) {
		// Add logic to create the order
		logger.info("CreateOrderRequest request: {}", order);
		CreateOrderCommand command = CreateOrderCommand.from(order);
		logger.info("CreateOrderCommand request: {}", command);
		
		return this.commandGateway.send(command)
			.thenApply(result -> {
				logger.info("OrderAggregate processing started successfully.");
				return ResponseEntity.ok("OrderAggregate processing started successfully.");
			})
			.exceptionally(exception -> {
				logger.error("Exception occurred during command execution", exception);
				return ResponseEntity.badRequest().body(exception.getMessage());
			});
	}
	
	@DeleteMapping("/{orderId}")
	public CompletableFuture<ResponseEntity<Object>> cancelOrder(@PathVariable UUID orderId) {
		// Add logic to delete the order by orderId
		logger.info("Order cancelled request: {}", orderId);
		CancelOrderCommand command = new CancelOrderCommand(orderId);
		logger.info("CancelOrderCommand request: {}", command);
		
		return this.commandGateway.send(command)
			.thenApply(result -> {
				logger.info("Order cancelled successfully: {}", orderId);
				return ResponseEntity.noContent().build();
			})
			.exceptionally(exception -> {
				logger.error("Exception occurred during command execution", exception);
				return ResponseEntity.badRequest().body(exception.getMessage());
			});
	}
	
	
	// Create a method to get an order
	@GetMapping("/{orderId}")
	public CompletableFuture<ResponseEntity<?>> getOrder(@PathVariable UUID orderId) {
		// Add logic to get the order by orderId
		FindOrderByIdQuery query = new FindOrderByIdQuery(orderId);
		logger.info("GetOrderByIdQuery request: {}", query);
		return this.queryGateway.query(query, OrderDTO.class)
			.handle((order, exception) -> {
				if (exception != null) {
					logger.error("Exception occurred during query execution", exception);
					return ResponseEntity.notFound().build();
				} else {
					logger.info("Order found: {}", order);
					return ResponseEntity.ok(order);
				}
			});
	}
	
	
	// Create a method to get all orders
	@GetMapping(value = "/")
	public CompletableFuture<ResponseEntity<?>> getAllOrders() {
		FindAllOrdersQuery query = new FindAllOrdersQuery();
		logger.info("GetOrderByIdQuery request: {}", query);
		CompletableFuture<OrderQueryListAllResponse> result = queryGateway.query(query, OrderQueryListAllResponse.class);
		logger.info("Result: {}", result);
		return result
			.thenApply(orderList -> {
				if (orderList.orders().isEmpty()) {
					logger.info("No orders found");
					return ResponseEntity.notFound().build();
				} else {
					logger.info("Orders found: {}", orderList);
					return ResponseEntity.ok(orderList.orders());
				}
			})
			.exceptionally(exception -> {
				logger.error("Exception occurred during query execution", exception);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			});
	}
	
}