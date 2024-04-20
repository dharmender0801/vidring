package com.vidring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidring.model.CountryMaster;

public interface CountryMasterRepo extends JpaRepository<CountryMaster, Long> {

}
