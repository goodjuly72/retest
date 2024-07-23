package com.gdtcs.batch.dataCollectors.vo.xml.response.nationalMeritVehicle;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ToString
@NoArgsConstructor
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlType(propOrder = {"header", "body"})
public class EnvelopeNationalMeritVehicle {

	private HeaderNationalMeritVehicle header;
	private BodyNationalMeritVehicle body;

	@XmlElement(name = "Header", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
	public HeaderNationalMeritVehicle getHeader() {
		return header;
	}

	@XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
	public BodyNationalMeritVehicle getBody() {
		return body;
	}
}

