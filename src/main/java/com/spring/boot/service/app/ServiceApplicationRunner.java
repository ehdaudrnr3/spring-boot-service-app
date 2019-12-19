package com.spring.boot.service.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.spring.boot.service.app.models.entity.Region;
import com.spring.boot.service.app.models.entity.RegionDetail;
import com.spring.boot.service.app.utils.RegionCodeGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServiceApplicationRunner implements ApplicationRunner {

	@Autowired
	private ResourceLoader resourceLoader;
	
	@PersistenceContext
	private EntityManager entityManager;
    
	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		ClassLoader classLoader = resourceLoader.getClassLoader();
		try {
			log.info("Application Csv Load....");
			InputStream inputStream = classLoader.getResourceAsStream("사전과제1.csv");
			BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
			CSVReader csvReader = new CSVReader(reader);
			
			String[] row = null;
			int i = 0;
			while((row = csvReader.readNext()) != null) {
				if(i > 0) {
					String regionCode = RegionCodeGenerator.generate(i);
					String regionName = row[1];
					String target = row[2];
					String usage = row[3];
					String limitAmount = row[4];
					String rate = row[5];
					String institute = row[6];
					String mgmt = row[7];
					String reception = row[8];
					
					Region region = new Region(regionCode, regionName);
					RegionDetail regionDetail = RegionDetail.builder()
							.region(region)
							.target(target)
							.usage(usage)
							.limitAmount(limitAmount)
							.rate(rate)
							.institute(institute)
							.mgmt(mgmt)
							.reception(reception).build();
					region.setRegionDetail(regionDetail);
					
					entityManager.persist(region);
					entityManager.persist(regionDetail);
				}
				i++;
			}
			csvReader.close();
			reader.close();
			log.info("Application Csv Load Complete!!!");
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}  

}
