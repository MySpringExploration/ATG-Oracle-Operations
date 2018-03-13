package org.iqra.operationsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class GMOOperationsApplication {

	public static void main(String[] args) {
		ApplicationContext context =
				new ClassPathXmlApplicationContext("Spring-Module.xml");
		SpringApplication.run(GMOOperationsApplication.class, args);
		

	}
}
