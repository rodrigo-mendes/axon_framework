package com.kmm.ordermanagement.infrastructure.adapters.restapi.product;

import com.kmm.ordermanagement.core.domain.commands.AddProductCommand;
import com.kmm.ordermanagement.core.domain.commands.RemoveProductCommand;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
//@Api(tags = ["OrderAggregate API"])
public class ProductController {
	
	private final CommandGateway commandGateway;
	private final QueryGateway queryGateway;
	private final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	public ProductController(CommandGateway commandGateway, QueryGateway queryGateway) {
		this.commandGateway = commandGateway;
		this.queryGateway = queryGateway;
	}
	
	@PutMapping("/{orderId}/product")
	public CompletableFuture<ResponseEntity<String>> addProduct(@PathVariable String orderId, @RequestBody AddProductRequest product) {
		// Add logic to create the order
		logger.info("AddProductRequest request: {}", product);
		AddProductCommand command = AddProductCommand.from(UUID.fromString(orderId),product);
		logger.info("AddProductCommand request: {}", command);
		
		return this.commandGateway.send(command)
			.thenApply(result -> {
				logger.info("Order processed successfully.");
				return ResponseEntity.ok("Order processed successfully.");
			})
			.exceptionally(exception -> {
				logger.error("Exception occurred during command execution", exception);
				return ResponseEntity.badRequest().body(exception.getMessage());
			});
	}
	
	
	// Create a method to delete an order
	@DeleteMapping("/{orderId}/product/{productId}")
	public CompletableFuture<ResponseEntity<String>> removeProduct(@PathVariable String orderId, @PathVariable String productId) {
		// Add logic to delete the order by orderId
		logger.info("RemoveProductRequest request: OrderId: {}, ProductId: {}", orderId, productId);
		RemoveProductCommand command = new RemoveProductCommand(UUID.fromString(orderId), UUID.fromString(productId));
		logger.info("RemoveProductCommand request: {}", command);
		return this.commandGateway.send(command).thenApply(result -> {
				logger.info("Order processed successfully.");
				return ResponseEntity.ok("Order processed successfully.");
			})
			.exceptionally(exception -> {
				logger.error("Exception occurred during command execution", exception);
				return ResponseEntity.badRequest().body(exception.getMessage());
			});
	}
}

