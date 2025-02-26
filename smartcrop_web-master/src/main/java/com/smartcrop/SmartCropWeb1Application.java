package com.smartcrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SmartCropWeb1Application {

	public static void main(String[] args) {
		SpringApplication.run(SmartCropWeb1Application.class, args);
	}
}