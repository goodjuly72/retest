package com.gdtcs.batch.dataCollectors.vo.xml.response.DisabilityVehicle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@XmlType(propOrder = {"commonHeader"})
public class HeaderDisabilityVehicle {

	private CommonHeaderDisabilityVehicle commonHeader;

	@XmlElement(name = "commonHeader", namespace = "http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionDisabledCarYn/types")
	public CommonHeaderDisabilityVehicle getCommonHeader() {
		return commonHeader;
	}
}
