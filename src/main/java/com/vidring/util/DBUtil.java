package com.vidring.util;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vidring.model.PartnerNotificationModel;
import com.vidring.model.VidringBillingSuccessModel;
import com.vidring.model.VidringProductModel;
import com.vidring.model.VidringSubscriptionModel;
import com.vidring.model.VidringSubscriptionRequestModel;
import com.vidring.model.VidringUnSubscriptionModel;
import com.vidring.repository.PartnerNotificationRepo;
import com.vidring.repository.VidingSuccessRepo;
import com.vidring.repository.VidringProductRepo;
import com.vidring.repository.VidringSubRepo;
import com.vidring.repository.VidringSubscriptionRequestRepo;
import com.vidring.repository.VidringUnSubscriptionRepo;
import com.vidring.request.NotificationRequest;

@Component
public class DBUtil {
	@Autowired
	private VidringSubRepo vidringSubRepo;
	@Autowired
	private VidringProductRepo productRepo;
	@Autowired
	private VidringSubscriptionRequestRepo repo;
	@Autowired
	private VidingSuccessRepo successRepo;
	@Autowired
	private VidringUnSubscriptionRepo unSubscriptionRepo;
	@Autowired
	private PartnerNotificationRepo notificationRepo;

	public void saveNotfication(String msisdn, String notification, String countryCode, String operatorId) {
		// TODO Auto-generated method stub
		PartnerNotificationModel notificationModel = new PartnerNotificationModel();
		notificationModel.setMsisdn(msisdn);
		notificationModel.setCountryCode(countryCode);
		notificationModel.setOperatorId(operatorId);
		notificationModel.setDateTime(new Date());
		notificationModel.setNotification(notification);
		notificationRepo.save(notificationModel);
	}

	public VidringSubscriptionModel saveUserSubscription(String msisdn, String billingId, String status) {
		// TODO Auto-generated method stub
		VidringSubscriptionModel subModel = null;
		VidringSubscriptionRequestModel requestModel = repo.findByTransactionId(billingId);
		if (requestModel != null) {
			VidringSubscriptionModel checkModel = vidringSubRepo.findByMsisdn(msisdn).get();
			if (checkModel == null) {
				VidringSubscriptionModel subscriptionModel = new VidringSubscriptionModel();
				BeanUtils.copyProperties(requestModel, subscriptionModel);
				subscriptionModel.setId(null);
				subscriptionModel.setValidity(requestModel.getProductModel().getValidity());
				subscriptionModel.setSubscriptionDate(new Date());
				if (status.equalsIgnoreCase("success")) {
					Date date = CalenderUtil.timeExtender(requestModel.getProductModel().getValidity());
					subscriptionModel.setChargeAmount(requestModel.getProductModel().getPricePoint());
					subscriptionModel.setChargeDate(new Date());
					subscriptionModel.setExpiryDate(date);
				}
				vidringSubRepo.save(subscriptionModel);
				subModel = subscriptionModel;
			}

		}
		return subModel;

	}

	public VidringSubscriptionModel saveUserSubscription(NotificationRequest notification, String channel) {
		VidringSubscriptionModel subModel = vidringSubRepo.findByMsisdn(notification.getMsisdn())
				.orElse(new VidringSubscriptionModel());

		VidringProductModel productModel = productRepo.findByMccAndMnc(notification.getMcc(), notification.getMnc());
		subModel.setMsisdn(notification.getMsisdn());
		subModel.setProductModel(productModel);
		subModel.setValidity(productModel.getValidity());
		subModel.setSubscriptionDate(new Date());
		subModel.setChannel(channel);
		subModel = vidringSubRepo.save(subModel);
		return subModel;
	}

	public void updateUserSubscription(String msisdn) {
		// TODO Auto-generated method stub
		VidringSubscriptionModel subModel = vidringSubRepo.findByMsisdn(msisdn).get();
		if (subModel != null) {
			if (subModel.getChargeDate() != null) {
				saveToSuccessBilling(subModel, "ren");
			} else {
				saveToSuccessBilling(subModel, "sub");
			}
			Date date = CalenderUtil.timeExtender(subModel.getProductModel().getValidity());
			subModel.setChargeDate(new Date());
			subModel.setExpiryDate(date);
			vidringSubRepo.save(subModel);
		}
	}

	public void unSubscribeUser(String msisdn) {
		// TODO Auto-generated method stub
		VidringSubscriptionModel subModel = vidringSubRepo.findByMsisdn(msisdn).get();
		if (subModel != null) {
			VidringUnSubscriptionModel unSubscriptionModel = new VidringUnSubscriptionModel();
			BeanUtils.copyProperties(subModel, unSubscriptionModel);
			unSubscriptionModel.setUnsubscriptionDate(new Date());
			unSubscriptionModel.setId(null);
			unSubscriptionRepo.save(unSubscriptionModel);
			vidringSubRepo.delete(subModel);
		}

	}

	public void saveSubscriptionRequest(String msisdn, String transactionId, VidringProductModel productModel,
			String requestBody, String httpResponse) {
		// TODO Auto-generated method stub
		VidringSubscriptionRequestModel requestModel = new VidringSubscriptionRequestModel();
		requestModel.setMsisdn(msisdn);
		requestModel.setTransactionId(transactionId);
		requestModel.setProductModel(productModel);
		requestModel.setRequest(requestBody);
		requestModel.setResponse(httpResponse);
		requestModel.setRequestDate(new Date());
		repo.save(requestModel);

	}

	public void saveToSuccessBilling(VidringSubscriptionModel subModel, String typeEvent) {
		// TODO Auto-generated method stub
		VidringBillingSuccessModel billingSuccessModel = new VidringBillingSuccessModel();
		BeanUtils.copyProperties(subModel, billingSuccessModel);
		billingSuccessModel.setId(null);
		billingSuccessModel.setDeductedAmount(subModel.getProductModel().getPricePoint());
		billingSuccessModel.setProcessDatetime(new Date());
		billingSuccessModel.setProductId(subModel.getProductModel().getProductId());
		billingSuccessModel.setThreadId("1");
		billingSuccessModel.setTypeEvent(typeEvent);
		billingSuccessModel.setCountryCode(subModel.getProductModel().getCountryCode());
		billingSuccessModel.setOperatorId(subModel.getProductModel().getOperatorId());
		successRepo.save(billingSuccessModel);

	}
}
