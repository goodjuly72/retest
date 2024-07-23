package com.gdtcs.batch.dataCollectors.vo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class VehicleVo implements Serializable {

	@Serial
	private static final long serialVersionUID = 8623039462867570669L;

	@Schema(description = "순번")
	private Long seq;

	@Schema(description = "차량번호")
	private String carNo;

	@Schema(description = "기준일자")
	private String baseDate;

	@Schema(description = "진행 FLAG (01 : API 요청 ,02 : API 처리중  ,03 : API 완료  ,04 : RPA 사용완료)" )
	private String progFlag;

	@Schema(description = "면제여부 (01 : 면제아님 ,02 : 조회불능 ,03 : 다자녀 04 : 유공자 ,05 : 장애인  ,06 : 전기(부산)  ,07 : 수소(부산)  ,08 : 공차(택시)  ,99 : 면제여부 조회전) ")
	private String exemptStatus;

	@Schema(description = "영업소ID")
	private String icCode;

	@Schema(description = "업무일자")
	private String workDate;

	@Schema(description = "업무번호")
	private String workNo;

	@Schema(description = "차량일번호")
	private String handSno;

	@Schema(description = "차량타입 (01 : 일반, 02 : 택시 (부산/경남) 10건씩 읽어드려서 36시간 뒤에 순차적으로 요청) ")
	private String vehicleType;

	@Schema(description = "통과시간 (01 : 일반차량일 경우 입력안해도 됨  ,02 : 택시일 경우는 반드시 입력 되어야 함 ) ")
	private String passTime;

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

	@Builder
	public VehicleVo(Long seq, String carNo, String baseDate, String progFlag, String exemptStatus, String icCode, String workDate, String workNo, String handSno, String vehicleType, String passTime, String vehicleKind, String regId, String regDt,
		String modId, String modDt) {
		this.seq = seq;
		this.carNo = carNo;
		this.baseDate = baseDate;
		this.progFlag = progFlag;
		this.exemptStatus = exemptStatus;
		this.icCode = icCode;
		this.workDate = workDate;
		this.workNo = workNo;
		this.handSno = handSno;
		this.vehicleType = vehicleType;
		this.passTime = passTime;
		this.vehicleKind = vehicleKind;
		this.regId = regId;
		this.regDt = regDt;
		this.modId = modId;
		this.modDt = modDt;
	}
}
