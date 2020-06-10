package com.kocsistem.ms.queue.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

import com.kocsistem.ms.queue.channel.PaymentChannel;

@EnableBinding(PaymentChannel.class)
public class KafkaConfig {

}
