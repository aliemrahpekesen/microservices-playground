package com.kocsistem.ms.queue.event;

import com.kocsistem.ms.domain.enums.DeliveryAction;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder(toBuilder = true)
public class DeliveryEvent {

	private long orderId;
	private DeliveryAction action;

}
