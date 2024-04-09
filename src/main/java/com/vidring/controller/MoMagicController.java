package com.vidring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vidring.request.subscriptionRequest;
import com.vidring.response.StatusResponse;
import com.vidring.response.statusResponseWithUrl;
import com.vidring.service.MoMagicService;

@RestController
@RequestMapping("/mo-magic/")
public class MoMagicController {
	@Autowired
	private MoMagicService magicService;

	@GetMapping("/v1/test")
	public String testMethod() {
		return "Test Method";
	}

	@RequestMapping(value = "v1/notification", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<StatusResponse> notficationHandler(
			@RequestParam(name = "msisdn", required = false, defaultValue = "0") String msisdn,
			@RequestParam(name = "operator", required = false, defaultValue = "0") String operator,
			@RequestParam(name = "shortcode", required = false, defaultValue = "0") String shortcode,
			@RequestParam(name = "billing_id", required = false, defaultValue = "0") String billingId,
			@RequestParam(name = "tariff", required = false, defaultValue = "0") String tariff,
			@RequestParam(name = "status", required = false, defaultValue = "0") String status,
			@RequestParam(name = "event", required = false, defaultValue = "0") String event,
			@RequestParam(name = "fail_reason", required = false, defaultValue = "") String failResaon) {
		return new ResponseEntity<StatusResponse>(magicService.handleNotification(msisdn, operator, shortcode,
				billingId, tariff, status, event, failResaon), HttpStatus.OK);
	}

	@PostMapping("v1/subscribe")
	public ResponseEntity<statusResponseWithUrl> SubscriptionRequest(@RequestBody subscriptionRequest request) {
		return new ResponseEntity<statusResponseWithUrl>(magicService.sendSubscriptionRequest(request), HttpStatus.OK);
	}

}
