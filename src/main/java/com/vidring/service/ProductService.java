package com.vidring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidring.dto.VidringProductDto;
import com.vidring.model.VidringProductModel;
import com.vidring.repository.VidringProductRepo;
import com.vidring.util.Utils;

@Service
public class ProductService {

	@Autowired
	private VidringProductRepo productRepo;

	public List<VidringProductDto> geProductData() {
		List<VidringProductDto> dtos = productRepo.findAll().stream().map(product -> {
			VidringProductDto dto = new VidringProductDto();
			Utils.copyProperties(product, dto);
			return dto;
		}).collect(Collectors.toList());
		return dtos;
	}

	public VidringProductDto addProductDetails(VidringProductDto productDto) {
		VidringProductModel productModel = new VidringProductModel();
		Utils.copyProperties(productDto, productModel);
		productModel = productRepo.save(productModel);
		Utils.copyProperties(productModel, productDto);
		return productDto;
	}

}