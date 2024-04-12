package com.vidring.request;

import java.util.List;

import lombok.Data;

@Data
public class NotificationRequest {
	private String productId;
	private String pricepointId;
	private String mcc;
	private String mnc;
	private String text;
	private String userIdentifier;
	private String largeAccount;
	private String transactionUUID;
	private List<String> tags;
}
