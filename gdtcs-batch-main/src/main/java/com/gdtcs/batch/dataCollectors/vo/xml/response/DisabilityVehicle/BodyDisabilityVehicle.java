package com.gdtcs.batch.dataCollectors.vo.xml.response.DisabilityVehicle;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@ToString
@NoArgsConstructor
@XmlRootElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class BodyDisabilityVehicle {
	private GetReductionDisabledCarYnResponse getReductionDisabledCarYnResponse;

	@XmlElement(name = "getReductionDisabledCarYnResponse", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public GetReductionDisabledCarYnResponse getGetReductionDisabledCarYnResponse() {
		return getReductionDisabledCarYnResponse;
	}
}
