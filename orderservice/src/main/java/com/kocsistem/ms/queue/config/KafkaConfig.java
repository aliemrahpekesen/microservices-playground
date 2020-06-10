package com.kocsistem.ms.queue.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

import com.kocsistem.ms.queue.channel.OrderChannel;

@EnableBinding(OrderChannel.class)
public class KafkaConfig {

}
