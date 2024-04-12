package com.vidring.util;

import com.vidring.response.StatusResponse;

public class ConstantManager {

	public static final StatusResponse getSuccess() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(200);
		response.setStatusDescription("Success");
		return response;
	}

	public static final StatusResponse getFailed() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(400);
		response.setStatusDescription("Failed");
		return response;
	}

	public static final StatusResponse getInternalServerError() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(500);
		response.setStatusDescription(" Internal Server Error !");
		return response;
	}

	public static final StatusResponse getPartnerNotFound() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(404);
		response.setStatusDescription("Partner Not Found ! ");
		return response;
	}

	public static final StatusResponse getProductNotFound() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(404);
		response.setStatusDescription("Product Not Found ! ");
		return response;
	}

	public static final StatusResponse TransNotFound() {
		StatusResponse response = new StatusResponse();
		response.setStatusCode(404);
		response.setStatusDescription("Transaction Id Not Found ! ");
		return response;
	}

}
