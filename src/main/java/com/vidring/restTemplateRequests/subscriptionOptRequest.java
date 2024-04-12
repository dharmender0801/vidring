package com.vidring.restTemplateRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class subscriptionOptinRequest {

	private String userIdentifier;
	private String userIdentifierType;
	private String productId;
	private String mcc;
	private String mnc;
	private String entryChannel;
	private String largeAccount;
	private String subKeyword;
	private String trackingId;
	private String clientIp;
	private String campaignUrl;

}
