package com.gdtcs.batch.dataCollectors.vo.xml.error;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Setter;

@Setter
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlType(propOrder = {"body"})
public class EnvelopeFalut {
	private BodyFault body;

	@XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
	public BodyFault getBody() {
		return body;
	}
}

