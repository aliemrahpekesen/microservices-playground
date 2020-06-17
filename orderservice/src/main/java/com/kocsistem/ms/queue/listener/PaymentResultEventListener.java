package com.kocsistem.ms.queue.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kocsistem.ms.domain.enums.OrderStatus;
import com.kocsistem.ms.domain.enums.PaymentAction;
import com.kocsistem.ms.queue.event.PaymentEvent;
import com.kocsistem.ms.service.OrderService;

@Component
public class PaymentResultEventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentResultEventListener.class);

	@Autowired
	private OrderService orderService;

	@StreamListener("order-input-from-payment")
	public void paymentResultEventHandler(@Payload PaymentEvent paymentEventPayload) {

		if (PaymentAction.PAYMENT_SUCCEEDED.equals(paymentEventPayload.getAction())) {
			LOGGER.info("PAYMENT_SUCCEEDED event received for order {} with payment confirmation Id {}",
					paymentEventPayload.getOrderId(), paymentEventPayload.getPaymentConfirmationId());
			orderService.update(paymentEventPayload.getOrderId(), OrderStatus.PLACED);
		} else if (PaymentAction.PAYMENT_FAILED.equals(paymentEventPayload.getAction())) {
			LOGGER.info("PAYMENT_FAILED event received for order {}.", paymentEventPayload.getOrderId());
			orderService.update(paymentEventPayload.getOrderId(), OrderStatus.NOT_PROCEED);
		}

	}
}
