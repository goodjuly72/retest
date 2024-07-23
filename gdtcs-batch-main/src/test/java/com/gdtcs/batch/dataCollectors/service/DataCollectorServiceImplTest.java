package com.gdtcs.batch.dataCollectors.service;

import com.gdtcs.batch.dataCollectors.mapper.CodeMapper;
import com.gdtcs.batch.dataCollectors.mapper.DataCollectorMapper;
import com.gdtcs.batch.dataCollectors.vo.VehicleVo;
import com.gdtcs.batch.dataCollectors.vo.CodeVo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataCollectorServiceImplTest {

		@Mock
		private DataCollectorMapper dataCollectorMapper;

		@Mock
		private CodeMapper codeMapper;

		@InjectMocks
		private DataCollectorServiceImpl dataCollectorService;

		@BeforeEach
		public void setUp() {
			when(dataCollectorMapper.selectVehicleList(VehicleVo.builder().build())).thenReturn(Arrays.asList(VehicleVo.builder().build()));
			when(codeMapper.selectCodeList()).thenReturn(Arrays.asList(new CodeVo()));
		}

		@Test
		public void testFindVehicleInfo() {
			dataCollectorService.findVehicleInfo();

			verify(dataCollectorMapper, times(1)).selectVehicleList(VehicleVo.builder().build());
			verify(codeMapper, times(1)).selectCodeList();
		}
}
