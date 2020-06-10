package com.kocsistem.ms.domain.entity;

import com.kocsistem.ms.domain.enums.OrderStatus;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder(toBuilder = true)
public class Order {

	private long id;
	private long customerId;
	private long itemId;
	private OrderStatus status;

}
