package com.vidring.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
