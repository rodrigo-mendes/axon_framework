package com.kmm.ordermanagement.infrastructure.adapters.restapi.order;

import java.util.List;

public record OrderQueryListAllResponse(List<OrderDTO> orders) {

}
