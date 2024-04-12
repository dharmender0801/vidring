package com.vidring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidring.model.VidringProductModel;

public interface VidringProductRepo extends JpaRepository<VidringProductModel, Long> {

	VidringProductModel findByProductId(String productId);

	VidringProductModel findByOfferCode(String productId);

	List<VidringProductModel> findByCountryCode(String countryCode);

}
