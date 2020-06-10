package com.kocsistem.ms.service.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kocsistem.ms.manager.DeliveryEventManager;
import com.kocsistem.ms.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryServiceImpl.class);
	private Random random = new Random();

	@Autowired
	private DeliveryEventManager deliveryEventManager;

	@Override
	public boolean deliverTheProductsfOrder(long orderId, int useCaseNoToBeRun) {

		boolean deliveryResult = false;

		switch (useCaseNoToBeRun) {
		case 1:
			deliveryResult = true;
			break;
		case 2:
			deliveryResult = false;
			break;
		case 3:
			deliveryResult = false;
			break;
		default:
			break;
		}
		try {
			Thread.sleep(10000);
			LOGGER.info("Delivery of {} order is ", orderId,
					deliveryResult == true ? "successfuly completed." : "failed.");

			if (deliveryResult)
				deliveryEventManager.publishDeliverySucceededEvent(orderId);
			else
				deliveryEventManager.publishDeliveryFailedEvent(orderId);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return deliveryResult;

	}

}
