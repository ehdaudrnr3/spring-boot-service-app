package com.spring.boot.service.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.boot.service.app.models.entity.Region;
import com.spring.boot.service.app.models.entity.RegionDetail;

@Repository
public interface RegionDetailRepository extends JpaRepository<RegionDetail, Long> {

	RegionDetail findByRegion(Region region);
	
	@Query("select r from RegionDetail r join fetch r.region")
	List<RegionDetail> findAllJoinFetch();
}
