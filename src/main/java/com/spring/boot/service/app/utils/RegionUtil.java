package com.spring.boot.service.app.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RegionUtil {

	public static String convertLimit(String limit) throws Exception {
		try {
			String amount = limit.replaceAll("[^0-9]", "");
			if(limit.indexOf("억") != -1) {
				return amount+"00000000";
			} else if(limit.indexOf("천만원") != -1) {
				return amount+"0000000";
			} else if(limit.indexOf("백만원") != -1) {
				return amount+"000000";
			} else if(limit.indexOf("십만원") != -1) {
				return amount+"00000";
			} else if(limit.indexOf("만원") != -1) {
				return amount+"0000";
			} else if(limit.indexOf("천원") != -1) {
				return amount+"000";
			} else if(limit.indexOf("백원") != -1) {
				return amount+"00";
			} else if(limit.indexOf("십원") != -1) {
				return amount+"0";
			} else if(limit.indexOf("원") != -1) {
				return amount;
			} else {
				return "0";
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static BigDecimal convertRate(String rate) throws Exception {
		try {
			if(rate.indexOf("전액") != -1) {
				return new BigDecimal(100);
			} else if(rate.indexOf("~") != -1) {
				String[] rateRange = rate.replace("%", "").split("\\~");
				
				String startRate = rateRange[0];
				String endRate = rateRange[1];
				
				BigDecimal sum = new BigDecimal(startRate).add(new BigDecimal(endRate));
				return sum.divide(new BigDecimal(2), RoundingMode.HALF_UP);
			} else {
				rate = rate.replace("%", "");
				return new BigDecimal(rate);
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
