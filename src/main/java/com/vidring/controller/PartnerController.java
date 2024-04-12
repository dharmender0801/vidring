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
import org.springframework.web.bind.annotation.RestController;

import com.vidring.dto.VidringPartnerDto;
import com.vidring.dto.VidringProductDto;
import com.vidring.enums.DeviceType;
import com.vidring.service.PartnerService;
import com.vidring.util.Constants;
import com.vidring.util.response.RestResponse;
import com.vidring.util.response.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/partner")
@Api(value = "REST API for Partner Details Services", tags = { "Partner Controller" })
@Slf4j
public class PartnerController {
	@Autowired
	private PartnerService partnerService;

	@ApiOperation(value = "Add Partner ", response = VidringPartnerDto.class, httpMethod = "POST", notes = "This API add new Partner of updates details about the current.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok", response = VidringPartnerDto.class),
			@ApiResponse(code = 401, message = "Not Authorized"),
			@ApiResponse(code = 403, message = "Not Authenticated"), @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(path = "/addPartnerDetails", produces = "application/json")
	public ResponseEntity<RestResponse<VidringPartnerDto>> addBannerDetails(
			@RequestHeader(name = Constants.DEVICE_TYPE, required = false) DeviceType deviceType,
			@RequestHeader(name = Constants.APP_VERSION, required = false) String appVersion,
			@RequestBody VidringPartnerDto partnerDto) {
		log.debug("Partner Requested is: {}", partnerDto);
		partnerDto = partnerService.addProductDetails(partnerDto);
		return (Boolean.TRUE.equals(Objects.nonNull(partnerDto)))
				? RestUtils.successResponse(partnerDto, Constants.SUCCESS, HttpStatus.OK)
				: RestUtils.errorResponse(null, Constants.FAIL, HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Get Multiple Partner", response = VidringPartnerDto.class, httpMethod = "GET", notes = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ok", response = VidringPartnerDto.class, responseContainer = "LIS"),
			@ApiResponse(code = 401, message = "Not Authorized"),
			@ApiResponse(code = 403, message = "Not Authenticated"), @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(path = "/getPartnerData", produces = "application/json")
	public ResponseEntity<RestResponse<List<VidringPartnerDto>>> getData(
			@RequestHeader(name = Constants.DEVICE_TYPE) DeviceType deviceType,
			@RequestHeader(name = Constants.APP_VERSION) String appVersion) throws Exception {
		List<VidringPartnerDto> data = partnerService.geProductData();
		return (Boolean.TRUE.equals(Objects.nonNull(data)))
				? RestUtils.successResponse(data, Constants.SUCCESS, HttpStatus.OK)
				: RestUtils.errorResponse(null, Constants.FAIL, HttpStatus.NOT_FOUND);
	}
	
	
	
	
	

}
