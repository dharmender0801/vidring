package com.vidring.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "partner_notifcation_logs")
@Data
public class PartnerNotificationModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String msisdn;
	@Column
	private String notification;
	@Column
	private String operatorId;
	@Column
	private String countryCode;
	@Column
	private Date dateTime;

}
