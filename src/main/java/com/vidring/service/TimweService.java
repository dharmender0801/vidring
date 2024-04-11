package com.vidring.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nigeria.restTemplateRequest.UaeUnsubHttpRequest;
import com.vidring.model.VidringPartnerModel;
import com.vidring.model.VidringProductModel;
import com.vidring.model.VidringSubscriptionModel;
import com.vidring.repository.VidringPartnerRepo;
import com.vidring.repository.VidringProductRepo;
import com.vidring.request.NotificationRequest;
import com.vidring.request.PinPushRequest;
import com.vidring.response.StatusResponse;
import com.vidring.restTemplateRequests.subscriptionOptinRequest;
import com.vidring.restTemplateResponse.SubscriptonOptinResponse;
import com.vidring.util.ConstantManager;
import com.vidring.util.DBUtil;
import com.vidring.util.HttpUtil;
import com.vidring.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TimweService {

	@Autowired
	private DBUtil dbUtil;
	@Autowired
	private VidringProductRepo productRepo;
	@Autowired
	private VidringPartnerRepo partnerRepo;
	@Autowired
	RestTemplate restTemplate;

	public StatusResponse sendPinPushRequest(PinPushRequest pinPushRequest) {

		// TODO Auto-generated method stub
		try {
			VidringProductModel productModel = productRepo.findByProductId(pinPushRequest.getProductId());
			if (Boolean.TRUE.equals(Objects.nonNull(productModel))) {
				VidringPartnerModel partnerModel = partnerRepo
						.findByCountryCodeAndOperatorId(productModel.getCountryCode(), productModel.getOperatorId());
				if (Boolean.TRUE.equals(Objects.nonNull(partnerModel))) {
					long transactionId = (long) (Math.random() * 100000000000000L);
					subscriptionOptinRequest optinRequest = new subscriptionOptinRequest();
					optinRequest.setUserIdentifier(pinPushRequest.getMsisdn());
					optinRequest.setClientIp("Tst");
					optinRequest.setLargeAccount("1936");
					optinRequest.setCampaignUrl(productModel.getCampaign());
					optinRequest.setSubKeyword(productModel.getSubKeyword());
					optinRequest.setEntryChannel(pinPushRequest.getChannel());
					optinRequest.setTrackingId(String.valueOf(transactionId));
					optinRequest.setMcc(productModel.getMcc());
					optinRequest.setMnc(productModel.getMnc());
					optinRequest.setProductId(productModel.getOfferCode());

					String endPoint = partnerModel.getEndPoint();
					HttpHeaders headers = new HttpHeaders();
					headers.set("apikey", "EtisalatTesting1111");
					HttpEntity<subscriptionOptinRequest> requestEntity = new HttpEntity<>(optinRequest, headers);
					ResponseEntity<SubscriptonOptinResponse> responseEntity = restTemplate.exchange(endPoint,
							HttpMethod.POST, requestEntity, SubscriptonOptinResponse.class);
					log.info("Timwe Pin Push Request  ::::  {} ", optinRequest);
					log.info("Timwe Pin Push Response ::::  {} ", responseEntity);
					SubscriptonOptinResponse httpResponse = responseEntity.getBody();
					dbUtil.saveSubscriptionRequest(pinPushRequest.getMsisdn(), String.valueOf(transactionId),
							productModel, Utils.classToJsonConvert(optinRequest),
							Utils.classToJsonConvert(httpResponse));
					return ConstantManager.getSuccess();
				} else {
					return ConstantManager.getPartnerNotFound();
				}

			} else {
				return ConstantManager.getProductNotFound();
			}
		} catch (Exception w) {
			return ConstantManager.getInternalServerError();
		}

	}

	public StatusResponse handleNotification(NotificationRequest notificationRequest, String roleId, String action) {
		// TODO Auto-generated method stub
		log.info("Test : {} ", notificationRequest);
		try {
			if (Boolean.TRUE.equals(action.equalsIgnoreCase("sub"))) {
				VidringSubscriptionModel model = dbUtil.saveUserSubscription(notificationRequest, "Web");
				dbUtil.updateUserSubscription(notificationRequest.getMsisdn());
			} else if (Boolean.TRUE.equals(action.equalsIgnoreCase("ren"))) {
				dbUtil.updateUserSubscription(notificationRequest.getMsisdn());
			} else if (Boolean.TRUE.equals(action.equalsIgnoreCase("unsub"))) {
				dbUtil.unSubscribeUser(notificationRequest.getMsisdn());
			} else if (Boolean.TRUE.equals(action.equalsIgnoreCase("mt"))) {

			} else if (Boolean.TRUE.equals(action.equalsIgnoreCase("mo"))) {

			}
		} catch (Exception e) {
			return ConstantManager.getInternalServerError();
		}
		return ConstantManager.getSuccess();
	}

}
