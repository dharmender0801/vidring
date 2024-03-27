package com.vidring.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidring.model.VidringPartnerModel;
import com.vidring.model.VidringProductModel;
import com.vidring.model.VidringSubscriptionModel;
import com.vidring.repository.VidringPartnerRepo;
import com.vidring.repository.VidringProductRepo;
import com.vidring.request.subscriptionRequest;
import com.vidring.response.StatusResponse;
import com.vidring.response.statusResponseWithUrl;
import com.vidring.util.ConstantManager;
import com.vidring.util.DBUtil;
import com.vidring.util.HttpUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MoMagicService {
	@Autowired
	private DBUtil dbUtil;
	@Autowired
	private VidringProductRepo productRepo;
	@Autowired
	private VidringPartnerRepo partnerRepo;

	@Value("${notificationRequest}")
	private String notificationRequest;

	public StatusResponse handleNotification(String msisdn, String operator, String shortcode, String billingId,
			String tariff, String status, String event, String failResaon) {
		// TODO Auto-generated method stub
		StatusResponse response = new StatusResponse();
		try {
			String notification = notificationRequest;
			notification = notification.replace("#msisdn#", msisdn);
			notification = notification.replace("#operator#", operator);
			notification = notification.replace("#shortcode#", shortcode);
			notification = notification.replace("#billing_id#", billingId);
			notification = notification.replace("#tariff#", tariff);
			notification = notification.replace("#status#", status);
			notification = notification.replace("#event#", event);
			notification = notification.replace("#fail_reason#", failResaon);
			dbUtil.saveNotfication(msisdn, notification, "880", "1");
			log.info("MoMagic Notification :::: {} ", notification);
			if (event.equalsIgnoreCase("subscription")) {
				VidringSubscriptionModel subModel = dbUtil.saveUserSubscription(msisdn, billingId, status);
				if (status.equalsIgnoreCase("success")) {
					dbUtil.saveToSuccessBilling(subModel, "sub");
				}
			} else if (event.equalsIgnoreCase("rebill")) {
				if (status.equalsIgnoreCase("success")) {
					dbUtil.updateUserSubscription(msisdn);
				}
			} else if (event.equalsIgnoreCase("unsubscription")) {
				dbUtil.unSubscribeUser(msisdn);
			}
			response = ConstantManager.getSuccess();
		} catch (Exception e) {
			response = ConstantManager.getInternalServerError();
		}

		return response;
	}

	public statusResponseWithUrl sendSubscriptionRequest(subscriptionRequest request) {
		// TODO Auto-generated method stub
		statusResponseWithUrl response = new statusResponseWithUrl();
		ObjectMapper mapper = new ObjectMapper();
		log.info(request.toString());
		try {
			VidringProductModel productModel = productRepo.findByProductId(request.getProductId());
			if (productModel != null) {
				VidringPartnerModel vidringPartnerModel = partnerRepo
						.findByCountryCodeAndOperatorId(productModel.getCountryCode(), productModel.getOperatorId());
				if (vidringPartnerModel != null) {
					long transactionId = (long) (Math.random() * 100000000000000L);
					String requestBody = vidringPartnerModel.getRequestBody();
					System.out.println("test");
					requestBody = requestBody.replace("{transactionId}", String.valueOf(transactionId));
					requestBody = requestBody.replace("{keyword}", productModel.getKeyWord());
					requestBody = requestBody.replace("{msisdn}", request.getMsisdn());
					requestBody = requestBody.replace("{amount}", productModel.getPricePoint());
					requestBody = requestBody.replace("{sucessUrl}", vidringPartnerModel.getSuccessUrl());
					requestBody = requestBody.replace("{denyUrl}", vidringPartnerModel.getDenyUrl());
					requestBody = requestBody.replace("{errorUrl}", vidringPartnerModel.getErrorUrl());
					String httpResponse =
//							"{ \"success\": true, \"status_code\": 200, \"redirect_url\": \"redirect_url_for_pin_submission\" }";
							HttpUtil.sendRequest(vidringPartnerModel.getEndPoint(), requestBody);
					log.info("Request  ::::  {} ", requestBody);
					log.info("Response ::::  {} ", httpResponse);
					dbUtil.saveSubscriptionRequest(request.getMsisdn(), String.valueOf(transactionId), productModel,
							requestBody, httpResponse);
					JsonNode reqNode = mapper.readTree(httpResponse);
					if (reqNode.get("success").asBoolean()) {
						BeanUtils.copyProperties(ConstantManager.getSuccess(), response);
						response.setRedirectionUrl(reqNode.get("redirect_url").asText());
					} else {
						BeanUtils.copyProperties(ConstantManager.getFailed(), response);
					}
				} else {
					BeanUtils.copyProperties(ConstantManager.getPartnerNotFound(), response);
				}
			} else {
				BeanUtils.copyProperties(ConstantManager.getProductNotFound(), response);
			}
		} catch (Exception e) {
			BeanUtils.copyProperties(ConstantManager.getInternalServerError(), response);
		}
		return response;
	}

}
