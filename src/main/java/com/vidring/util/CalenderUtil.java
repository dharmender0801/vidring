package com.vidring.util;

import java.util.Calendar;
import java.util.Date;

public class CalenderUtil {

	public static final Date timeExtender(Integer validity) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, validity);
		Date date = cal.getTime();
		return date;
	}
}
