package com.vidring.util;

import java.math.BigDecimal;

public class Constants {
	public static final String DEVICE_TYPE = "DEVICE-TYPE";
	public static final String APP_VERSION = "VER";
	public static final String SUCCESS = "Success";
	public static final String NOT_FOUND = "Not Found";
	public static final Integer USER = 3;
	public static final Integer SUPER_ADMIN = 1;
	public static final String REQUEST_DATE_PATTEREN = "dd-MM-yyyy Z";
	public static final String REQUEST_TIME_PATTEREN = "HH:mm:ss Z";
	public static final String REQUEST_DATETIME_PATTEREN = "dd-MM-yyyy HH:mm:ss Z";
	public static final String REQUEST_TIMEZONE = "Asia/Kolkata";
	public static final String FAIL = "Request Failed";
	public static final Integer PENDING = 0;
	public static final Integer CREATED = 1;
	public static final String accessApiKey = "13D7BB322BE5D315576406";
	public static final String REQUEST_MORE_THAN_PERMITTED = "Requested Operation Not Allowed with current value.";
	public static final String EMPTY_OR_INCOMPLETE_REQUEST_BODY = "Incomplete or empty request received";
	public static final String PARTIAL_CONTENT_AVAILABLE = "Partial or No Success for the request";
	public static final int ONE = 1;
	public static final Integer HTTP_OK = 200;
	public static final Integer HTTP_FORBIDDEN = 403;
	public static final Integer HTTP_NOT_FOUND = 404;
	public static final String FILE_PATH_DELIMETER = "/";
	public static final String[] SHIPMENT_UPLOAD_SCHEMA = { "Order ID", "CustomerId", "Tracking ID", "Carrier", "Route",
			"Order Type", "First Name", "Last Name", "Address Type", "Address1", "Address2", "Zipcode", "City", "State",
			"Country", "Customer Email", "Customer Phone", "Special Instructions", "Delivery Target Date (MM/dd/yyyy)",
			"Delivery Target Time", "Product", "Quantity", "Bill To Name", "Bill To Address1", "Bill To Address2",
			"Bill To City", "Bill To State", "Bill To Country", "Bill To Zipcode", "Bill To Email", "Bill To Contact",
			"Service Type", "Company Name", "LOB", "Ship By Date", "Pick Date" };
	public static final Integer SHIPMENT_UPLOAD_SCHEMA_LENGTH = SHIPMENT_UPLOAD_SCHEMA.length;
	public static final Integer ZERO_INT = 0;
	public static final Integer ONE_INT = 1;
	public static final Float ZERO_FLOAT = 0f;
	public static final Float ONE_FLOAT = 1f;
	public static final Integer ACTIVE = 1;
	public static final String USER_NOT_FOUND = "Bad Credentials or User not found. Please try again.";
	public static final String LMS_ERROR_CODE_2002 = "2002";
	public static final Integer TEN_INT = 10;
	public static final String CART_EMPTY = "User Cart is empty. No record to display.";
	public static final Long ZERO_LONG = 0L;
	public static final Integer ROLE_USER = 3;
	public static final Integer earthRadius = 6371;
	public static final String dailyReportType = "Daily";
	public static final String calories = "Calories";
	public static final String fat = "fat";
	public static final String carbs = "Carbs";
	public static final String protein = "protein";
	public static final String cO2eMission = "co2Emission";
	public static final String NULL_BUSINESSTYPE = "-1";
	public static final BigDecimal ZERO_BIG = BigDecimal.ZERO;
	public static final Integer INT_THIRTY_MINS = 1800;

}
