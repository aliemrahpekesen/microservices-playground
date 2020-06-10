package com.kocsistem.ms.domain.model;

import com.kocsistem.ms.domain.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderPlacementRes {
	private Long orderId;
	private int useCaseNo;
	private OrderStatus status;
}
