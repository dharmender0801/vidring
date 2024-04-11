package com.vidring.request;

import lombok.Data;

@Data
public class PinPushRequest {

	private String msisdn;
	private String productId;
	private String channel;

}
