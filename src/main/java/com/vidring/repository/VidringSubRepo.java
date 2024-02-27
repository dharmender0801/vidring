package com.vidring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidring.model.VidringSubscriptionModel;

public interface VidringSubRepo extends JpaRepository<VidringSubscriptionModel, Long> {

	VidringSubscriptionModel findByMsisdn(String msisdn);

}
