package com.vidring.service;

import java.util.ArrayList;
import java.util.List;

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
		List<VidringProductDto> dtos = new ArrayList<>();
		List<VidringProductModel> productModel = productRepo.findAll();
		Utils.copyProperties(productModel, dtos);
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
