package com.spring.boot.service.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.boot.service.app.models.entity.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {
	
	Region findByRegionName(String regionName);
	
	@Query("SELECT MAX(regionCode) FROM Region r")
	String findMaxRegion();
}
