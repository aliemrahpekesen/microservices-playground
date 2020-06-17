package com.kocsistem.ms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kocsistem.ms.domain.entity.Order;
import com.kocsistem.ms.domain.model.OrderPlacementReq;
import com.kocsistem.ms.domain.model.OrderPlacementRes;
import com.kocsistem.ms.service.OrderService;

@RestController
@RefreshScope
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Value("${order.max.count}")
	private int orderMaxCount;

	@PostMapping
	public OrderPlacementRes placeAnOrder(@RequestBody OrderPlacementReq request) {
		LOGGER.info("USECASENO : {} || Order Started -> Product Id : {}, Customer Id : {}", request.getUseCaseNo(),
				request.getItemId(), request.getCustomerId());
		OrderPlacementRes response = orderService.create(request);
		LOGGER.info("USECASENO : {} || Order Process Completed -> Product Id : {}, Customer Id : {}, Current Status : {}",
				request.getUseCaseNo(), request.getItemId(), request.getCustomerId(), response.getStatus());

		return response;
	}

	@GetMapping
	public List<Order> getallOrders() {
		List<Order> allOrders = orderService.getOrders();
		return orderMaxCount < allOrders.size() ? allOrders.subList(0, orderMaxCount)
				: allOrders;
	}

	@GetMapping("/{orderId}")
	public Order getOrderById(@PathVariable(required = true, name = "orderId") long orderId) throws Exception {
		Order foundOrder = orderService.getOrderById(orderId);
		if (foundOrder == null) {
			throw new Exception("Order not found!");
		} else
			return foundOrder;
	}

}
