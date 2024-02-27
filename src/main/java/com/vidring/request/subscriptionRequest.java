package com.vidring.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class subscriptionRequest {
	private String msisdn;
	private String productId;
	private String language;
}
