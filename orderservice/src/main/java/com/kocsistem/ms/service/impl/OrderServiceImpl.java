package com.kocsistem.ms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kocsistem.ms.domain.entity.Order;
import com.kocsistem.ms.domain.enums.OrderStatus;
import com.kocsistem.ms.domain.model.OrderPlacementReq;
import com.kocsistem.ms.domain.model.OrderPlacementRes;
import com.kocsistem.ms.queue.manager.OrderPlacementEventManager;
import com.kocsistem.ms.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderPlacementEventManager eventManager;

	private static List<Order> orderList = new ArrayList<Order>();

	private Random random = new Random();

	@Override
	public OrderPlacementRes create(OrderPlacementReq req) {
		Order newOrder = Order.builder().id(Math.abs(random.nextLong())).customerId(req.getCustomerId()).itemId(req.getItemId())
				.status(OrderStatus.INPROGRESS).build();
		LOGGER.info("Order is Initiated with Id : {0}.", newOrder.getId());
		orderList.add(newOrder);

		LOGGER.info("OrderCreated Event is being Published : {0}.", newOrder.getId());
		eventManager.publishOrderPlacementEvent(newOrder.getId(),req.getUseCaseNo());
		LOGGER.info("OrderCreated Event is Published : {0}.", newOrder.getId());
		OrderPlacementRes response = new OrderPlacementRes();
		response.setOrderId(newOrder.getId());
		response.setStatus(newOrder.getStatus());
		response.setUseCaseNo(req.getUseCaseNo());
		return response;
	}

	@Override
	public void update(long id, OrderStatus status) {
		Optional<Order> foundOrder = orderList.stream().filter(o -> id == o.getId()).findFirst();
		if (foundOrder.isPresent()) {
			LOGGER.info("{} Order status is being updated. FROM-> {}, TO-> {}", id, foundOrder.get().getStatus(),
					status);
			foundOrder.get().setStatus(status);
		} else {
			LOGGER.info("No Matching Order Found with order Id {}", id);
		}

	}

	@Override
	public List<Order> getOrders() {
		return orderList;
	}

	@Override
	public Order getOrderById(long id) {
		Optional<Order> foundOrder = orderList.stream().filter(o -> id == o.getId()).findFirst();

		if (foundOrder.isPresent()) {
			return foundOrder.get();
		} else {
			LOGGER.info("No Matching Order Found with order Id {}", id);
			return null;
		}
	}

}
