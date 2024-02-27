package com.vidring.response;

import lombok.Data;

@Data
public class StatusResponse {

	private Integer statusCode;
	private String statusDescription;
}
