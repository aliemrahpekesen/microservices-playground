package com.kocsistem.ms.queue.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kocsistem.ms.domain.enums.PaymentAction;
import com.kocsistem.ms.queue.event.PaymentEvent;
import com.kocsistem.ms.service.DeliveryService;

@Component
public class PaymentSucceededEventListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentSucceededEventListener.class);

	@Autowired
	private DeliveryService deliveryService;

	@StreamListener("delivery-input-from-payment")
	public void paymentScceededEventHandler(@Payload PaymentEvent paymentEventPayload) {

		if (PaymentAction.PAYMENT_SUCCEEDED.equals(paymentEventPayload.getAction())) {
			LOGGER.info("PAYMENT_SUCCEEDED event received for Order {} with payment confirmation Id {}",
					paymentEventPayload.getOrderId(), paymentEventPayload.getPaymentConfirmationId());

			deliveryService.deliverTheProductsfOrder(paymentEventPayload.getOrderId(),paymentEventPayload.getUseCaseNoToBeRun());
		}

	}
}
