package com.vidring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSubscriptionDto {
	private String msisdn;
	private String productId;
	private String channel;
	private String operatorId;
	private String otp;
	private String transactionId;
}
