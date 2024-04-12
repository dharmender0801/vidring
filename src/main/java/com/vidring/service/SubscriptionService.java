package com.vidring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidring.dto.UserSubscriptionDto;
import com.vidring.model.VidringSubscriptionModel;
import com.vidring.repository.VidringSubRepo;
import com.vidring.response.StatusResponse;

@Service
public class SubscriptionService {

	@Autowired
	private VidringSubRepo vidringSubRepo;

	public StatusResponse SubscribeUser(UserSubscriptionDto subscriptionDto) {
		// TODO Auto-generated method stub
		VidringSubscriptionModel subscriptionModel = vidringSubRepo.findByMsisdn(subscriptionDto.getMsisdn()).get();

		return null;
	}

}
