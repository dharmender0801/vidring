package com.vidring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidring.request.PinPushRequest;
import com.vidring.response.StatusResponse;
import com.vidring.service.TimweService;

@RestController
@RequestMapping("/api/at-timwe/")
public class TimweController {

	@Autowired
	private TimweService timweService;

	@GetMapping("test")
	public String test() {
		return "This is testing";
	}

	@PostMapping("v1/pin-push")
	public ResponseEntity<StatusResponse> pinPush(@RequestBody PinPushRequest pinPushRequest) {
		StatusResponse response = timweService.sendPinPushRequest(pinPushRequest);
		return response.getStatusCode() == 200 ? new ResponseEntity<>(response, HttpStatus.OK)
				: new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
	}
	
//	@PostMapping("/v1/notification")
	

}
