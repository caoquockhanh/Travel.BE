package com.MyTravel.mytravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class MyTravelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyTravelApplication.class, args);
	}
	@Bean
	public Executor executor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(30);
		executor.setMaxPoolSize(90);
		executor.setQueueCapacity(500);
		executor.initialize();
		return executor;
	}
}
