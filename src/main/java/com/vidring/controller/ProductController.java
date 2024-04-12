package com.vidring.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vidring.dto.VidringProductDto;
import com.vidring.enums.DeviceType;
import com.vidring.service.ProductService;
import com.vidring.util.Constants;
import com.vidring.util.response.RestResponse;
import com.vidring.util.response.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product")
@Api(value = "REST API for Product Details Services", tags = { "Product Controller" })
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;

	@ApiOperation(value = "Get Multiple Product", response = VidringProductDto.class, httpMethod = "GET", notes = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ok", response = VidringProductDto.class, responseContainer = "LIS"),
			@ApiResponse(code = 401, message = "Not Authorized"),
			@ApiResponse(code = 403, message = "Not Authenticated"), @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(path = "/getData", produces = "application/json")
	public ResponseEntity<RestResponse<List<VidringProductDto>>> getData(
			@RequestHeader(name = Constants.DEVICE_TYPE) DeviceType deviceType,
			@RequestHeader(name = Constants.APP_VERSION) String appVersion) throws Exception {
		List<VidringProductDto> data = productService.geProductData();
		return (Boolean.TRUE.equals(Objects.nonNull(data)))
				? RestUtils.successResponse(data, Constants.SUCCESS, HttpStatus.OK)
				: RestUtils.errorResponse(null, Constants.FAIL, HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Add Product ", response = VidringProductDto.class, httpMethod = "POST", notes = "This API creates new Banner of updates details about the current sections.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok", response = VidringProductDto.class),
			@ApiResponse(code = 401, message = "Not Authorized"),
			@ApiResponse(code = 403, message = "Not Authenticated"), @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(path = "/addProductDetails", produces = "application/json")
	public ResponseEntity<RestResponse<VidringProductDto>> addBannerDetails(
			@RequestHeader(name = Constants.DEVICE_TYPE, required = false) DeviceType deviceType,
			@RequestHeader(name = Constants.APP_VERSION, required = false) String appVersion,
			@RequestBody VidringProductDto productDto) {
		log.debug("Product Requested is: {}", productDto);
		productDto = productService.addProductDetails(productDto);
		return (Boolean.TRUE.equals(Objects.nonNull(productDto)))
				? RestUtils.successResponse(productDto, Constants.SUCCESS, HttpStatus.OK)
				: RestUtils.errorResponse(null, Constants.FAIL, HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Get Multiple Product", response = VidringProductDto.class, httpMethod = "GET", notes = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ok", response = VidringProductDto.class, responseContainer = "LIS"),
			@ApiResponse(code = 401, message = "Not Authorized"),
			@ApiResponse(code = 403, message = "Not Authenticated"), @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(path = "/getProductByCountry", produces = "application/json")
	public ResponseEntity<RestResponse<List<VidringProductDto>>> getProductByCountry(
			@RequestHeader(name = Constants.DEVICE_TYPE) DeviceType deviceType,
			@RequestHeader(name = Constants.APP_VERSION) String appVersion,
			@RequestParam(name = "countryCode") String countryCode) throws Exception {
		List<VidringProductDto> data = productService.geProductDataByCountryCode(countryCode);
		return (Boolean.TRUE.equals(Objects.nonNull(data)))
				? RestUtils.successResponse(data, Constants.SUCCESS, HttpStatus.OK)
				: RestUtils.errorResponse(null, Constants.FAIL, HttpStatus.NOT_FOUND);
	}

}
