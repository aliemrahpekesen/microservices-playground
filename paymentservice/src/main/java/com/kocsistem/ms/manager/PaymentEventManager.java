package com.kocsistem.ms.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.kocsistem.ms.domain.enums.PaymentAction;
import com.kocsistem.ms.queue.channel.PaymentChannel;
import com.kocsistem.ms.queue.event.PaymentEvent;

@Component
public class PaymentEventManager {

	@Autowired
	private PaymentChannel paymentChannel;

	public void publishPaymentSucceededEvent(long orderId, long paymentConfirmationId, int useCaseNoToBeRun) {
		MessageChannel messageChannel = paymentChannel.paymentOutput();
		messageChannel.send(MessageBuilder
				.withPayload(PaymentEvent.builder().useCaseNoToBeRun(useCaseNoToBeRun).orderId(orderId).action(PaymentAction.PAYMENT_SUCCEEDED)
						.paymentConfirmationId(paymentConfirmationId).build())
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}

	public void publishPaymentNotSucceededEvent(long orderId, int useCaseNoToBeRun) {
		MessageChannel messageChannel = paymentChannel.paymentOutput();
		messageChannel.send(MessageBuilder
				.withPayload(PaymentEvent.builder().useCaseNoToBeRun(useCaseNoToBeRun).orderId(orderId).action(PaymentAction.PAYMENT_FAILED)
						.paymentConfirmationId(-1).build())
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}
}
