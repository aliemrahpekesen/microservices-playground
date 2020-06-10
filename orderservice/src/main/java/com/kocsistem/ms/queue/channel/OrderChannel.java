package com.kocsistem.ms.queue.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderChannel {

	@Output("order-output")
	MessageChannel orderOutput();

	@Input("order-input-from-payment")
	SubscribableChannel orderInputFromPayment();

	@Input("order-input-from-delivery")
	SubscribableChannel orderInputFromdelivery();

}
