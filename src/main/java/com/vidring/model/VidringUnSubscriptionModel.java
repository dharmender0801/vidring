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
import lombok.ToString;

@Entity
@Table(name = "vidring_users_unsubscription")
@Data
@ToString
public class VidringUnSubscriptionModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String msisdn;
	@Column
	private String chargeAmount;
	@Column
	private Date chargeDate;
	@Column
	private Date expiryDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private VidringProductModel productModel;
	@Column
	private Date subscriptionDate;
	@Column
	private Integer validity;
	@Column
	private Date unsubscriptionDate;
	

}
