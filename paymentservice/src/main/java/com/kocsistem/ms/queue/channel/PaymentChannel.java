package com.kocsistem.ms.queue.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentChannel {
	
	@Input("payment-input-from-order")
	SubscribableChannel paymentInputFromOrder();
	
	@Output("payment-output")
	MessageChannel paymentOutput();

}
