package com.vidring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
