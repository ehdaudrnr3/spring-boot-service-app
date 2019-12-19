package com.spring.boot.service.app.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.spring.boot.service.app.constant.Constants;

public class DateUtil {

	public static String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat(Constants.TIME_FORMAT);
		return dateFormat.format(new Date());
	}

	public static Date getOneHourExpireTimeMillis() {
		Date expireTime = new Date();
		long expireTimeMillis = expireTime.getTime() + Constants.ONE_HOUR;
		expireTime.setTime(expireTimeMillis);
		return expireTime;
	}
	
	public static Date getTwoHourExpireTimeMillis() {
		Date expireTime = new Date();
		long expireTimeMillis = expireTime.getTime() + Constants.TWO_HOUR;
		expireTime.setTime(expireTimeMillis);
		return expireTime;
	}
}
