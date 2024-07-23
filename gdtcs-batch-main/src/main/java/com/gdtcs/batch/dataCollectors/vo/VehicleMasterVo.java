package com.gdtcs.batch.dataCollectors.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class VehicleMasterVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 9198379759898136785L;

	@Schema(description = "차량번호")
	private String carNo;

	@Schema(description = "기준일자")
	private String baseDate;

	@Schema(description = "면제여부 (01 : 면제아님 ,02 : 조회불능 ,03 : 다자녀 04 : 유공자 ,05 : 장애인  ,06 : 전기(부산)  ,07 : 수소(부산)  ,08 : 공차(택시)  ,99 : 면제여부 조회전) ")
	private String exemptStatus;

	@Schema(description = "차량종류 (국토부(차량 분류코드 VHCTY_SE_CODE 동일) 01 : 소형  ,02 : 중형  ,03 : 대형  ,04 : 경형  ,05 : 저상형  ,06 : 고상형  ,07 : 작제함형,   ,08 : 샷시형  ,09 : 기타형) ")
	private String vehicleKind;

	@Schema(description = "등록ID")
	private String regId;

	@Schema(description = "등록일")
	private String regDt;

	@Schema(description = "수정자")
	private String modId;

	@Schema(description = "수정일")
	private String modDt;

	@Schema(description = "동기화상태 01 : 동기화, 02 : 미동기화")
	private String syncStatus;

	@Builder
	public VehicleMasterVo(String carNo, String baseDate, String exemptStatus, String vehicleKind, String regId, String regDt, String modId, String modDt, String syncStatus) {
		this.carNo = carNo;
		this.baseDate = baseDate;
		this.exemptStatus = exemptStatus;
		this.vehicleKind = vehicleKind;
		this.regId = regId;
		this.regDt = regDt;
		this.modId = modId;
		this.modDt = modDt;
		this.syncStatus = syncStatus;
	}
}
