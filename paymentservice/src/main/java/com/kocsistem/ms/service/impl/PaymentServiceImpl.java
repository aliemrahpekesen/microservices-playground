package com.kocsistem.ms.service.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kocsistem.ms.manager.PaymentEventManager;
import com.kocsistem.ms.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);
	private Random random = new Random();

	@Autowired
	private PaymentEventManager paymentEventManager;

	@Override
	public boolean tryPayment(long orderId, int useCaseNo) {
		boolean paymentResult = false;

		switch (useCaseNo) {
		case 1:
			paymentResult = true;
			break;
		case 2:
			paymentResult = false;
			break;
		case 3:
			paymentResult = true;
			break;
		default:
			break;
		}

		try {
			Thread.sleep(10000);
			LOGGER.info("Payment of {} order is ", orderId,
					paymentResult == true ? "successfuly completed." : "failed.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return paymentResult;
	}

	@Override
	public void manageSuccessPayment(long orderId, long paymentConfirmationId, int useCaseNoToBeRun) {
		paymentEventManager.publishPaymentSucceededEvent(orderId, paymentConfirmationId, useCaseNoToBeRun);
	}

	@Override
	public void manageNotSuccessPayment(long orderId, int useCaseNoToBeRun) {
		paymentEventManager.publishPaymentNotSucceededEvent(orderId, useCaseNoToBeRun);

	}

}
