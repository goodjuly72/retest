package com.gdtcs.batch.dataCollectors.vo.xml.response.nationalMeritVehicle;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@ToString
@NoArgsConstructor
@XmlType(propOrder = {"commonHeader"})
public class HeaderNationalMeritVehicle {

	private CommonHeaderNationalMeritVehicle commonHeader;

	@XmlElement(name = "commonHeader", namespace = "http://ccais.mopas.go.kr/dh/rid/services/mpva/MeritCarYn/types")
	public CommonHeaderNationalMeritVehicle getCommonHeader() {
		return commonHeader;
	}
}
