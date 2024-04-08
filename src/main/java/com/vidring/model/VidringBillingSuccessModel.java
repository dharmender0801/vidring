package com.vidring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
