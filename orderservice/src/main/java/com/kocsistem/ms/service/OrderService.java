package com.kocsistem.ms.service;

import java.util.List;

import com.kocsistem.ms.domain.entity.Order;
import com.kocsistem.ms.domain.enums.OrderStatus;
import com.kocsistem.ms.domain.model.OrderPlacementReq;
import com.kocsistem.ms.domain.model.OrderPlacementRes;

public interface OrderService {

	List<Order> getOrders();

	Order getOrderById(long id);

	OrderPlacementRes create(OrderPlacementReq req);

	void update(long id, OrderStatus status);
	
}
