package com.kocsistem.ms.queue.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

import com.kocsistem.ms.queue.channel.DeliveryChannel;

@EnableBinding(DeliveryChannel.class)
public class KafkaConfig {

}
