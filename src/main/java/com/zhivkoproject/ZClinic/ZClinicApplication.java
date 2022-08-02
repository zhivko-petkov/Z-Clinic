package com.zhivkoproject.ZClinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ZClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZClinicApplication.class, args);
	}

}
