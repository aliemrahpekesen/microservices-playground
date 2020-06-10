package com.kocsistem.ms.service;

public interface PaymentService {

	boolean tryPayment(long orderId,int useCaseNo);
	
	void manageSuccessPayment(long orderId,long paymentConfirmationId, int useCaseNoToBeRun);
	
	void manageNotSuccessPayment(long orderId, int useCaseNoToBeRun);
}
