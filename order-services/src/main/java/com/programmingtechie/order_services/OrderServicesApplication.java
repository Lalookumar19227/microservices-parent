package com.programmingtechie.order_services;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Slf4j
@SpringBootApplication
public class OrderServicesApplication {

	static Logger log = LoggerFactory.getLogger(OrderServicesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OrderServicesApplication.class, args);

		log.info("sdfghjxcvbn");

		System.out.println("order services");
	}

}
