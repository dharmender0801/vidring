package com.vidring.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidring.dto.UserSubscriptionDto;
import com.vidring.dto.VidringSubscriptionDto;
import com.vidring.model.VidringSubscriptionModel;
import com.vidring.repository.VidringPartnerRepo;
import com.vidring.repository.VidringProductRepo;
import com.vidring.repository.VidringSubRepo;
import com.vidring.repository.VidringSubscriptionRequestRepo;
import com.vidring.response.StatusResponse;
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
	private VidringSubscriptionRequestRepo subscriptionRequestRepo;
	@Autowired
	private TimweService timweService;

	public StatusResponse SubscribeUser(UserSubscriptionDto subscriptionDto) {
		// TODO Auto-generated method stub
		Optional<VidringSubscriptionModel> subscriptionModel = vidringSubRepo.findByMsisdn(subscriptionDto.getMsisdn());
		log.info("{}", subscriptionModel);
		if (Boolean.TRUE.equals(!subscriptionModel.isPresent())) {
			switch (subscriptionDto.getOperatorId()) {
			case "101":
				return timweService.sendPinPushRequest(subscriptionDto);
			default:
				break;
			}

		}

		return ConstantManager.AlreadySubscribed();
	}

	public StatusResponse UserPinVerify(UserSubscriptionDto subscriptionDto) throws Exception {
		// TODO Auto-generated method stub
		switch (subscriptionDto.getOperatorId()) {
		case "101":
			return timweService.sendPinVerify(subscriptionDto);
		default:
			break;
		}

		return null;
	}

	public VidringSubscriptionDto getUserDetail(String msisdn) {
		Optional<VidringSubscriptionModel> subscriptionModel = vidringSubRepo.findByMsisdn(msisdn);
		VidringSubscriptionDto dto = new VidringSubscriptionDto();
		if (subscriptionModel.isPresent()) {
			Utils.copyProperties(subscriptionModel.get(), dto);
			dto.setProductModel(subscriptionModel.get().getProductModel());
		}
		return dto;
	}
}
