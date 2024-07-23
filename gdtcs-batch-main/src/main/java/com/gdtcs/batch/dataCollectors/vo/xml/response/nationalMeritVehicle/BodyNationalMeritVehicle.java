package com.gdtcs.batch.dataCollectors.vo.xml.response.nationalMeritVehicle;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@ToString
@NoArgsConstructor
@XmlRootElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class BodyNationalMeritVehicle {
	private GetMeritCarYnResponse getMeritCarYnResponse;

	@XmlElement(name = "getMeritCarYnResponse", namespace = "http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types")
	public GetMeritCarYnResponse getGetMeritCarYnResponse() {
		return getMeritCarYnResponse;
	}
}
