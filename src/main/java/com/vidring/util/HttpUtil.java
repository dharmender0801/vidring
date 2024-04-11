package com.vidring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpUtil {

	@Autowired
	RestTemplate restTemplate;

	public <T> ResponseEntity<T> sendRequest(String httpUrl, HttpEntity<?> requestEntity, HttpMethod httpMethod,
			Class<T> responseType) {
		ResponseEntity<T> responseEntity = restTemplate.exchange(httpUrl, httpMethod, requestEntity, responseType);
		return responseEntity;
	}

}
