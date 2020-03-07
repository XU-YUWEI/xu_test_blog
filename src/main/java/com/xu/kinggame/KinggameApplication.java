package com.xu.kinggame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xu.kinggame.dao")
@SpringBootApplication
public class KinggameApplication {

	public static void main(String[] args) {
		System.out.println("ffffff");
		SpringApplication.run(KinggameApplication.class, args);
	}

}
