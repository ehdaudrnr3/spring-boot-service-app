package com.spring.boot.service.app.models.comparator;

import java.math.BigDecimal;
import java.util.Comparator;

import com.spring.boot.service.app.models.RegionDto;

public class LimitDescendingComparator implements Comparator<RegionDto>{

	@Override
	public int compare(RegionDto o1, RegionDto o2) {
		BigDecimal o1Limit = new BigDecimal(o1.getLimit());
		BigDecimal o2Limit = new BigDecimal(o2.getLimit());
		
		return o2Limit.compareTo(o1Limit);
	}

}
