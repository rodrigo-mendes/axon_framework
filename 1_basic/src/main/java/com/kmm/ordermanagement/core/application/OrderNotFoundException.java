package com.kmm.ordermanagement.core.application;

import java.io.Serial;

public class OrderNotFoundException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;
	public OrderNotFoundException(String message) {
		super(message);
	}
	public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
	public OrderNotFoundException(Throwable cause) {
        super(cause);
    }
	
}
