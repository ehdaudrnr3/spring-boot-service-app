package com.spring.boot.service.app.models.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.spring.boot.service.app.models.RegionDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Entity
public class RegionDetail extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGION_CODE", unique = true)
	private Region region;
	
	private String target;
	
	private String usage;
	
	private String limitAmount;
	
	private String rate;
	
	private String institute;
	
	private String mgmt;
	
	private String reception;
	
	public RegionDetail(Region region, RegionDto regionDto) {
		this.region = region;
		this.target = regionDto.getTarget();
		this.usage = regionDto.getUsage();
		this.limitAmount = regionDto.getLimit();
		this.rate = regionDto.getRate();
		this.institute = regionDto.getInstitute();
		this.mgmt = regionDto.getMgmt();
		this.reception = regionDto.getReception();
	}
	
	public void updateRegionDetail(RegionDto regionDto) {
		this.setTarget(regionDto.getTarget());
		this.setUsage(regionDto.getUsage());
		this.setLimitAmount(regionDto.getLimit());
		this.setRate(regionDto.getRate());
		this.setInstitute(regionDto.getInstitute());
		this.setMgmt(regionDto.getMgmt());
		this.setReception(regionDto.getReception());
	}
}
