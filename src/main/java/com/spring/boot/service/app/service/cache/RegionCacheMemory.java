package com.spring.boot.service.app.service.cache;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class RegionCacheMemory {
	
	private HashMap<String, Object> cacheMap = new HashMap<>();
	
	public void insert(String key, Object data) {
		cacheMap.put(key, data);
	}
	
	public Object findByKey(String key) {
		if(cacheMap.containsKey(key)) {
			return cacheMap.get(key);
		}
		return null;
	}
	
	public void clear() {
		cacheMap.clear();
	}
}
