package com.gdtcs.batch.dataCollectors.vo.xml.response.DisabilityVehicle;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ToString
@NoArgsConstructor
@Setter
@XmlType(propOrder = {"carNo", "tgtrNm", "obsLvCla", "inqrDt", "qufyYn"})
@XmlRootElement(name = "getReductionDisabledCarYnResponse", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
public class GetReductionDisabledCarYnResponse {
	private String carNo;
	private String tgtrNm;
	private String obsLvCla;
	private String inqrDt;
	private String qufyYn;

	@XmlElement(name = "CAR_NO", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getCarNo() {
		return carNo;
	}

	@XmlElement(name = "TGTR_NM", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getTgtrNm() {
		return tgtrNm;
	}

	@XmlElement(name = "OBS_LV_CLA", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getObsLvCla() {
		return obsLvCla;
	}

	@XmlElement(name = "INQR_DT", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getInqrDt() {
		return inqrDt;
	}

	@XmlElement(name = "QUFY_YN", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public String getQufyYn() {
		return qufyYn;
	}

	@Builder
	public GetReductionDisabledCarYnResponse(String carNo, String tgtrNm, String obsLvCla, String inqrDt, String qufyYn) {
		this.carNo = carNo;
		this.tgtrNm = tgtrNm;
		this.obsLvCla = obsLvCla;
		this.inqrDt = inqrDt;
		this.qufyYn = qufyYn;
	}
}
