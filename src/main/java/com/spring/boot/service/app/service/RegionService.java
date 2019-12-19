package com.spring.boot.service.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spring.boot.service.app.constant.Constants;
import com.spring.boot.service.app.exception.ApplicationRuntimeException;
import com.spring.boot.service.app.models.RegionDto;
import com.spring.boot.service.app.models.comparator.LimitDescendingComparator;
import com.spring.boot.service.app.models.comparator.RateDescendingComparator;
import com.spring.boot.service.app.models.entity.Region;
import com.spring.boot.service.app.models.entity.RegionDetail;
import com.spring.boot.service.app.repository.RegionDetailRepository;
import com.spring.boot.service.app.repository.RegionRepository;
import com.spring.boot.service.app.service.cache.RegionCacheMemory;
import com.spring.boot.service.app.utils.RegionCodeGenerator;
import com.spring.boot.service.app.utils.RegionUtil;

@Service
public class RegionService {

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private RegionDetailRepository regionDetailRepository;
	
	@Autowired
	private RegionCacheMemory regionCacheMemory;
	
	/**
	 * 지자체 목록
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<RegionDto> getRegionList() throws Exception {
		try {
			List<RegionDto> list = (List<RegionDto>) regionCacheMemory.findByKey(Constants.REGION_CACHE_KEY_LIST);

			if(list == null) {
				list = regionDetailRepository.findAllJoinFetch()
						.stream()
						.map(RegionDto::new)
						.collect(Collectors.toList());
				
				regionCacheMemory.insert(Constants.REGION_CACHE_KEY_LIST, list);
			}
			return list;
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 지자체명 입력에 따른 지자체 정보
	 * @param regionName
	 * @return
	 * @throws Exception
	 */
	public RegionDto getRegion(String regionName) throws Exception {
		try {
			RegionDetail regionDetail = (RegionDetail) regionCacheMemory.findByKey(regionName);
			if(regionDetail == null) {
				Region region = regionRepository.findByRegionName(regionName);
				regionDetail = regionDetailRepository.findByRegion(region);
				
				if(regionDetail == null) {
					throw new ApplicationRuntimeException(HttpStatus.NOT_FOUND, "지자체 정보를 찾을 수 없습니다.");
				}
				
				regionCacheMemory.insert(regionName, regionDetail);
			}
			return new RegionDto(regionDetail);
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 지원금액을 내림차순으로 정렬한 지자체명 목록
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getRegionListOrderbyDesc(int size) throws Exception {
		List<Map<String, String>> regionList = null;
		try {
			if(size <= 0) {
				return new ArrayList<>();
			}
			
			String descKey = Constants.REGION_CACHE_KEY_DESC+"_"+size;
			regionList = (List<Map<String, String>>) regionCacheMemory.findByKey(descKey);
			
			if(regionList == null) {
				regionList = new ArrayList<>();
				List<RegionDto> list = new ArrayList<>();
				
				List<RegionDetail> regionDetails = regionDetailRepository.findAllJoinFetch();
				for(RegionDetail regionDetail : regionDetails) {
					String limit = RegionUtil.convertLimit(regionDetail.getLimitAmount());
					String rate = RegionUtil.convertRate(regionDetail.getRate()).toString();
					
					regionDetail.setLimitAmount(limit);
					regionDetail.setRate(rate);
					RegionDto regionDetailDto = new RegionDto(regionDetail);
					list.add(regionDetailDto);
				}
				
				list = list.stream()
						.sorted(new LimitDescendingComparator()
						.thenComparing(new RateDescendingComparator()))
						.collect(Collectors.toList());
				
				for(int i = 0;i < list.size();i++) {
					if(i < size) {
						HashMap<String, String> region = new HashMap<String, String>();
						region.put("region", list.get(i).getRegion());
						regionList.add(region);
					}
				}
				
				regionCacheMemory.insert(descKey, regionList);
			}
			
			return regionList;
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 이차보전의 비율이 가장 낮은 추천기관
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getInstituteByMinRate() throws Exception {
		Map<String, String> institute = null;
		try {
			institute = (Map<String, String>) regionCacheMemory.findByKey(Constants.REGION_CACHE_KEY_RATE);
			
			if(institute == null) {
				institute = new HashMap<>();
				BigDecimal minRate = null;
				
				List<RegionDetail> regionDetails = regionDetailRepository.findAllJoinFetch();
				for (int i = 0; i < regionDetails.size(); i++) {
					String textRate = regionDetails.get(i).getRate();
					BigDecimal rate = RegionUtil.convertRate(textRate);

					if (i == 0) {
						minRate = rate;
						institute.put("institute", regionDetails.get(i).getInstitute());
					} else if (rate.compareTo(minRate) < 0) {
						minRate = rate;
						institute.replace("institute", regionDetails.get(i).getInstitute());
					}
				}
				regionCacheMemory.insert(Constants.REGION_CACHE_KEY_RATE, institute);
			}
			
			return institute;
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 지자체 정보 저장
	 * @param regionList
	 * @throws Exception
	 */
	public void save(List<RegionDto> regionList) throws Exception {
		try {
			for (RegionDto regionDto : regionList) {
				if(regionDto.getRegion() == null) {
					throw new ApplicationRuntimeException(HttpStatus.BAD_REQUEST, "지자체명이 입력되지 않았습니다.");
				}
				
				String regionName = regionDto.getRegion();
				Region region = regionRepository.findByRegionName(regionName);

				if (region == null) {
					String maxRegion = regionRepository.findMaxRegion();
					String regionCode = RegionCodeGenerator.generate(maxRegion);

					region = new Region(regionCode, regionName);
					regionRepository.save(region);

					RegionDetail regionDetail = new RegionDetail(region, regionDto);
					regionDetailRepository.save(regionDetail);
				} else {
					RegionDetail regionDetail = regionDetailRepository.findByRegion(region);
					regionDetail.updateRegionDetail(regionDto);
					
					regionDetailRepository.save(regionDetail);
				}
			}
			regionCacheMemory.clear();
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 지자체 삭제
	 * @param regionName
	 * @throws Exception
	 */
	public void delete(String regionName) throws Exception {
		try {
			Region region = regionRepository.findByRegionName(regionName);
			if (region == null) {
				throw new ApplicationRuntimeException(HttpStatus.NOT_FOUND, "지자체 정보를 찾을 수 없습니다.");
			}
			regionRepository.delete(region);
			regionCacheMemory.clear();
		} catch (ApplicationRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
}
