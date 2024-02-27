package com.vidring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidring.model.VidringSubscriptionRequestModel;

public interface VidringSubscriptionRequestRepo extends JpaRepository<VidringSubscriptionRequestModel, Long> {

	VidringSubscriptionRequestModel findByTransactionId(String billingId);

}
