package com.kocsistem.ms.queue.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kocsistem.ms.domain.enums.DeliveryAction;
import com.kocsistem.ms.domain.enums.OrderStatus;
import com.kocsistem.ms.queue.event.DeliveryEvent;
import com.kocsistem.ms.service.OrderService;

@Component
public class DeliveryResultEventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryResultEventListener.class);

	@Autowired
	private OrderService orderService;

	@StreamListener("order-input-from-delivery")
	public void deliveryResultEventHandler(@Payload DeliveryEvent deliveryEventPayload) {

		if (DeliveryAction.DELIVERY_SUCCEEDED.equals(deliveryEventPayload.getAction())) {
			LOGGER.info("DELIVERY_SUCCEEDED event received for order {}", deliveryEventPayload.getOrderId());
			orderService.update(deliveryEventPayload.getOrderId(), OrderStatus.DELIVERED);
		} else if (DeliveryAction.DELIVERY_FAILED.equals(deliveryEventPayload.getAction())) {
			LOGGER.info("DELIVERY_FAILED event received for order {}.", deliveryEventPayload.getOrderId());
			orderService.update(deliveryEventPayload.getOrderId(), OrderStatus.NOT_DELIVERED);
		}

	}
}
