package com.kocsistem.ms.queue.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.kocsistem.ms.domain.enums.OrderAction;
import com.kocsistem.ms.queue.channel.OrderChannel;
import com.kocsistem.ms.queue.event.OrderEvent;

@Component
public class OrderPlacementEventManager {

	@Autowired
	private OrderChannel orderChannel;

	public void publishOrderPlacementEvent(long orderId,int useCaseNo) {
		MessageChannel messageChannel = orderChannel.orderOutput();
		messageChannel.send(MessageBuilder
				.withPayload(OrderEvent.builder().orderId(orderId).action(OrderAction.ORDER_CREATED).useCaseToBeRun(useCaseNo).build())
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}

}
