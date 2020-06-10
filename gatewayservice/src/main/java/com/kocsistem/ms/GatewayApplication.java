package com.kocsistem.ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.kocsistem.ms.filter.AuthFilter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@RefreshScope
public class GatewayApplication {

	private static Logger LOG = LoggerFactory.getLogger(GatewayApplication.class);

	public static void main(String[] args) {
		LOG.info("Uygulama Başlatıldı");
		SpringApplication.run(GatewayApplication.class, args);
	}
	
	@Bean
	public AuthFilter preFilter() {
		return new AuthFilter();
	}
}
