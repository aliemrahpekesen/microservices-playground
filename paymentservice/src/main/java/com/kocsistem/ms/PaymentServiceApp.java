package com.kocsistem.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class PaymentServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApp.class, args);
	}
}
