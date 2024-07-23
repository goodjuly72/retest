package com.gdtcs.batch.dataCollectors.mapper;

import com.gdtcs.batch.dataCollectors.vo.VehicleMasterVo;
import com.gdtcs.batch.dataCollectors.vo.VehicleVo;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataCollectorMapper {

		// 차량정보 목록 가져오기
		List<VehicleVo> selectVehicleList(VehicleVo vehicleVo);

		// 차량정보 업데이트
		void updateVehicle(VehicleVo vehicleVo);

		// 면제차량 데이터 insert
		void insertVehicleMaster(VehicleMasterVo vehicleMasterVo);

		// 면제차량 여부 조회
		VehicleMasterVo selectVehicleMaster(VehicleMasterVo vehicleMasterVo);

		// 면제차량 데이터 update
		List<VehicleMasterVo> selectAllSyncVehicleMaster(VehicleMasterVo vehicleMasterVo);
}
