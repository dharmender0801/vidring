package com.vidring.response;

import lombok.Data;

@Data
public class statusResponseWithUrl {
	private Integer statusCode;
	private String statusDescription;
	private String redirectionUrl;
}
