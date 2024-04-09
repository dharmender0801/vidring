package com.vidring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class VidringProductDto {
	private Long id;
	private String countryCode;
	private String country;
	private String pricePoint;
	private String packType;
	private String productId;
	private Integer validity;
	private String language;
	private String currency;
	private String offerCode;
	private String operatorId;
	private String operatorName;
	private String keyWord;
	private String mcc;
	private String mnc;
	private String campaign;
	private String packName;
}
