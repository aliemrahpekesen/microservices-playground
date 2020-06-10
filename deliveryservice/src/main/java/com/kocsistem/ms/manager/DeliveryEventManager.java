package com.kocsistem.ms.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.kocsistem.ms.domain.enums.DeliveryAction;
import com.kocsistem.ms.queue.channel.DeliveryChannel;
import com.kocsistem.ms.queue.event.DeliveryEvent;

@Component
public class DeliveryEventManager {

	@Autowired
	private DeliveryChannel deliveryChannel;

	public void publishDeliverySucceededEvent(long orderId) {
		MessageChannel messageChannel = deliveryChannel.deliveryOutput();

		messageChannel.send(MessageBuilder
				.withPayload(DeliveryEvent.builder().orderId(orderId).action(DeliveryAction.DELIVERY_SUCCEEDED).build())
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}

	public void publishDeliveryFailedEvent(long orderId) {
		MessageChannel messageChannel = deliveryChannel.deliveryOutput();

		messageChannel.send(MessageBuilder
				.withPayload(DeliveryEvent.builder().orderId(orderId).action(DeliveryAction.DELIVERY_FAILED).build())
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}

}
