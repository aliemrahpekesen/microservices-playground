package com.kocsistem.ms.queue.event;

import com.kocsistem.ms.domain.enums.OrderAction;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder(toBuilder = true)
public class OrderEvent {

	private long orderId;
	private int useCaseToBeRun;
	private OrderAction action;

}
