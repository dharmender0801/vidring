package com.vidring.util;

import com.vidring.response.StatusResponse;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ConstantManager {
	
	

	public static final StatusResponse getSuccess() {
		
		StatusResponse response = new StatusResponse();
		response.setStatusCode(200);
		response.setStatusDescription("Success");
		log.info("Response : {} ",response);
		return response;
	}

	public static final StatusResponse getFailed() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(400);
		response.setStatusDescription("Failed");
		log.info("Response : {} ",response);
		return response;
	}

	public static final StatusResponse getInternalServerError() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(500);
		response.setStatusDescription(" Internal Server Error !");
		log.info("Response : {} ",response);
		return response;
	}

	public static final StatusResponse getPartnerNotFound() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(404);
		response.setStatusDescription("Partner Not Found ! ");
		log.info("Response : {} ",response);
		return response;
	}

	public static final StatusResponse getProductNotFound() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(404);
		response.setStatusDescription("Product Not Found ! ");
		log.info("Response : {} ",response);
		return response;
	}

	public static final StatusResponse AlreadySubscribed() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(208);
		response.setStatusDescription("User Already Subscribed ");
		log.info("Response : {} ",response);
		return response;
	}

	public static final StatusResponse TransNotFound() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(404);
		response.setStatusDescription("Transaction Id Not Found ! ");
		log.info("Response : {} ",response);
		return response;
	}

}
