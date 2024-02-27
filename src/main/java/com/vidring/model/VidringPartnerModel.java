package com.vidring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "vidring_partner_config")
@Data
public class VidringPartnerModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String countryCode;
	@Column
	private String operatorId;
	@Column
	private String userName;
	@Column
	private String password;
	@Column
	private String endPoint;
	@Column
	private String requestBody;
	@Column
	private String successUrl;
	@Column
	private String denyUrl;
	@Column
	private String errorUrl;

}
