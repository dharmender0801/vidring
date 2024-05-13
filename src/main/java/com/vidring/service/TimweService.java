package com.vidring.service;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.vidring.dto.UserSubscriptionDto;
import com.vidring.model.VidringBillingSuccessModel;
import com.vidring.model.VidringPartnerModel;
import com.vidring.model.VidringProductModel;
import com.vidring.model.VidringSubscriptionModel;
import com.vidring.model.VidringSubscriptionRequestModel;
import com.vidring.repository.VidringPartnerRepo;
import com.vidring.repository.VidringProductRepo;
import com.vidring.repository.VidringSubRepo;
import com.vidring.repository.VidringSubscriptionRequestRepo;
import com.vidring.request.NotificationRequest;
import com.vidring.request.PinPushRequest;
import com.vidring.response.StatusResponse;
import com.vidring.restTemplateRequests.subscriptionOptRequest;
import com.vidring.restTemplateResponse.SubscriptonOptinResponse;
import com.vidring.util.ConstantManager;
import com.vidring.util.DBUtil;
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
	@Autowired
	private VidringSubscriptionRequestRepo subscriptionRequestRepo;
	@Autowired
	private VidringSubRepo vidringSubRepo;

	public StatusResponse sendPinPushRequest(UserSubscriptionDto pinPushRequest) {

		// TODO Auto-generated method stub
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
					String auth = encrypt("708", partnerModel.getUserName());
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
					return new StatusResponse(200, "Success", 1, String.valueOf(transactionId), null);
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

	public StatusResponse sendPinVerify(UserSubscriptionDto pinPushRequest) throws Exception {
		// TODO Auto-generated method stub
		VidringSubscriptionRequestModel requestModel = subscriptionRequestRepo
				.findByTransactionId(pinPushRequest.getTransactionId());
		if (Boolean.TRUE.equals(Objects.nonNull(requestModel))) {
			VidringPartnerModel partnerModel = partnerRepo.findByCountryCodeAndOperatorId(
					requestModel.getProductModel().getCountryCode(), requestModel.getProductModel().getOperatorId());
			if (Boolean.TRUE.equals(Objects.nonNull(partnerModel))) {
				subscriptionOptRequest optinRequest = new subscriptionOptRequest();
				optinRequest.setUserIdentifier(pinPushRequest.getMsisdn());
				optinRequest.setClientIp("203.190.154.20");
				optinRequest.setUserIdentifierType("MSISDN");
				optinRequest.setSubKeyword(requestModel.getProductModel().getSubKeyword());
				optinRequest.setEntryChannel("WEB");
				optinRequest.setMcc(requestModel.getProductModel().getMcc());
				optinRequest.setMnc(requestModel.getProductModel().getMnc());
				optinRequest.setProductId(requestModel.getProductModel().getOfferCode());
				optinRequest.setTransactionAuthCode(pinPushRequest.getOtp());
				log.info("Timwe Pin verify Request  ::::  {} ", Utils.classToJsonConvert(optinRequest));
				long transactionId = (long) (Math.random() * 100000000000000L);
				HttpHeaders headers = new HttpHeaders();
				String auth = encrypt("708", partnerModel.getUserName());
				headers.set("apikey", partnerModel.getPassword());
				headers.set("external-tx-id", String.valueOf(transactionId));
				headers.set("authentication", auth);
				headers.set("Content-Type", "application/json");
				headers.set("accept", "application/json");
				HttpEntity<subscriptionOptRequest> requestEntity = new HttpEntity<>(optinRequest, headers);
				ResponseEntity<SubscriptonOptinResponse> responseEntity = restTemplate.exchange(
						"https://tigo.timwe.com/gh/ma/api/external/v1/subscription/optin/confirm/726", HttpMethod.POST,
						requestEntity, SubscriptonOptinResponse.class);
				SubscriptonOptinResponse httpResponse = responseEntity.getBody();
				log.info("Timwe Pin Verify Request  ::::  {} ", Utils.classToJsonConvert(httpResponse));
				requestModel.setPinVerifyRequest(Utils.classToJsonConvert(optinRequest));
				requestModel.setPinVerifyResponse(Utils.classToJsonConvert(httpResponse));
				requestModel.setPinVerificationDate(new Date());
				subscriptionRequestRepo.save(requestModel);

			}

			return ConstantManager.getSuccess();
		}
		return ConstantManager.TransNotFound();
	}

	public StatusResponse sendUnsubscriptionRequest(String msisdn) throws Exception {
		// TODO Auto-generated method stub
		VidringSubscriptionModel subModel = vidringSubRepo.findByMsisdn(msisdn).orElse(null);
		if (Boolean.TRUE.equals(Objects.nonNull(subModel))) {
			VidringPartnerModel partnerModel = partnerRepo.findByCountryCodeAndOperatorId(
					subModel.getProductModel().getCountryCode(), subModel.getProductModel().getOperatorId());
			if (Boolean.TRUE.equals(Objects.nonNull(partnerModel))) {
				long transactionId = (long) (Math.random() * 100000000000000L);
				subscriptionOptRequest optoutnRequest = new subscriptionOptRequest();
				optoutnRequest.setUserIdentifier(subModel.getMsisdn());
				optoutnRequest.setClientIp("203.190.154.20");
				optoutnRequest.setUserIdentifierType("MSISDN");
				optoutnRequest.setSubKeyword(subModel.getProductModel().getSubKeyword());
				optoutnRequest.setEntryChannel("WEB");
				optoutnRequest.setMcc(subModel.getProductModel().getMcc());
				optoutnRequest.setMnc(subModel.getProductModel().getMnc());
				optoutnRequest.setProductId(subModel.getProductModel().getOfferCode());
				optoutnRequest.setTrackingId(String.valueOf(transactionId));
				log.info("Timwe Unsubscription Request  ::::  {} ", Utils.classToJsonConvert(optoutnRequest));

				HttpHeaders headers = new HttpHeaders();
				String auth = encrypt("708", partnerModel.getUserName());
				headers.set("apikey", partnerModel.getPassword());
				headers.set("external-tx-id", String.valueOf(transactionId));
				headers.set("authentication", auth);
				headers.set("Content-Type", "application/json");
				headers.set("accept", "application/json");
				HttpEntity<subscriptionOptRequest> requestEntity = new HttpEntity<>(optoutnRequest, headers);
				ResponseEntity<SubscriptonOptinResponse> responseEntity = restTemplate.exchange(
						"https://tigo.timwe.com/gh/ma/api/external/v1/subscription/optout/726", HttpMethod.POST,
						requestEntity, SubscriptonOptinResponse.class);
				SubscriptonOptinResponse httpResponse = responseEntity.getBody();
				log.info("Timwe Unsubscription Response  ::::  {} ", Utils.classToJsonConvert(httpResponse));
			}

			return ConstantManager.getSuccess();
		}
		return ConstantManager.TransNotFound();
	}

	String encrypt(String Data, String preSharedKey) throws Exception {
		String phrasetoEncrypt = Data + "#" + System.currentTimeMillis();
		String encryptionAlgorithm = "AES/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
		SecretKeySpec key = new SecretKeySpec(preSharedKey.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		final byte[] crypted = cipher.doFinal(phrasetoEncrypt.getBytes());
		String encrypted = Base64.getEncoder().encodeToString(crypted);
		return encrypted;
	}

	public StatusResponse handleNotification(NotificationRequest notificationRequest, String roleId, String action) {
		// TODO Auto-generated method stub
		log.info("Test : {} ", notificationRequest);
		dbUtil.saveNotfication(notificationRequest.getUserIdentifier(), Utils.classToJsonConvert(notificationRequest),
				"233", "101");
		try {
			if (Boolean.TRUE.equals(action.equalsIgnoreCase("sub"))) {
				dbUtil.saveUserSubscription(notificationRequest, "WEB");
				dbUtil.updateUserSubscription(notificationRequest.getUserIdentifier());
			} else if (Boolean.TRUE.equals(action.equalsIgnoreCase("ren"))) {
				dbUtil.saveUserSubscription(notificationRequest, "WEB");
				dbUtil.updateUserSubscription(notificationRequest.getUserIdentifier());
			} else if (Boolean.TRUE.equals(action.equalsIgnoreCase("unsub"))) {
				dbUtil.unSubscribeUser(notificationRequest.getUserIdentifier());
			} else if (Boolean.TRUE.equals(action.equalsIgnoreCase("mt"))) {

			} else if (Boolean.TRUE.equals(action.equalsIgnoreCase("mo"))) {

			}
		} catch (Exception e) {
			return ConstantManager.getInternalServerError();
		}
		return ConstantManager.getSuccess();
	}

}
