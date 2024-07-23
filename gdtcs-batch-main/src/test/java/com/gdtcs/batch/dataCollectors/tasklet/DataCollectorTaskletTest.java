package com.gdtcs.batch.dataCollectors.tasklet;

import com.gdtcs.batch.dataCollectors.service.DataCollectorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class DataCollectorTaskletTest {

	@Mock
	private DataCollectorServiceImpl dataCollectorService;

	@Mock
	private StepContribution stepContribution;

	@Mock
	private ChunkContext chunkContext;

	private DataCollectorTasklet dataCollectorTasklet;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		dataCollectorTasklet = new DataCollectorTasklet(dataCollectorService);
	}

	@Test
	@DisplayName("execute 메소드는 findVehicleInfo를 호출하고 RepeatStatus.FINISHED를 반환해야 한다")
	public void executeShouldCallFindVehicleInfoAndReturnFinished() throws Exception {
		// Act
		RepeatStatus result = dataCollectorTasklet.execute(stepContribution, chunkContext);

		// Assert
		verify(dataCollectorService).findVehicleInfo();
		assertEquals(RepeatStatus.FINISHED, result);
	}
}
