package com.internevaluation.formfiller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class FormFillerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormFillerApplication.class, args);
	}

	@Configuration
	@EnableAsync
	public class AsyncConfig {
		@Bean
		public TaskExecutor taskExecutor() {
			return new SimpleAsyncTaskExecutor();
		}
	}
}
