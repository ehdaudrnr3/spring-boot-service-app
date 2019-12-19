package com.spring.boot.service.app.models;

import java.io.Serializable;

import com.spring.boot.service.app.models.entity.RegionDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String region;
	
	private String target;
	
	private String usage;
	
	private String limit;
	
	private String rate;
	
	private String institute;
	
	private String mgmt;
	
	private String reception;
	
	public RegionDto(RegionDetail regionDetail) {
		this.region = regionDetail.getRegion().getRegionName();
		this.target = regionDetail.getTarget();
		this.usage = regionDetail.getUsage();
		this.limit = regionDetail.getLimitAmount();
		this.rate = regionDetail.getRate();
		this.institute = regionDetail.getInstitute();
		this.mgmt = regionDetail.getMgmt();
		this.reception = regionDetail.getReception();
	}
}
