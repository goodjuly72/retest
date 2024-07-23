package com.gdtcs.batch.dataCollectors.job;

import com.gdtcs.batch.dataCollectors.common.BatchConstants;
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
public class DataCollectorJobConfig {

	@Bean(name = BatchConstants.DATA_COLLECTOR_JOB)
	public Job dataCollectorJob(JobRepository jobRepository,  @Qualifier(BatchConstants.DATA_COLLECTOR_STEP) Step dataCollectorStep) {
		return new JobBuilder(BatchConstants.DATA_COLLECTOR_JOB, jobRepository)
			.start(dataCollectorStep)
			.build();
	}

	@Bean(name = BatchConstants.DATA_COLLECTOR_STEP)
	public Step dataCollectorStep(JobRepository jobRepository, @Qualifier("dataCollectorTasklet")Tasklet dataCollectorTasklet, PlatformTransactionManager platformTransactionManager){
		return new StepBuilder(BatchConstants.DATA_COLLECTOR_STEP, jobRepository)
			.tasklet(dataCollectorTasklet, platformTransactionManager)
			.build();
	}

}
