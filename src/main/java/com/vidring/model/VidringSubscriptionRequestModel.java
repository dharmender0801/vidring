package com.vidring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "vidring_subciption_request")
@Data
public class VidringSubscriptionRequestModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String msisdn;
	@Column
	private String transactionId;
	@Column
	private String countryCode;
	@Column
	private String operatorId;
	@Column
	private Date requestDate;
	@Column(columnDefinition = "2048")
	private String pinPushrequest;
	@Column(columnDefinition = "2048")
	private String pinPushresponse;
	@Column
	private Date pinVerificationDate;
	@Column(columnDefinition = "2048")
	private String pinVerifyRequest;
	@Column(columnDefinition = "2048")
	private String pinVerifyResponse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private VidringProductModel productModel;

}
