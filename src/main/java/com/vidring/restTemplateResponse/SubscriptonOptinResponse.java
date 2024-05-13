package com.vidring.restTemplateResponse;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubscriptonOptinResponse {

	private String message;
	private Boolean inError;
	private String requestId;
	private String code;
	private ResponseObject responseData;

	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public static class ResponseObject {
		private String transactionId;
		private String subscriptionResult;
		private String subscriptionError;
	}

}
