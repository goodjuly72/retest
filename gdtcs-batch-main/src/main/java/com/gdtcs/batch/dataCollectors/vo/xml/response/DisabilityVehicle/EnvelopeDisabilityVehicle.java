package com.gdtcs.batch.dataCollectors.vo.xml.response.DisabilityVehicle;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ToString
@NoArgsConstructor
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlType(propOrder = {"header", "body"})
public class EnvelopeDisabilityVehicle {

	private HeaderDisabilityVehicle header;
	private BodyDisabilityVehicle body;

	@XmlElement(name = "Header", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
	public HeaderDisabilityVehicle getHeader() {
		return header;
	}

	@XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
	public BodyDisabilityVehicle getBody() {
		return body;
	}
}

