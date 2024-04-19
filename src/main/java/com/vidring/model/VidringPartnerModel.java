package com.vidring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
	@Column
	private String partnerId;
	@Column
	private String unsubEndpoint;

}
