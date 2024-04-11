package com.vidring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@NoArgsConstructor
public class VidringPartnerDto {

	private Long id;
	private String countryCode;
	private String operatorId;
	private String userName;
	private String password;
	private String endPoint;
	private String requestBody;
	private String successUrl;
	private String denyUrl;
	private String errorUrl;
	private String partnerId;
}
