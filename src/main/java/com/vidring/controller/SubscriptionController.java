package com.vidring.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidring.dto.UserSubscriptionDto;
import com.vidring.enums.DeviceType;
import com.vidring.response.StatusResponse;
import com.vidring.service.SubscriptionService;
import com.vidring.util.Constants;
import com.vidring.util.response.RestResponse;
import com.vidring.util.response.RestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/vidring")
@Api(value = "REST API for Subscription Of User for Vidring", tags = { "Subscription Controller" })
@Slf4j
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;

	@ApiOperation(value = "Subscribe User ", response = UserSubscriptionDto.class, httpMethod = "POST", notes = "This API Will Handled Subscription of User")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok", response = UserSubscriptionDto.class),
			@ApiResponse(code = 401, message = "Not Authorized"),
			@ApiResponse(code = 403, message = "Not Authenticated"), @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(path = "/v1/subscribe", produces = "application/json")
	public ResponseEntity<RestResponse<StatusResponse>> addSubscriptionofOUser(
			@RequestHeader(name = Constants.DEVICE_TYPE, required = false) DeviceType deviceType,
			@RequestHeader(name = Constants.APP_VERSION, required = false) String appVersion,
			@RequestBody UserSubscriptionDto subscriptionDto) {
		log.debug("Partner Requested is: {}", subscriptionDto);
		StatusResponse statusResponse = subscriptionService.SubscribeUser(subscriptionDto);
		return (Boolean.TRUE.equals(Objects.nonNull(statusResponse)))
				? RestUtils.successResponse(statusResponse, Constants.SUCCESS, HttpStatus.OK)
				: RestUtils.errorResponse(null, Constants.FAIL, HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Subscribe User ", response = UserSubscriptionDto.class, httpMethod = "POST", notes = "This API Will Handled Subscription of User")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok", response = UserSubscriptionDto.class),
			@ApiResponse(code = 401, message = "Not Authorized"),
			@ApiResponse(code = 403, message = "Not Authenticated"), @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@PostMapping(path = "/v1/subscribe/verify", produces = "application/json")
	public ResponseEntity<RestResponse<StatusResponse>> PinVerify(
			@RequestHeader(name = Constants.DEVICE_TYPE, required = false) DeviceType deviceType,
			@RequestHeader(name = Constants.APP_VERSION, required = false) String appVersion,
			@RequestBody UserSubscriptionDto subscriptionDto) throws Exception {
		log.debug("Partner Requested is: {}", subscriptionDto);
		StatusResponse statusResponse = subscriptionService.UserPinVerify(subscriptionDto);
		return (Boolean.TRUE.equals(Objects.nonNull(statusResponse)))
				? RestUtils.successResponse(statusResponse, Constants.SUCCESS, HttpStatus.OK)
				: RestUtils.errorResponse(null, Constants.FAIL, HttpStatus.NOT_FOUND);
	}

}
