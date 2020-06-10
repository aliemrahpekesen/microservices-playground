package com.kocsistem.ms.queue.event;

import com.kocsistem.ms.domain.enums.PaymentAction;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder(toBuilder = true)
public class PaymentEvent {

	private long orderId;
	private long paymentConfirmationId;
	private int useCaseNoToBeRun;
	private PaymentAction action;

}
