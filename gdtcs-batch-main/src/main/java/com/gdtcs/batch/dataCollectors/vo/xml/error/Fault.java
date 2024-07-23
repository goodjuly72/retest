package com.gdtcs.batch.dataCollectors.vo.xml.error;


import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@XmlRootElement(name = "Fault", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class Fault {

	private String faultCode;
	private String faultString;

	@XmlElement(name = "faultcode")
	public String getFaultCode() {
		return faultCode;
	}

	@XmlElement(name = "faultstring")
	public String getFaultString() {
		return faultString;
	}
}
