package com.vidring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidring.model.VidringProductModel;

public interface VidringProductRepo extends JpaRepository<VidringProductModel, Long> {

	VidringProductModel findByProductId(String productId);

	VidringProductModel findByMccAndMnc(String mcc, String mnc);

}
