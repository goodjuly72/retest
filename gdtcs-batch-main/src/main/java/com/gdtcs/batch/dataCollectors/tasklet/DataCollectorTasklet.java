package com.gdtcs.batch.dataCollectors.tasklet;

import com.gdtcs.batch.dataCollectors.service.DataCollectorServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
@Qualifier("dataCollectorTasklet")
public class DataCollectorTasklet implements Tasklet {

	private final DataCollectorServiceImpl dataCollectorService;

	public DataCollectorTasklet(DataCollectorServiceImpl dataCollectorService) {

		this.dataCollectorService = dataCollectorService;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		dataCollectorService.findVehicleInfo();
		return RepeatStatus.FINISHED;
	}
}
