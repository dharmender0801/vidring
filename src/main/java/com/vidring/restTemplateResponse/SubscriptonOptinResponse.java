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

}
