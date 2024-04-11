package com.vidring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidring.dto.VidringPartnerDto;
import com.vidring.model.VidringPartnerModel;
import com.vidring.repository.VidringPartnerRepo;
import com.vidring.util.Utils;

@Service
public class PartnerService {

	@Autowired
	private VidringPartnerRepo partnerRepo;

	public VidringPartnerDto addProductDetails(VidringPartnerDto partnerDto) {
		// TODO Auto-generated method stub
		VidringPartnerModel partnerModel = new VidringPartnerModel();
		if (Boolean.TRUE.equals(partnerDto.getId() != null)) {
			partnerModel = partnerRepo.findById(partnerDto.getId()).orElse(new VidringPartnerModel());
		}
		Utils.copyProperties(partnerDto, partnerModel);
		partnerModel = partnerRepo.save(partnerModel);
		Utils.copyProperties(partnerModel, partnerDto);
		return partnerDto;
	}

	public List<VidringPartnerDto> geProductData() {
		// TODO Auto-generated method stub
		List<VidringPartnerModel> partnerList = partnerRepo.findAll();
		List<VidringPartnerDto> partnerDtos = partnerList.parallelStream().map(partner -> {
			VidringPartnerDto dto = new VidringPartnerDto();
			Utils.copyProperties(partner, dto);
			return dto;
		}).collect(Collectors.toList());
		return partnerDtos;
	}

}
