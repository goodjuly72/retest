package com.gdtcs.config;

import com.gdtcs.batch.dataCollectors.util.PriorityExecutorService;

import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorConfig {

	@Schema(description = "Common pool size", example = "2")
	@Value("${common.pool.size}")
	private int poolSize;

	@Bean
	public PriorityExecutorService priorityExecutorService() {
		return new PriorityExecutorService(poolSize);
	}
}
