package com.kocsistem.ms.queue.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface DeliveryChannel {

	@Input("delivery-input-from-payment")
	SubscribableChannel deliveryInputFromPayment();

	@Output("delivery-output")
	MessageChannel deliveryOutput();

}
