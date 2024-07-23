package com.gdtcs.batch.dataCollectors.vo.xml.error;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.Setter;

@Setter
@XmlRootElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlType(propOrder = {"fault" })
public class BodyFault {
	private Fault fault;
	@XmlElement(name = "Fault", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
	public Fault getFault() {
		return fault;
	}
}
