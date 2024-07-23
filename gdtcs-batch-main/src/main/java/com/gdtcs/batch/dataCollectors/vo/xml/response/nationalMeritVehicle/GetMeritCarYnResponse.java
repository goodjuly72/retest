package com.gdtcs.batch.dataCollectors.vo.xml.response.nationalMeritVehicle;


import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ToString
@Setter
@NoArgsConstructor
@XmlRootElement(name = "getMeritCarYnResponse", namespace = "http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types")
@XmlType(propOrder = { "resultCd", "resultMsg" })
public class GetMeritCarYnResponse {
	private String resultCd;
	private String resultMsg;

	@XmlElement(namespace = "http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types")
	public String getResultCd() {
		return resultCd;
	}

	@XmlElement(namespace = "http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types")
	public String getResultMsg() {
		return resultMsg;
	}

	@Builder
	public GetMeritCarYnResponse(String resultCd, String resultMsg) {
		this.resultCd = resultCd;
		this.resultMsg = resultMsg;
	}
}
