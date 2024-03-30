package com.vidring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidring.model.VidringSubscriptionModel;

public interface VidringSubRepo extends JpaRepository<VidringSubscriptionModel, Long> {

	Optional<VidringSubscriptionModel> findByMsisdn(String msisdn);

}
