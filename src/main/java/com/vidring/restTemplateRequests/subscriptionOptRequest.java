package com.vidring.restTemplateRequests;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class subscriptionOptRequest {

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
	private String transactionAuthCode;
	private Integer subId;
	private String controlKeyword;
	private String controlServiceKeyword;
	private Integer cancelReason;
	private Integer cancelSource;

}
