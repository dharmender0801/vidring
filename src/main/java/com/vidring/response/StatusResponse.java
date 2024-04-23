package com.vidring.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponse {

	private Integer statusCode;
	private String statusDescription;
	private Integer isPin;
	private String transactionId;
	private String redirectionUrl;
}
