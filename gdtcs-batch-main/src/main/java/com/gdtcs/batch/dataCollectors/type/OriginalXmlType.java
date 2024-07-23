package com.gdtcs.batch.dataCollectors.type;

import lombok.Getter;

@Getter
public enum OriginalXmlType {
	MULTI_CHILD("03"),
	NATIONAL_MERIT("04"),
	DISABLED("05");

	private final String code;

	OriginalXmlType(String code) {
		this.code = code;
	}
}
