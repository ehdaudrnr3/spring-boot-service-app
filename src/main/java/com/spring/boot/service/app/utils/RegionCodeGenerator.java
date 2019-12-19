package com.spring.boot.service.app.utils;

public class RegionCodeGenerator {
	
	public static String generate(int key) {
		return String.format("reg%s", key);
	}
	
	public static String generate(String maxValue) {
		maxValue = maxValue.replaceAll("[^0-9]", "");
		return String.format("reg%s", Long.parseLong(maxValue)+1L);
	}
}
