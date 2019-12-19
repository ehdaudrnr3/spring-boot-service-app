package com.spring.boot.service.app.models.comparator;

import java.math.BigDecimal;
import java.util.Comparator;

import com.spring.boot.service.app.models.RegionDto;

public class RateDescendingComparator implements Comparator<RegionDto>{
	
	@Override
	public int compare(RegionDto o1, RegionDto o2) {
		BigDecimal o1Rate = new BigDecimal(o1.getRate());
		BigDecimal o2Rate = new BigDecimal(o2.getRate());
		
		return o2Rate.compareTo(o1Rate);
	}

}
  