package com.vidring.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vidring.model.VidringProductModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VidringSubscriptionDto {
	private Long id;
	private String msisdn;
	private String chargeAmount;
	private Date chargeDate;
	private Date expiryDate;
	private String countryCode;
	private String operatorId;
	private VidringProductModel productModel;
	private Date subscriptionDate;
	private Integer validity;
	private String channel;
}
