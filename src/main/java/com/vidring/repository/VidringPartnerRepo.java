package com.vidring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidring.model.VidringPartnerModel;

public interface VidringPartnerRepo extends JpaRepository<VidringPartnerModel, Long> {

	VidringPartnerModel findByCountryCodeAndOperatorId(String countryCode, String operatorId);

}
