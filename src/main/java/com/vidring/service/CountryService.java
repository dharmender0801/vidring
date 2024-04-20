package com.vidring.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidring.dto.CountryMasterDto;
import com.vidring.model.CountryMaster;
import com.vidring.repository.CountryMasterRepo;
import com.vidring.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountryService {

	@Autowired
	private CountryMasterRepo countryMasterRepo;

	public CountryMasterDto addCountryDetails(CountryMasterDto countryMasterDto) {
		log.info("Country Request : {} ", countryMasterDto);
		CountryMaster countryMaster = new CountryMaster();
		if (Boolean.TRUE.equals(Objects.nonNull(countryMasterDto.getId()))) {
			countryMaster = countryMasterRepo.findById(countryMasterDto.getId()).orElse(new CountryMaster());
		}
		Utils.copyProperties(countryMasterDto, countryMaster);
		countryMaster = countryMasterRepo.save(countryMaster);
		Utils.copyProperties(countryMaster, countryMasterDto);
		return countryMasterDto;
	}

	public List<CountryMasterDto> getCountryData() {
		List<CountryMaster> country = countryMasterRepo.findAll();

		List<CountryMasterDto> masterDto = country.parallelStream().map(partner -> {
			CountryMasterDto dto = new CountryMasterDto();
			Utils.copyProperties(partner, dto);
			return dto;
		}).collect(Collectors.toList());
		return masterDto;
	}

}
