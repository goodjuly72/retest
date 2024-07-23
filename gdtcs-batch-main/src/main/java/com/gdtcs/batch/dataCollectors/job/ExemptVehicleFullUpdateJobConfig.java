package com.gdtcs.batch.dataCollectors.job;

import com.gdtcs.batch.dataCollectors.common.BatchConstants;

import com.gdtcs.batch.dataCollectors.tasklet.ExemptVehicleFullUpdateTasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ExemptVehicleFullUpdateJobConfig {

	@Bean(name= BatchConstants.EXEMPT_VEHICLE_JOB)
	public Job exemptVehicleFullUpdateJob(JobRepository jobRepository, @Qualifier(BatchConstants.EXEMPT_VEHICLE_STEP) Step exemptVehicleFullUpdateStep) {
		return new JobBuilder(BatchConstants.EXEMPT_VEHICLE_JOB, jobRepository)
			.start(exemptVehicleFullUpdateStep)
			.build();
	}

	@Bean(name= BatchConstants.EXEMPT_VEHICLE_STEP)
	public Step exemptVehicleFullUpdateStep(JobRepository jobRepository, @Qualifier("exemptVehicleFullUpdateTasklet")Tasklet exemptVehicleFullUpdateTasklet,  PlatformTransactionManager platformTransactionManager) {
		return new StepBuilder(BatchConstants.EXEMPT_VEHICLE_STEP, jobRepository)
			.tasklet(exemptVehicleFullUpdateTasklet, platformTransactionManager)
			.build();
	}
}
