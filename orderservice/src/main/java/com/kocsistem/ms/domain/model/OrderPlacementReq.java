package com.kocsistem.ms.domain.model;

import lombok.Data;

@Data
public class OrderPlacementReq {

	private long itemId;
	private long customerId;
	private int useCaseNo;

}
