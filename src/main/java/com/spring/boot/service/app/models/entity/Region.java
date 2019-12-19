package com.spring.boot.service.app.models.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
public class Region extends BaseEntity {

	@Id
	@Column(name = "REGION_CODE")
	private String regionCode;
	
	@Column(nullable = false)
	private String regionName;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "region")
	private RegionDetail regionDetail;
	
	public Region(String regionCode, String regionName) {
		this.regionCode = regionCode;
		this.regionName = regionName;
	}
}
