package com.vidring.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vidring.dto.UserSubscriptionDto;
import com.vidring.model.VidringPartnerModel;
import com.vidring.model.VidringProductModel;
import com.vidring.model.VidringSubscriptionModel;
import com.vidring.repository.VidringPartnerRepo;
import com.vidring.repository.VidringProductRepo;
import com.vidring.repository.VidringSubRepo;
import com.vidring.repository.VidringSubscriptionRequestRepo;
import com.vidring.response.StatusResponse;
import com.vidring.restTemplateRequests.subscriptionOptRequest;
import com.vidring.restTemplateResponse.SubscriptonOptinResponse;
import com.vidring.util.ConstantManager;
import com.vidring.util.DBUtil;
import com.vidring.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubscriptionService {

	@Autowired
	private VidringSubRepo vidringSubRepo;
	@Autowired
	private DBUtil dbUtil;
	@Autowired
	private VidringProductRepo productRepo;
	@Autowired
	private VidringPartnerRepo partnerRepo;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private VidringSubscriptionRequestRepo subscriptionRequestRepo;

	public StatusResponse SubscribeUser(UserSubscriptionDto subscriptionDto) {
		// TODO Auto-generated method stub
		VidringSubscriptionModel subscriptionModel = vidringSubRepo.findByMsisdn(subscriptionDto.getMsisdn()).get();
		if (Boolean.FALSE.equals(Objects.nonNull(subscriptionModel))) {
			switch (subscriptionDto.getOperatorId()) {
			case "101":
				return sendPinPushRequestForTimwe(subscriptionDto);
			default:
				break;
			}

		}

		return null;
	}

	public StatusResponse sendPinPushRequestForTimwe(UserSubscriptionDto pinPushRequest) {
		try {
			VidringProductModel productModel = productRepo.findByProductId(pinPushRequest.getProductId());
			if (Boolean.TRUE.equals(Objects.nonNull(productModel))) {
				VidringPartnerModel partnerModel = partnerRepo
						.findByCountryCodeAndOperatorId(productModel.getCountryCode(), productModel.getOperatorId());
				if (Boolean.TRUE.equals(Objects.nonNull(partnerModel))) {
					long transactionId = (long) (Math.random() * 100000000000000L);
					subscriptionOptRequest optinRequest = new subscriptionOptRequest();
					optinRequest.setUserIdentifier(pinPushRequest.getMsisdn());
					optinRequest.setClientIp("203.190.154.20");
					optinRequest.setUserIdentifierType("MSISDN");
					optinRequest.setLargeAccount("1936");
					optinRequest.setCampaignUrl(productModel.getCampaign());
					optinRequest.setSubKeyword(productModel.getSubKeyword());
					optinRequest.setEntryChannel("WEB");
					optinRequest.setTrackingId(String.valueOf(transactionId));
					optinRequest.setMcc(productModel.getMcc());
					optinRequest.setMnc(productModel.getMnc());
					optinRequest.setProductId(productModel.getOfferCode());
					String endPoint = partnerModel.getEndPoint();
					log.info("Timwe pin Push Push end point :{} ", endPoint);
					log.info("Timwe Pin Push Request  :  {} ", Utils.classToJsonConvert(optinRequest));
					HttpHeaders headers = new HttpHeaders();
					String auth = Utils.TimweEncryption("708", partnerModel.getUserName());
					headers.set("apikey", partnerModel.getPassword());
					headers.set("external-tx-id", String.valueOf(transactionId));
					headers.set("authentication", auth);
					headers.set("Content-Type", "application/json");
					headers.set("accept", "application/json");
					HttpEntity<subscriptionOptRequest> requestEntity = new HttpEntity<>(optinRequest, headers);
					ResponseEntity<SubscriptonOptinResponse> responseEntity = restTemplate.exchange(endPoint,
							HttpMethod.POST, requestEntity, SubscriptonOptinResponse.class);
					SubscriptonOptinResponse httpResponse = responseEntity.getBody();
					log.info("Timwe Pin Push Response :  {} ", Utils.classToJsonConvert(httpResponse));
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

}
