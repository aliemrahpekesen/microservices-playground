package com.kocsistem.ms.queue.listener;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kocsistem.ms.domain.enums.OrderAction;
import com.kocsistem.ms.queue.event.OrderEvent;
import com.kocsistem.ms.service.PaymentService;

@Component
public class OrderCreatedEventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCreatedEventListener.class);
	private Random random = new Random();

	@Autowired
	private PaymentService paymentService;

	@StreamListener("payment-input-from-order")
	public void orderCreatedEventHandler(@Payload OrderEvent orderEventPayload) {

		if (OrderAction.ORDER_CREATED.equals(orderEventPayload.getAction())) {
			LOGGER.info("ORDER_CREATED event received for Order {}.", orderEventPayload.getOrderId());
			LOGGER.info("Payment is going to be proceed for Order {}.", orderEventPayload.getOrderId());
			boolean paymentResult = paymentService.tryPayment(orderEventPayload.getOrderId(),
					orderEventPayload.getUseCaseToBeRun());
			if (paymentResult) {
				long paymentConfirmationRandomValue = Math.abs(random.nextLong());
				LOGGER.info("PAYMENT_SUCCEEDED event is published for order {} with payment confirmation Id {}",
						orderEventPayload.getOrderId(), paymentConfirmationRandomValue);
				paymentService.manageSuccessPayment(orderEventPayload.getOrderId(), paymentConfirmationRandomValue,
						orderEventPayload.getUseCaseToBeRun());
			} else {
				LOGGER.info("PAYMENT_NOT_SUCCEEDED event is published for order {}", orderEventPayload.getOrderId());
				paymentService.manageNotSuccessPayment(orderEventPayload.getOrderId(),
						orderEventPayload.getUseCaseToBeRun());
			}
		}

	}

}
