package com.vidring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vidring_product_config")
@Data
public class VidringProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String countryCode;
	@Column
	private String country;
	@Column
	private String pricePoint;
	@Column
	private String packType;
	@Column
	private String productId;
	@Column
	private Integer validity;
	@Column
	private String language;
	@Column
	private String currency;
	@Column
	private String offerCode;
	@Column
	private String operatorId;
	@Column
	private String operatorName;
	@Column
	private String keyWord;
	@Column
	private String mcc;
	@Column
	private String mnc;
	@Column
	private String campaign;
}
