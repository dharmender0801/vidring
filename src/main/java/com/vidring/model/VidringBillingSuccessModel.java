package com.vidring.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "vidring_billing_success")
@Data
@ToString
public class VidringBillingSuccessModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String msisdn;
	@Column
	private String countryCode;
	@Column
	private String deductedAmount;
	@Column
	private Date processDatetime;
	@Column
	private String typeEvent;
	@Column
	private Date subscriptionDate;
	@Column
	private String operatorId;
	@Column
	private String productId;
	@Column
	private String threadId;

}
