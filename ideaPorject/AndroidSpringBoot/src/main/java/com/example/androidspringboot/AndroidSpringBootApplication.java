package com.example.androidspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//exclude = DataSourceAutoConfiguration.class
@SpringBootApplication
@MapperScan
public class AndroidSpringBootApplication {

	public static void main(String[] args) {

		try{
			SpringApplication.run(AndroidSpringBootApplication.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
