package com.vidring.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidring.model.VidringPartnerModel;
import com.vidring.model.VidringProductModel;
import com.vidring.model.VidringSubscriptionModel;
import com.vidring.repository.VidringPartnerRepo;
import com.vidring.repository.VidringProductRepo;
import com.vidring.request.NotificationRequest;
import com.vidring.request.PinPushRequest;
import com.vidring.response.StatusResponse;
import com.vidring.util.ConstantManager;
import com.vidring.util.DBUtil;
import com.vidring.util.HttpUtil;

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

	public StatusResponse sendPinPushRequest(PinPushRequest pinPushRequest) {

		// TODO Auto-generated method stub
		try {
			VidringProductModel productModel = productRepo.findByProductId(pinPushRequest.getProductId());
			if (Boolean.TRUE.equals(Objects.nonNull(productModel))) {
				VidringPartnerModel partnerModel = partnerRepo
						.findByCountryCodeAndOperatorId(productModel.getCountryCode(), productModel.getOperatorId());
				if (Boolean.TRUE.equals(Objects.nonNull(partnerModel))) {
					String requestBody = partnerModel.getRequestBody();
					requestBody = requestBody.replace("{subKeyword}", productModel.getKeyWord());
					requestBody = requestBody.replace("{trackingId}", pinPushRequest.getTransactionId());
					requestBody = requestBody.replace("{entryChannel}", pinPushRequest.getChannel());
					requestBody = requestBody.replace("{mcc}", productModel.getMcc());
					requestBody = requestBody.replace("{mnc}", productModel.getMnc());
					requestBody = requestBody.replace("{campaign}", productModel.getCampaign());
					String httpResponse = HttpUtil.sendRequest(partnerModel.getEndPoint(), requestBody);
					log.info("Timwe Pin Push Request  ::::  {} ", requestBody);
					log.info("Timwe Pin Push Response ::::  {} ", httpResponse);
					dbUtil.saveSubscriptionRequest(pinPushRequest.getMsisdn(), pinPushRequest.getTransactionId(),
							productModel, requestBody, httpResponse);
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
