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

import com.vidring.dto.CountryMasterDto;
import com.vidring.dto.VidringPartnerDto;
import com.vidring.enums.DeviceType;
import com.vidring.service.CountryService;
import com.vidring.util.Constants;
import com.vidring.util.response.RestResponse;
import com.vidring.util.response.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/country")
@Api(value = "REST API for Country Services", tags = { "Country Controller" })
@Slf4j
public class CountryController {
	
	@Autowired
	private CountryService countryService;

	@ApiOperation(value = "Add Country ", response = CountryMasterDto.class, httpMethod = "POST", notes = "This API add new country of updates details about the current.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok", response = CountryMasterDto.class),
			@ApiResponse(code = 401, message = "Not Authorized"),
			@ApiResponse(code = 403, message = "Not Authenticated"), @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(path = "/addCountryDetails", produces = "application/json")
	public ResponseEntity<RestResponse<CountryMasterDto>> addBannerDetails(
			@RequestHeader(name = Constants.DEVICE_TYPE, required = false) DeviceType deviceType,
			@RequestHeader(name = Constants.APP_VERSION, required = false) String appVersion,
			@RequestBody CountryMasterDto countryMasterDto) {
		log.debug("Partner Requested is: {}", countryMasterDto);
		countryMasterDto = countryService.addCountryDetails(countryMasterDto);
		return (Boolean.TRUE.equals(Objects.nonNull(countryMasterDto)))
				? RestUtils.successResponse(countryMasterDto, Constants.SUCCESS, HttpStatus.OK)
				: RestUtils.errorResponse(null, Constants.FAIL, HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Get Multiple Country", response = CountryMasterDto.class, httpMethod = "GET", notes = "")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "ok", response = CountryMasterDto.class, responseContainer = "LIS"),
			@ApiResponse(code = 401, message = "Not Authorized"),
			@ApiResponse(code = 403, message = "Not Authenticated"), @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@GetMapping(path = "/getCountryData", produces = "application/json")
	public ResponseEntity<RestResponse<List<CountryMasterDto>>> getData(
			@RequestHeader(name = Constants.DEVICE_TYPE) DeviceType deviceType,
			@RequestHeader(name = Constants.APP_VERSION) String appVersion) throws Exception {
		List<CountryMasterDto> data = countryService.getCountryData();
		return (Boolean.TRUE.equals(Objects.nonNull(data)))
				? RestUtils.successResponse(data, Constants.SUCCESS, HttpStatus.OK)
				: RestUtils.errorResponse(null, Constants.FAIL, HttpStatus.NOT_FOUND);
	}

}
