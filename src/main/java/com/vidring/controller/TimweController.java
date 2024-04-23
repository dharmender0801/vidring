package com.vidring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidring.dto.UserSubscriptionDto;
import com.vidring.request.NotificationRequest;
import com.vidring.response.StatusResponse;
import com.vidring.service.TimweService;

@RestController
@RequestMapping("/at-timwe/")
public class TimweController {

	@Autowired
	private TimweService timweService;

	@GetMapping("test")
	public String test() {
		return "This is testing";
	}

	@PostMapping("v1/pin-push")
	public ResponseEntity<StatusResponse> pinPush(@RequestBody UserSubscriptionDto pinPushRequest) {
		StatusResponse response = timweService.sendPinPushRequest(pinPushRequest);
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response, HttpStatus.OK)
				: new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	}

	@PostMapping("v1/pin-verify")
	public ResponseEntity<StatusResponse> pinVerify(@RequestBody UserSubscriptionDto subDto) throws Exception {
		StatusResponse response = timweService.sendPinVerify(subDto);
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response, HttpStatus.OK)
				: new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	}

	@PostMapping("/v1/notification/mo/{partnerRole}")
	public ResponseEntity<StatusResponse> moNotification(@PathVariable("partnerRole") String roleId,
			@RequestBody NotificationRequest notificationRequest) {
		StatusResponse response = timweService.handleNotification(notificationRequest, roleId, "mo");
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response, HttpStatus.OK)
				: new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	}

	@PostMapping("/v1/notification/mt/dn/{partnerRole}")
	public ResponseEntity<StatusResponse> mtNotification(@PathVariable("partnerRole") String roleId,
			@RequestBody NotificationRequest notificationRequest) {
		StatusResponse response = timweService.handleNotification(notificationRequest, roleId, "mt");
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response, HttpStatus.OK)
				: new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	}

	@PostMapping("/v1/notification/user-optin/{partnerRole}")
	public ResponseEntity<StatusResponse> SubNotification(@PathVariable("partnerRole") String roleId,
			@RequestBody NotificationRequest notificationRequest) {
		StatusResponse response = timweService.handleNotification(notificationRequest, roleId, "sub");
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response, HttpStatus.OK)
				: new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	}

	@PostMapping("/v1/notification/user-renewed/{partnerRole}")
	public ResponseEntity<StatusResponse> RenNotification(@PathVariable("partnerRole") String roleId,
			@RequestBody NotificationRequest notificationRequest) {
		StatusResponse response = timweService.handleNotification(notificationRequest, roleId, "ren");
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response, HttpStatus.OK)
				: new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	}

	@PostMapping("/v1/notification/user-optout/{partnerRole}")
	public ResponseEntity<StatusResponse> unsubNotification(@PathVariable("partnerRole") String roleId,
			@RequestBody NotificationRequest notificationRequest) {
		StatusResponse response = timweService.handleNotification(notificationRequest, roleId, "unsub");
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response, HttpStatus.OK)
				: new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	}

	@PostMapping("/v1/notification/charge/{partnerRole}")
	public ResponseEntity<StatusResponse> ChargeNotification(@PathVariable("partnerRole") String roleId,
			@RequestBody NotificationRequest notificationRequest) {
		StatusResponse response = timweService.handleNotification(notificationRequest, roleId, "sub");
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response, HttpStatus.OK)
				: new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	}
}
